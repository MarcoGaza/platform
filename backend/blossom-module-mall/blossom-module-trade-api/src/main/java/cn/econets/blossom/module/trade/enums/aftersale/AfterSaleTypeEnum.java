package cn.econets.blossom.module.trade.enums.aftersale;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Transaction and after-sales - Type
 *
 */
@RequiredArgsConstructor
@Getter
public enum AfterSaleTypeEnum implements IntArrayValuable {

    IN_SALE(10, "Refund during sale"), // The buyer requested a refund before the transaction was completed
    AFTER_SALE(20, "After-sales refund"); // After the transaction is completed, the buyer applies for a refund

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(AfterSaleTypeEnum::getType).toArray();

    /**
     * Type
     */
    private final Integer type;
    /**
     * Type name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
