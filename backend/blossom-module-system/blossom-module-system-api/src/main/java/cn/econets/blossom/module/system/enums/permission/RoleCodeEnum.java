package cn.econets.blossom.module.system.enums.permission;

import cn.econets.blossom.framework.common.util.object.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Role ID enumeration
 */
@Getter
@AllArgsConstructor
public enum RoleCodeEnum {

    SUPER_ADMIN("super_admin", "Super Administrator"),
    TENANT_ADMIN("tenant_admin", "Tenant Administrator"),
    ;

    /**
     * Role Code
     */
    private final String code;
    /**
     * Name
     */
    private final String name;

    public static boolean isSuperAdmin(String code) {
        return ObjectUtils.equalsAny(code, SUPER_ADMIN.getCode());
    }

}
