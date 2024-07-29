package cn.econets.blossom.module.pay.enums.notify;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Payment notification status enumeration
 *
 *
 */
@Getter
@AllArgsConstructor
public enum PayNotifyStatusEnum {

    WAITING(0, "Waiting for notification"),
    SUCCESS(10, "Notification successful"),
    FAILURE(20, "Notification failed"), // Multiple attempts，Total failure
    REQUEST_SUCCESS(21, "Request successful，But the result failed"),
    REQUEST_FAILURE(22, "Request failed"),

    ;

    /**
     * Status
     */
    private final Integer status;
    /**
     * Name
     */
    private final String name;

}
