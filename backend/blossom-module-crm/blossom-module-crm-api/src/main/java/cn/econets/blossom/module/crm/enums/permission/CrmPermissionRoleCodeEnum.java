package cn.econets.blossom.module.crm.enums.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Crm Data permission role enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum CrmPermissionRoleCodeEnum {

    CRM_ADMIN("crm_admin", "CRM Administrator");

    /**
     * Role ID
     */
    private String code;
    /**
     * Role name
     */
    private String name;

}

