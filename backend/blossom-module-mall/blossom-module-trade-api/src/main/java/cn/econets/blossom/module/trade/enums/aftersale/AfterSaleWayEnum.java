package cn.econets.blossom.module.trade.enums.aftersale;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Transaction and after-sales - Method
 *
 */
@RequiredArgsConstructor
@Getter
public enum AfterSaleWayEnum implements IntArrayValuable {

    REFUND(10, "Refund only"),
    RETURN_AND_REFUND(20, "Return and Refund");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(AfterSaleWayEnum::getWay).toArray();

    /**
     * Method
     */
    private final Integer way;
    /**
     * Method name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
