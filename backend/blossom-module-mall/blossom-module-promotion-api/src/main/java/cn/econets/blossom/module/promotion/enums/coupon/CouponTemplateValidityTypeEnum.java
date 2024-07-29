package cn.econets.blossom.module.promotion.enums.coupon;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Enumeration of the limited-time type of coupon template
 *
 */
@AllArgsConstructor
@Getter
public enum CouponTemplateValidityTypeEnum implements IntArrayValuable {

    DATE(1, "Fixed date"),
    TERM(2, "After receiving"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CouponTemplateValidityTypeEnum::getType).toArray();

    /**
     * Value
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
