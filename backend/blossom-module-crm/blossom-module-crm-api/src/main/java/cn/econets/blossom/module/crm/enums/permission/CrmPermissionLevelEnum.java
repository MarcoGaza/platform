package cn.econets.blossom.module.crm.enums.permission;

import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * CRM Data permission level enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum CrmPermissionLevelEnum implements IntArrayValuable {

    OWNER(1, "Person in charge"),
    READ(2, "Read"),
    WRITE(3, "Write");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CrmPermissionLevelEnum::getLevel).toArray();

    /**
     * Level
     */
    private final Integer level;
    /**
     * Level Name
     */
    private final String name;

    @Override
    public int[] array() {
        return ARRAYS;
    }

    public static boolean isOwner(Integer level) {
        return ObjUtil.equal(OWNER.level, level);
    }

    public static boolean isRead(Integer level) {
        return ObjUtil.equal(READ.level, level);
    }

    public static boolean isWrite(Integer level) {
        return ObjUtil.equal(WRITE.level, level);
    }

}
