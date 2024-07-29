package cn.econets.blossom.module.system.service.sms;

import cn.econets.blossom.framework.common.core.KeyValue;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.permission.core.annotation.DataPermission;
import cn.econets.blossom.module.system.framework.sms.core.client.SmsClient;
import cn.econets.blossom.module.system.framework.sms.core.client.dto.SmsReceiveRespDTO;
import cn.econets.blossom.module.system.framework.sms.core.client.dto.SmsSendRespDTO;
import cn.econets.blossom.module.system.dal.dataobject.sms.SmsChannelDO;
import cn.econets.blossom.module.system.dal.dataobject.sms.SmsTemplateDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import cn.econets.blossom.module.system.enums.ErrorCodeConstants;
import cn.econets.blossom.module.system.mq.message.sms.SmsSendMessage;
import cn.econets.blossom.module.system.mq.producer.sms.SmsProducer;
import cn.econets.blossom.module.system.service.member.MemberService;
import cn.econets.blossom.module.system.service.user.AdminUserService;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;

/**
 * Send SMS Service Implementation of sending
 *
 */
@Service
@Slf4j
public class SmsSendServiceImpl implements SmsSendService {

    @Resource
    private AdminUserService adminUserService;
    @Resource
    private MemberService memberService;
    @Resource
    private SmsChannelService smsChannelService;
    @Resource
    private SmsTemplateService smsTemplateService;
    @Resource
    private SmsLogService smsLogService;

    @Resource
    private SmsProducer smsProducer;

    @Override
    @DataPermission(enable = false) // When sending SMS，No need to consider data permissions
    public Long sendSingleSmsToAdmin(String mobile, Long userId, String templateCode, Map<String, Object> templateParams) {
        // If mobile Empty，Then load the mobile phone number corresponding to the user number
        if (StrUtil.isEmpty(mobile)) {
            AdminUserDO user = adminUserService.getUser(userId);
            if (user != null) {
                mobile = user.getMobile();
            }
        }
        // Execute send
        return sendSingleSms(mobile, userId, UserTypeEnum.ADMIN.getValue(), templateCode, templateParams);
    }

    @Override
    public Long sendSingleSmsToMember(String mobile, Long userId, String templateCode, Map<String, Object> templateParams) {
        // If mobile Empty，Then load the mobile phone number corresponding to the user number
        if (StrUtil.isEmpty(mobile)) {
            mobile = memberService.getMemberUserMobile(userId);
        }
        // Execute send
        return sendSingleSms(mobile, userId, UserTypeEnum.MEMBER.getValue(), templateCode, templateParams);
    }

    @Override
    public Long sendSingleSms(String mobile, Long userId, Integer userType,
                              String templateCode, Map<String, Object> templateParams) {
        // Check whether the SMS template is legal
        SmsTemplateDO template = validateSmsTemplate(templateCode);
        // Check whether the SMS channel is legal
        SmsChannelDO smsChannel = validateSmsChannel(template.getChannelId());

        // Check if the mobile phone number exists
        mobile = validateMobile(mobile);
        // Build ordered template parameters。Why put it in this position?，is to ensure the correctness of template parameters in advance，Instead of inserting and sending logs
        List<KeyValue<String, Object>> newTemplateParams = buildTemplateParams(template, templateParams);

        // Create a sending log。If the template is disabled，Do not send SMS，Only log
        Boolean isSend = CommonStatusEnum.ENABLE.getStatus().equals(template.getStatus())
                && CommonStatusEnum.ENABLE.getStatus().equals(smsChannel.getStatus());
        String content = smsTemplateService.formatSmsTemplateContent(template.getContent(), templateParams);
        Long sendLogId = smsLogService.createSmsLog(mobile, userId, userType, isSend, template, content, templateParams);

        // Send MQ Message，Asynchronous execution of SMS sending
        if (isSend) {
            smsProducer.sendSmsSendMessage(sendLogId, mobile, template.getChannelId(),
                    template.getApiTemplateId(), newTemplateParams);
        }
        return sendLogId;
    }

    @VisibleForTesting
    SmsChannelDO validateSmsChannel(Long channelId) {
        // Get SMS template。Taking efficiency into consideration，Get from cache
        SmsChannelDO channelDO = smsChannelService.getSmsChannel(channelId);
        // SMS template does not exist
        if (channelDO == null) {
            throw exception(ErrorCodeConstants.SMS_CHANNEL_NOT_EXISTS);
        }
        return channelDO;
    }

    @VisibleForTesting
    SmsTemplateDO validateSmsTemplate(String templateCode) {
        // Get SMS template。Taking efficiency into consideration，Get from cache
        SmsTemplateDO template = smsTemplateService.getSmsTemplateByCodeFromCache(templateCode);
        // SMS template does not exist
        if (template == null) {
            throw exception(ErrorCodeConstants.SMS_SEND_TEMPLATE_NOT_EXISTS);
        }
        return template;
    }

    /**
     * Set parameter template，Processed into order KeyValue Array
     * <p>
     * The reason is，Some SMS platforms do not use key As a parameter，It is an array subscript，For example <a href="https://cloud.tencent.com/document/product/382/39023">Tencent Cloud</a>
     *
     * @param template       SMS template
     * @param templateParams Original parameters
     * @return Processed parameters
     */
    @VisibleForTesting
    List<KeyValue<String, Object>> buildTemplateParams(SmsTemplateDO template, Map<String, Object> templateParams) {
        return template.getParams().stream().map(key -> {
            Object value = templateParams.get(key);
            if (value == null) {
                throw exception(ErrorCodeConstants.SMS_SEND_MOBILE_TEMPLATE_PARAM_MISS, key);
            }
            return new KeyValue<>(key, value);
        }).collect(Collectors.toList());
    }

    @VisibleForTesting
    public String validateMobile(String mobile) {
        if (StrUtil.isEmpty(mobile)) {
            throw exception(ErrorCodeConstants.SMS_SEND_MOBILE_NOT_EXISTS);
        }
        return mobile;
    }

    @Override
    public void doSendSms(SmsSendMessage message) {
        // Get the corresponding channel SmsClient Client
        SmsClient smsClient = smsChannelService.getSmsClient(message.getChannelId());
        Assert.notNull(smsClient, "SMS client({}) Does not exist", message.getChannelId());
        // Send SMS
        try {
            SmsSendRespDTO sendResponse = smsClient.sendSms(message.getLogId(), message.getMobile(),
                    message.getApiTemplateId(), message.getTemplateParams());
            smsLogService.updateSmsSendResult(message.getLogId(), sendResponse.getSuccess(),
                    sendResponse.getApiCode(), sendResponse.getApiMsg(),
                    sendResponse.getApiRequestId(), sendResponse.getSerialNo());
        } catch (Throwable ex) {
            log.error("[doSendSms][Abnormal SMS sending，Log number({})]", message.getLogId(), ex);
            smsLogService.updateSmsSendResult(message.getLogId(), false,
                    "EXCEPTION", ExceptionUtil.getRootCauseMessage(ex), null, null);
        }
    }

    @Override
    public void receiveSmsStatus(String channelCode, String text) throws Throwable {
        // Get the corresponding channel SmsClient Client
        SmsClient smsClient = smsChannelService.getSmsClient(channelCode);
        Assert.notNull(smsClient, "SMS client({}) Does not exist", channelCode);
        // Parsing content
        List<SmsReceiveRespDTO> receiveResults = smsClient.parseSmsReceiveStatus(text);
        if (CollUtil.isEmpty(receiveResults)) {
            return;
        }
        // Update the SMS log reception results. Because the quantity is usually not large，So use it first for Loop update
        receiveResults.forEach(result -> smsLogService.updateSmsReceiveResult(result.getLogId(),
                result.getSuccess(), result.getReceiveTime(), result.getErrorCode(), result.getErrorMsg()));
    }

}
