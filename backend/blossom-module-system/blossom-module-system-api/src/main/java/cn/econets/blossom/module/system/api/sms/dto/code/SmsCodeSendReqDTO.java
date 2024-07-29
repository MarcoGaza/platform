package cn.econets.blossom.module.system.api.sms.dto.code;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.framework.common.validation.Mobile;
import cn.econets.blossom.module.system.enums.sms.SmsSceneEnum;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Sending SMS verification code Request DTO
 *
 */
@Data
public class SmsCodeSendReqDTO {

    /**
     * Mobile phone number
     */
    @Mobile
    @NotEmpty(message = "Mobile number cannot be empty")
    private String mobile;
    /**
     * Send scene
     */
    @NotNull(message = "The sending scene cannot be empty")
    @InEnum(SmsSceneEnum.class)
    private Integer scene;
    /**
     * Send IP
     */
    @NotEmpty(message = "Send IP Cannot be empty")
    private String createIp;

}
