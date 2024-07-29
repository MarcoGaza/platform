package cn.econets.blossom.module.system.mq.message.mail;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Send message via email
 *
 */
@Data
public class MailSendMessage {

    /**
     * Mail log number
     */
    @NotNull(message = "Mail log number cannot be empty")
    private Long logId;
    /**
     * Receiving email address
     */
    @NotNull(message = "The receiving email address cannot be empty")
    private String mail;
    /**
     * Email account number
     */
    @NotNull(message = "The email account number cannot be empty")
    private Long accountId;

    /**
     * Mail sender
     */
    private String nickname;
    /**
     * Email Title
     */
    @NotEmpty(message = "The email title cannot be empty")
    private String title;
    /**
     * Email content
     */
    @NotEmpty(message = "The email content cannot be empty")
    private String content;

}
