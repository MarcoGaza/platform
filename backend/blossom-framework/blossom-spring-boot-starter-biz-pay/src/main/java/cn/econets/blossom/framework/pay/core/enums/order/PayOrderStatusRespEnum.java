package cn.econets.blossom.framework.pay.core.enums.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * Channel payment status enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum PayOrderStatusRespEnum {

    WAITING(0, "Not paid"),
    SUCCESS(10, "Payment successful"),
    REFUND(20, "Refunded"),
    CLOSED(30, "Payment closed"),
    ;

    private final Integer status;
    private final String name;

    /**
     * Judge whether the payment is successful
     *
     * @param status Status
     * @return Whether payment was successful
     */
    public static boolean isSuccess(Integer status) {
        return Objects.equals(status, SUCCESS.getStatus());
    }

    /**
     * Judge whether a refund has been made
     *
     * @param status Status
     * @return Whether payment was successful
     */
    public static boolean isRefund(Integer status) {
        return Objects.equals(status, REFUND.getStatus());
    }

    /**
     * Judge whether payment is closed
     *
     * @param status Status
     * @return Whether to close payment
     */
    public static boolean isClosed(Integer status) {
        return Objects.equals(status, CLOSED.getStatus());
    }

}
