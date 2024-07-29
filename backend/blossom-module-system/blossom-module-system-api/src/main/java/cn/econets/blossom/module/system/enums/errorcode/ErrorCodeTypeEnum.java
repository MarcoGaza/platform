package cn.econets.blossom.module.system.enums.errorcode;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Error code type enumeration
 *
 */
@AllArgsConstructor
@Getter
public enum ErrorCodeTypeEnum implements IntArrayValuable {

    /**
     * Automatically generated
     */
    AUTO_GENERATION(1),
    /**
     * Manual Edit
     */
    MANUAL_OPERATION(2);

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(ErrorCodeTypeEnum::getType).toArray();

    /**
     * Type
     */
    private final Integer type;

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
