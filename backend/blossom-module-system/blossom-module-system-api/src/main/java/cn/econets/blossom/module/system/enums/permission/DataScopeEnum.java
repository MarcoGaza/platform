package cn.econets.blossom.module.system.enums.permission;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Data range enumeration class
 *
 * Used to implement data-level permissions
 *
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum implements IntArrayValuable {

    ALL(1), // All data permissions

    DEPT_CUSTOM(2), // Specify department data permissions
    DEPT_ONLY(3), // Department data permissions
    DEPT_AND_CHILD(4), // Data permissions for departments and below

    SELF(5); // Only personal data permissions

    /**
     * Range
     */
    private final Integer scope;

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(DataScopeEnum::getScope).toArray();

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
