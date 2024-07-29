package cn.econets.blossom.module.trade.enums.order;

import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Transaction order item - After-sales status
 *
 */
@RequiredArgsConstructor
@Getter
public enum TradeOrderItemAfterSaleStatusEnum implements IntArrayValuable {

    NONE(0, "Not sold yet"),
    APPLY(10, "After-sales service"),
    SUCCESS(20, "After-sales success");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(TradeOrderItemAfterSaleStatusEnum::getStatus).toArray();

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

    /**
     * Judge the specified state，Whether currently in【Not applied for】Status
     *
     * @param status Specify status
     * @return Yes
     */
    public static boolean isNone(Integer status) {
        return ObjectUtil.equals(status, NONE.getStatus());
    }

}
