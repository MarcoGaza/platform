package cn.econets.blossom.module.pay.enums.wallet;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Wallet transaction business classification
 *
 *
 */
@AllArgsConstructor
@Getter
public enum PayWalletBizTypeEnum implements IntArrayValuable {

    RECHARGE(1, "Recharge"),
    RECHARGE_REFUND(2, "Recharge Refund"),
    PAYMENT(3, "Payment"),
    PAYMENT_REFUND(4, "Payment refund");

    // TODO Added later

    /**
     * Business Classification
     */
    private final Integer type;
    /**
     * Description
     */
    private final String description;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PayWalletBizTypeEnum::getType).toArray();

    @Override
    public int[] array() {
         return ARRAYS;
    }
}
