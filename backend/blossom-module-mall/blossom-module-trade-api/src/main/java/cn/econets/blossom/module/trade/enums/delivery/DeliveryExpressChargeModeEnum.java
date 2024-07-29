package cn.econets.blossom.module.trade.enums.delivery;

import cn.hutool.core.util.ArrayUtil;
import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Express delivery billing method enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum DeliveryExpressChargeModeEnum implements IntArrayValuable {

    COUNT(1, "According to the item"),
    WEIGHT(2,"By weight"),
    VOLUME(3, "By volume");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DeliveryExpressChargeModeEnum::getType).toArray();

    /**
     * Type
     */
    private final Integer type;
    /**
     * Description
     */
    private final String desc;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static DeliveryExpressChargeModeEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(chargeMode -> chargeMode.getType().equals(value), DeliveryExpressChargeModeEnum.values());
    }

}
