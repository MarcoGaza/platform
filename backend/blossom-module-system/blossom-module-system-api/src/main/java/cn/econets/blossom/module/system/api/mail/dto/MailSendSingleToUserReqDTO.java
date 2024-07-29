package cn.econets.blossom.module.system.api.mail.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Email sent Request DTO
 *
 */
@Data
public class MailSendSingleToUserReqDTO {

    /**
     * User Number
     */
    private Long userId;
    /**
     * Email
     */
    @Email
    private String mail;

    /**
     * Mail template number
     */
    @NotNull(message = "The email template ID cannot be empty")
    private String templateCode;
    /**
     * Email template parameters
     */
    private Map<String, Object> templateParams;

}
