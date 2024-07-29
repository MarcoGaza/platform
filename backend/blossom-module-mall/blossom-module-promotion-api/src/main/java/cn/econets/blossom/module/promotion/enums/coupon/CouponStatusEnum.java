package cn.econets.blossom.module.promotion.enums.coupon;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Coupon status enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum CouponStatusEnum implements IntArrayValuable {

    UNUSED(1, "Not used"),
    USED(2, "Already used"),
    EXPIRE(3, "Expired"),
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CouponStatusEnum::getStatus).toArray();

    /**
     * Value
     */
    private final Integer status;
    /**
     * Name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
