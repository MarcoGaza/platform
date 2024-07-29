package cn.econets.blossom.module.promotion.enums.common;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Marketing type enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum PromotionTypeEnum implements IntArrayValuable {

    SECKILL_ACTIVITY(1, "Second-sale event"),
    BARGAIN_ACTIVITY(2, "Bargaining activity"),
    COMBINATION_ACTIVITY(3, "Group buying activity"),

    DISCOUNT_ACTIVITY(4, "Limited time discount"),
    REWARD_ACTIVITY(5, "Get a discount if you spend more than 100 yuan"),

    MEMBER_LEVEL(6, "Membership discount"),
    COUPON(7, "Coupon"),
    POINT(8, "Points")
    ;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(PromotionTypeEnum::getType).toArray();

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
