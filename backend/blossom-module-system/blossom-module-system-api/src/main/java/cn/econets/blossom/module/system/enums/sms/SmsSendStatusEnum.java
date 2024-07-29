package cn.econets.blossom.module.system.enums.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SMS sending status enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum SmsSendStatusEnum {

    INIT(0), // Initialization
    SUCCESS(10), // Sent successfully
    FAILURE(20), // Send failed
    IGNORE(30), // Ignore，Do not send
    ;

    private final int status;

}
