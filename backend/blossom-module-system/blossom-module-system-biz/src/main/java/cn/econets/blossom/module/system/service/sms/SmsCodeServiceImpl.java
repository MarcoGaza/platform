package cn.econets.blossom.module.system.service.sms;

import cn.econets.blossom.framework.common.util.date.DateUtils;
import cn.econets.blossom.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import cn.econets.blossom.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import cn.econets.blossom.module.system.api.sms.dto.code.SmsCodeValidateReqDTO;
import cn.econets.blossom.module.system.dal.mysql.sms.SmsCodeMapper;
import cn.econets.blossom.module.system.dal.dataobject.sms.SmsCodeDO;
import cn.econets.blossom.module.system.enums.ErrorCodeConstants;
import cn.econets.blossom.module.system.enums.sms.SmsSceneEnum;
import cn.econets.blossom.module.system.framework.sms.config.SmsCodeProperties;
import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.hutool.core.util.RandomUtil.randomInt;

/**
 * SMS verification code Service Implementation class
 *
 */
@Service
@Validated
public class SmsCodeServiceImpl implements SmsCodeService {

    @Resource
    private SmsCodeProperties smsCodeProperties;

    @Resource
    private SmsCodeMapper smsCodeMapper;

    @Resource
    private SmsSendService smsSendService;

    @Override
    public void sendSmsCode(SmsCodeSendReqDTO reqDTO) {
        SmsSceneEnum sceneEnum = SmsSceneEnum.getCodeByScene(reqDTO.getScene());
        Assert.notNull(sceneEnum, "Verification code scene({}) Configuration not found", reqDTO.getScene());
        // Create verification code
        String code = createSmsCode(reqDTO.getMobile(), reqDTO.getScene(), reqDTO.getCreateIp());
        // Send verification code
        smsSendService.sendSingleSms(reqDTO.getMobile(), null, null,
                sceneEnum.getTemplateCode(), MapUtil.of("code", code));
    }

    private String createSmsCode(String mobile, Integer scene, String ip) {
        // Check whether the verification code can be sent，No need to filter scenes
        SmsCodeDO lastSmsCode = smsCodeMapper.selectLastByMobile(mobile, null, null);
        if (lastSmsCode != null) {
            if (LocalDateTimeUtil.between(lastSmsCode.getCreateTime(), LocalDateTime.now()).toMillis()
                    < smsCodeProperties.getSendFrequency().toMillis()) { // Sent too frequently
                throw exception(ErrorCodeConstants.SMS_CODE_SEND_TOO_FAST);
            }
            if (DateUtils.isToday(lastSmsCode.getCreateTime()) && // It must be today，To calculate the limit exceeding the current day
                    lastSmsCode.getTodayIndex() >= smsCodeProperties.getSendMaximumQuantityPerDay()) { // Exceeded the upper limit of sending on the day。
                throw exception(ErrorCodeConstants.SMS_CODE_EXCEED_SEND_MAXIMUM_QUANTITY_PER_DAY);
            }
            // TODO Promotion，Each IP Number of items that can be sent per day
            // TODO Improvement，Each IP Number of items that can be sent per hour
        }

        // Create verification code record
        String code = String.valueOf(randomInt(smsCodeProperties.getBeginCode(), smsCodeProperties.getEndCode() + 1));
        SmsCodeDO newSmsCode = SmsCodeDO.builder().mobile(mobile).code(code).scene(scene)
                .todayIndex(lastSmsCode != null && DateUtils.isToday(lastSmsCode.getCreateTime()) ? lastSmsCode.getTodayIndex() + 1 : 1)
                .createIp(ip).used(false).build();
        smsCodeMapper.insert(newSmsCode);
        return code;
    }

    @Override
    public void useSmsCode(SmsCodeUseReqDTO reqDTO) {
        // Check whether the verification code is valid
        SmsCodeDO lastSmsCode = validateSmsCode0(reqDTO.getMobile(), reqDTO.getCode(), reqDTO.getScene());
        // Use verification code
        smsCodeMapper.updateById(SmsCodeDO.builder().id(lastSmsCode.getId())
                .used(true).usedTime(LocalDateTime.now()).usedIp(reqDTO.getUsedIp()).build());
    }

    @Override
    public void validateSmsCode(SmsCodeValidateReqDTO reqDTO) {
        validateSmsCode0(reqDTO.getMobile(), reqDTO.getCode(), reqDTO.getScene());
    }

    private SmsCodeDO validateSmsCode0(String mobile, String code, Integer scene) {
        // Verify verification code
        SmsCodeDO lastSmsCode = smsCodeMapper.selectLastByMobile(mobile, code, scene);
        // If the verification code does not exist，Throws an exception
        if (lastSmsCode == null) {
            throw exception(ErrorCodeConstants.SMS_CODE_NOT_FOUND);
        }
        // Time exceeded
        if (LocalDateTimeUtil.between(lastSmsCode.getCreateTime(), LocalDateTime.now()).toMillis()
                >= smsCodeProperties.getExpireTimes().toMillis()) { // Verification code has expired
            throw exception(ErrorCodeConstants.SMS_CODE_EXPIRED);
        }
        // Judge whether the verification code has been used
        if (Boolean.TRUE.equals(lastSmsCode.getUsed())) {
            throw exception(ErrorCodeConstants.SMS_CODE_USED);
        }
        return lastSmsCode;
    }

}
