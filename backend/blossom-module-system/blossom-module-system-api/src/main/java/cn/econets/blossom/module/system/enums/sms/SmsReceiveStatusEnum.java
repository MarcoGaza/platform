package cn.econets.blossom.module.system.enums.sms;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * SMS receiving status enumeration
 *
 *
 */
@Getter
@AllArgsConstructor
public enum SmsReceiveStatusEnum {

    INIT(0), // Initialization
    SUCCESS(10), // Received successfully
    FAILURE(20), // Receiving failed
    ;

    private final int status;

}
