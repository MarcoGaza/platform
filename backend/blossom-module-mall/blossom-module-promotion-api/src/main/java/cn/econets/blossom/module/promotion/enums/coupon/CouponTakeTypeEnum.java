package cn.econets.blossom.module.promotion.enums.coupon;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * How to get coupons
 *
 */
@AllArgsConstructor
@Getter
public enum CouponTakeTypeEnum implements IntArrayValuable {

    USER(1, "Receive directly"), // Users can go to the home page、Get your daily coupons directly
    ADMIN(2, "Specified distribution"), // Specify members in the backend to receive coupons
    REGISTER(3, "Newcomer Coupon"), // Automatically receive when registering
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CouponTakeTypeEnum::getValue).toArray();

    /**
     * Value
     */
    private final Integer value;
    /**
     * Name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
