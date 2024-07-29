package cn.econets.blossom.module.promotion.enums.common;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Marketing product range enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum PromotionProductScopeEnum implements IntArrayValuable {

    ALL(1, "General Coupon"), // All products
    SPU(2, "Commodity voucher"), // Specified products
    CATEGORY(3, "Category coupons"), // Specify category
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PromotionProductScopeEnum::getScope).toArray();

    /**
     * Range value
     */
    private final Integer scope;
    /**
     * Range Name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
