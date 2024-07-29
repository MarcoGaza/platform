package cn.econets.blossom.module.system.enums.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumeration of email sending status
 *
 */
@Getter
@AllArgsConstructor
public enum MailSendStatusEnum {

    INIT(0), // Initialization
    SUCCESS(10), // Sent successfully
    FAILURE(20), // Send failed
    IGNORE(30), // Ignore，Do not send
    ;

    private final int status;

}
