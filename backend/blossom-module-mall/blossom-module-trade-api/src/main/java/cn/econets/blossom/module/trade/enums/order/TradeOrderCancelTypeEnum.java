package cn.econets.blossom.module.trade.enums.order;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Transaction Order - Close type
 *
 */
@RequiredArgsConstructor
@Getter
public enum TradeOrderCancelTypeEnum implements IntArrayValuable {

    PAY_TIMEOUT(10, "Overdue payment"),
    AFTER_SALE_CLOSE(20, "Refund is closed"),
    MEMBER_CANCEL(30, "Buyer canceled");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(TradeOrderCancelTypeEnum::getType).toArray();

    /**
     * Close type
     */
    private final Integer type;
    /**
     * Close type name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
