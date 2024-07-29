package cn.econets.blossom.framework.common.enums;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import cn.hutool.core.util.ObjUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * General status enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum CommonStatusEnum implements IntArrayValuable {

    ENABLE(0, "Open"),
    DISABLE(1, "Close");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CommonStatusEnum::getStatus).toArray();

    /**
     * Status value
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

    public static boolean isEnable(Integer status) {
        return ObjUtil.equal(ENABLE.status, status);
    }

    public static boolean isDisable(Integer status) {
        return ObjUtil.equal(DISABLE.status, status);
    }

}
