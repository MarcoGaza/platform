package cn.econets.blossom.module.system.service.sms;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.sms.vo.log.SmsLogPageReqVO;
import cn.econets.blossom.module.system.dal.mysql.sms.SmsLogMapper;
import cn.econets.blossom.module.system.dal.dataobject.sms.SmsLogDO;
import cn.econets.blossom.module.system.dal.dataobject.sms.SmsTemplateDO;
import cn.econets.blossom.module.system.enums.sms.SmsReceiveStatusEnum;
import cn.econets.blossom.module.system.enums.sms.SmsSendStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

/**
 * SMS log Service Implementation class
 *
 */
@Slf4j
@Service
public class SmsLogServiceImpl implements SmsLogService {

    @Resource
    private SmsLogMapper smsLogMapper;

    @Override
    public Long createSmsLog(String mobile, Long userId, Integer userType, Boolean isSend,
                             SmsTemplateDO template, String templateContent, Map<String, Object> templateParams) {
        SmsLogDO.SmsLogDOBuilder logBuilder = SmsLogDO.builder();
        // Depending on whether to send，Set status
        logBuilder.sendStatus(Objects.equals(isSend, true) ? SmsSendStatusEnum.INIT.getStatus()
                : SmsSendStatusEnum.IGNORE.getStatus());
        // Set mobile phone related fields
        logBuilder.mobile(mobile).userId(userId).userType(userType);
        // Set template related fields
        logBuilder.templateId(template.getId()).templateCode(template.getCode()).templateType(template.getType());
        logBuilder.templateContent(templateContent).templateParams(templateParams)
                .apiTemplateId(template.getApiTemplateId());
        // Set channel related fields
        logBuilder.channelId(template.getChannelId()).channelCode(template.getChannelCode());
        // Set receiving related fields
        logBuilder.receiveStatus(SmsReceiveStatusEnum.INIT.getStatus());

        // Insert into database
        SmsLogDO logDO = logBuilder.build();
        smsLogMapper.insert(logDO);
        return logDO.getId();
    }

    @Override
    public void updateSmsSendResult(Long id, Boolean success,
                                    String apiSendCode, String apiSendMsg,
                                    String apiRequestId, String apiSerialNo) {
        SmsSendStatusEnum sendStatus = success ? SmsSendStatusEnum.SUCCESS : SmsSendStatusEnum.FAILURE;
        smsLogMapper.updateById(SmsLogDO.builder().id(id)
                .sendStatus(sendStatus.getStatus()).sendTime(LocalDateTime.now())
                .apiSendCode(apiSendCode).apiSendMsg(apiSendMsg)
                .apiRequestId(apiRequestId).apiSerialNo(apiSerialNo).build());
    }

    @Override
    public void updateSmsReceiveResult(Long id, Boolean success, LocalDateTime receiveTime,
                                       String apiReceiveCode, String apiReceiveMsg) {
        SmsReceiveStatusEnum receiveStatus = Objects.equals(success, true) ?
                SmsReceiveStatusEnum.SUCCESS : SmsReceiveStatusEnum.FAILURE;
        smsLogMapper.updateById(SmsLogDO.builder().id(id).receiveStatus(receiveStatus.getStatus())
                .receiveTime(receiveTime).apiReceiveCode(apiReceiveCode).apiReceiveMsg(apiReceiveMsg).build());
    }

    @Override
    public PageResult<SmsLogDO> getSmsLogPage(SmsLogPageReqVO pageReqVO) {
        return smsLogMapper.selectPage(pageReqVO);
    }

}
