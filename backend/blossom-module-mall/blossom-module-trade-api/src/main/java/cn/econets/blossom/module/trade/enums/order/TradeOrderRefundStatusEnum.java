package cn.econets.blossom.module.trade.enums.order;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Transaction Order - Refund Status
 *
 */
@RequiredArgsConstructor
@Getter
public enum TradeOrderRefundStatusEnum implements IntArrayValuable {

    NONE(0, "No refund"),
    PART(10, "Partial Refund"),
    ALL(20, "Full refund");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(TradeOrderRefundStatusEnum::getStatus).toArray();

    /**
     * Status value
     */
    private final Integer status;
    /**
     * Status name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
