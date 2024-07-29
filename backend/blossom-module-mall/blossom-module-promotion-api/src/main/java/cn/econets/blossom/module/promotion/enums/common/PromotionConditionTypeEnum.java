package cn.econets.blossom.module.promotion.enums.common;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Marketing condition type enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum PromotionConditionTypeEnum implements IntArrayValuable {

    PRICE(10, "Full N Yuan"),
    COUNT(20, "Full N Item");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PromotionConditionTypeEnum::getType).toArray();

    /**
     * Type value
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
