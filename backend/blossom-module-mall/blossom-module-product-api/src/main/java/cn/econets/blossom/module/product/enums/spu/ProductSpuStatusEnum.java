package cn.econets.blossom.module.product.enums.spu;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Products SPU Status
 *
 */
@Getter
@AllArgsConstructor
public enum ProductSpuStatusEnum implements IntArrayValuable {

    RECYCLE(-1, "Recycle Bin"),
    DISABLE(0, "Off the shelf"),
    ENABLE(1, "On the shelf");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(ProductSpuStatusEnum::getStatus).toArray();

    /**
     * Status
     */
    private final Integer status;
    /**
     * Status name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    /**
     * Judge whether it is in【On the shelf】Status
     *
     * @param status Status
     * @return Whether in【On the shelf】Status
     */
    public static boolean isEnable(Integer status) {
        return ENABLE.getStatus().equals(status);
    }

}
