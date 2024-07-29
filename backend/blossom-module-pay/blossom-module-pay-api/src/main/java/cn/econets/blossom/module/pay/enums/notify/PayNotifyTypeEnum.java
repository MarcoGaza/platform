package cn.econets.blossom.module.pay.enums.notify;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Payment notification type
 *
 *
 */
@Getter
@AllArgsConstructor
public enum PayNotifyTypeEnum {

    ORDER(1, "Payment slip"),
    REFUND(2, "Refund slip"),
    TRANSFER(3, "Transfer slip")
    ;

    /**
     * Type
     */
    private final Integer type;
    /**
     * Name
     */
    private final String name;

}
