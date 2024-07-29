package cn.econets.blossom.module.crm.framework.permission.core.util;

import cn.hutool.extra.spring.SpringUtil;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionRoleCodeEnum;
import cn.econets.blossom.module.system.api.permission.PermissionApi;

import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

/**
 * Data Permission Tools
 *
 */
public class CrmPermissionUtils {

    /**
     * Check if the user is CRM Administrator
     *
     * @return Yes/No
     */
    public static boolean isCrmAdmin() {
        return SingletonManager.getPermissionApi().hasAnyRoles(getLoginUserId(), CrmPermissionRoleCodeEnum.CRM_ADMIN.getCode());
    }

    /**
     * Static inner class implements singleton acquisition
     *
     * @author HUIHUI
     */
    private static class SingletonManager {

        private static final PermissionApi PERMISSION_API = SpringUtil.getBean(PermissionApi.class);

        public static PermissionApi getPermissionApi() {
            return PERMISSION_API;
        }

    }

}
