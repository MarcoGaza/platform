package cn.econets.blossom.module.pay.enums.refund;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * Channel refund status enumeration
 *
 *
 */
@Getter
@AllArgsConstructor
public enum PayRefundStatusEnum {

    WAITING(0, "No refund"),
    SUCCESS(10, "Refund successful"),
    FAILURE(20, "Refund failed");

    private final Integer status;
    private final String name;

    public static boolean isSuccess(Integer status) {
        return Objects.equals(status, SUCCESS.getStatus());
    }

    public static boolean isFailure(Integer status) {
        return Objects.equals(status, FAILURE.getStatus());
    }

}
