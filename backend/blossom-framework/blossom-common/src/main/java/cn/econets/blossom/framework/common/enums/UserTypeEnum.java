package cn.econets.blossom.framework.common.enums;

import cn.econets.blossom.framework.common.core.IntArrayValuable;
import cn.hutool.core.util.ArrayUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * Global User Type Enumeration
 */
@AllArgsConstructor
@Getter
public enum UserTypeEnum implements IntArrayValuable {

    MEMBER(1, "Member"), // Facing c End，Ordinary user
    ADMIN(2, "Administrator"); // Facing b End，Management Backend

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(UserTypeEnum::getValue).toArray();

    /**
     * Type
     */
    private final Integer value;
    /**
     * Type name
     */
    private final String name;

    public static UserTypeEnum valueOf(Integer value) {
        return ArrayUtil.firstMatch(userType -> userType.getValue().equals(value), UserTypeEnum.values());
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
