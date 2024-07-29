package cn.econets.blossom.module.trade.enums.order;

import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * Transaction Order - Type
 *
 */
@RequiredArgsConstructor
@Getter
public enum TradeOrderTypeEnum implements IntArrayValuable {

    NORMAL(0, "Ordinary order"),
    SECKILL(1, "Second-selling order"),
    BARGAIN(2, "Bargaining Order"),
    COMBINATION(3, "Group order"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(TradeOrderTypeEnum::getType).toArray();

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

    public static boolean isNormal(Integer type) {
        return ObjectUtil.equal(type, NORMAL.getType());
    }

    public static boolean isSeckill(Integer type) {
        return ObjectUtil.equal(type, SECKILL.getType());
    }

    public static boolean isBargain(Integer type) {
        return ObjectUtil.equal(type, BARGAIN.getType());
    }

    public static boolean isCombination(Integer type) {
        return ObjectUtil.equal(type, COMBINATION.getType());
    }

}
