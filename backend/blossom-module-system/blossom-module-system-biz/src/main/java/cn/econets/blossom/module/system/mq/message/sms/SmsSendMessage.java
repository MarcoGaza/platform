package cn.econets.blossom.module.system.mq.message.sms;

import cn.econets.blossom.framework.common.core.KeyValue;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Send message via SMS
 *
 */
@Data
public class SmsSendMessage {

    /**
     * SMS log number
     */
    @NotNull(message = "The SMS log number cannot be empty")
    private Long logId;
    /**
     * Mobile phone number
     */
    @NotNull(message = "Mobile number cannot be empty")
    private String mobile;
    /**
     * SMS channel number
     */
    @NotNull(message = "The SMS channel number cannot be empty")
    private Long channelId;
    /**
     * SMS API Template number
     */
    @NotNull(message = "SMS API The template number cannot be empty")
    private String apiTemplateId;
    /**
     * SMS template parameters
     */
    private List<KeyValue<String, Object>> templateParams;

}
