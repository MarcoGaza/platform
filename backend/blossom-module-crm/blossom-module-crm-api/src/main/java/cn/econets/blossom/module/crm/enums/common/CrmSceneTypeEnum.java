package cn.econets.blossom.module.crm.enums.common;

import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.core.IntArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * CRM List search scenes
 *
 */
@Getter
@AllArgsConstructor
public enum CrmSceneTypeEnum implements IntArrayValuable {

    OWNER(1, "I am responsible"),
    INVOLVED(2, "I participated"),
    SUBORDINATE(3, "Subordinates are responsible");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(CrmSceneTypeEnum::getType).toArray();

    /**
     * Scene Type
     */
    private final Integer type;
    /**
     * Scene name
     */
    private final String name;

    public static boolean isOwner(Integer type) {
        return ObjUtil.equal(OWNER.getType(), type);
    }

    public static boolean isInvolved(Integer type) {
        return ObjUtil.equal(INVOLVED.getType(), type);
    }

    public static boolean isSubordinate(Integer type) {
        return ObjUtil.equal(SUBORDINATE.getType(), type);
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }

}
