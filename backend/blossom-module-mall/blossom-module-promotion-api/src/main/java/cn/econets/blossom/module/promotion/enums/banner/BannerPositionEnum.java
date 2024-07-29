package cn.econets.blossom.module.promotion.enums.banner;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Banner Position Enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum BannerPositionEnum implements IntArrayValuable {

    HOME_POSITION(1, "Home"),
    SECKILL_POSITION(2, "Second sale activity page"),
    COMBINATION_POSITION(3, "Bargaining activity page"),
    DISCOUNT_POSITION(4, "Limited time discount page"),
    REWARD_POSITION(5, "Save and get discounts page");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(BannerPositionEnum::getPosition).toArray();

    /**
     * Value
     */
    private final Integer position;
    /**
     * Name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
