package cn.econets.blossom.module.trade.enums.delivery;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Delivery method enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum DeliveryTypeEnum implements IntArrayValuable {

    EXPRESS(1, "Express delivery"),
    PICK_UP(2, "User pick up"),;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DeliveryTypeEnum::getType).toArray();

    /**
     * Delivery method
     */
    private final Integer type;
    /**
     * Status name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
