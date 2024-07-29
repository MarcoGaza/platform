package cn.econets.blossom.module.pay.enums.order;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import cn.econets.blossom.framework.common.util.object.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * Payment order status enumeration
 *
 *
 */
@Getter
@AllArgsConstructor
public enum PayOrderStatusEnum implements IntArrayValuable {

    WAITING(0, "Not paid"),
    SUCCESS(10, "Payment successful"),
    REFUND(20, "Refunded"),
    CLOSED(30, "Payment closed"), // Attention：After full refund，Still REFUND Status
    ;

    private final Integer status;
    private final String name;

    @Override
    public int[] array() {
        return new int[0];
    }

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
     * Judge whether the payment is successful or refunded
     *
     * @param status Status
     * @return Whether payment was successful or refunded
     */
    public static boolean isSuccessOrRefund(Integer status) {
        return ObjectUtils.equalsAny(status,
                SUCCESS.getStatus(), REFUND.getStatus());
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
