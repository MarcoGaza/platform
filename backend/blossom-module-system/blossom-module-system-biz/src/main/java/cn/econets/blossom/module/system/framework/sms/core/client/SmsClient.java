package cn.econets.blossom.module.system.framework.sms.core.client;


import cn.econets.blossom.framework.common.core.KeyValue;
import cn.econets.blossom.module.system.framework.sms.core.client.dto.SmsReceiveRespDTO;
import cn.econets.blossom.module.system.framework.sms.core.client.dto.SmsSendRespDTO;
import cn.econets.blossom.module.system.framework.sms.core.client.dto.SmsTemplateRespDTO;

import java.util.List;

/**
 * SMS client，Used to connect to various SMS platforms SDK，Realize functions such as SMS sending
 *
 */
public interface SmsClient {

    /**
     * Get channel number
     *
     * @return Channel Number
     */
    Long getId();

    /**
     * Send message
     *
     * @param logId Log number
     * @param mobile Mobile phone number
     * @param apiTemplateId SMS API Template number
     * @param templateParams SMS template parameters。Passed List Array，Ensure the order of parameters
     * @return SMS sending result
     */
    SmsSendRespDTO sendSms(Long logId, String mobile, String apiTemplateId,
                           List<KeyValue<String, Object>> templateParams) throws Throwable;

    /**
     * Analysis of the result of receiving SMS
     *
     * @param text Results
     * @return Result content
     * @throws Throwable When parsing text When an exception occurs，An exception will be thrown
     */
    List<SmsReceiveRespDTO> parseSmsReceiveStatus(String text) throws Throwable;

    /**
     * Query the specified SMS template
     *
     * @param apiTemplateId SMS API Template number
     * @return SMS template
     */
    SmsTemplateRespDTO getSmsTemplate(String apiTemplateId) throws Throwable;

}
