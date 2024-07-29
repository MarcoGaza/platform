package cn.econets.blossom.module.promotion.enums.common;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Promotion type enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum PromotionDiscountTypeEnum implements IntArrayValuable {

    PRICE(1, "Save on purchases"), // Specific amount
    PERCENT(2, "Discount"), // Percentage
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PromotionDiscountTypeEnum::getType).toArray();

    /**
     * Discount type
     */
    private final Integer type;
    /**
     * Name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
