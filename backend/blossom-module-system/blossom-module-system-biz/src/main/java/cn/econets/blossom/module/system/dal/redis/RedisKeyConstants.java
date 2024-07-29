package cn.econets.blossom.module.system.dal.redis;

import cn.econets.blossom.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;

/**
 * System Redis Key Enumeration class
 *
 */
public interface RedisKeyConstants {

    /**
     * A cache of the array of all sub-department numbers of a specified department
     * <p>
     * KEY Format：dept_children_ids:{id}
     * VALUE Data type：String Sub-department number collection
     */
    String DEPT_CHILDREN_ID_LIST = "dept_children_ids";

    /**
     * Role cache
     * <p>
     * KEY Format：role:{id}
     * VALUE Data type：String Character Information
     */
    String ROLE = "role";

    /**
     * Cache of role IDs owned by the user
     * <p>
     * KEY Format：user_role_ids:{userId}
     * VALUE Data type：String Role number collection
     */
    String USER_ROLE_ID_LIST = "user_role_ids";

    /**
     * The cache with the character ID of the specified menu
     * <p>
     * KEY Format：user_role_ids:{menuId}
     * VALUE Data type：String Role number collection
     */
    String MENU_ROLE_ID_LIST = "menu_role_ids";

    /**
     * Cache of array of menu numbers corresponding to permissions
     * <p>
     * KEY Format：permission_menu_ids:{permission}
     * VALUE Data type：String Menu number array
     */
    String PERMISSION_MENU_ID_LIST = "permission_menu_ids";

    /**
     * OAuth2 Client cache
     * <p>
     * KEY Format：user:{id}
     * VALUE Data type：String Client Information
     */
    String OAUTH_CLIENT = "oauth_client";

    /**
     * Access token cache
     * <p>
     * KEY Format：oauth2_access_token:{token}
     * VALUE Data type：String Access token information {@link OAuth2AccessTokenDO}
     * <p>
     * Due to dynamic expiration time，Use RedisTemplate Operation
     */
    String OAUTH2_ACCESS_TOKEN = "oauth2_access_token:%s";

    /**
     * Cache of internal message templates
     * <p>
     * KEY Format：notify_template:{code}
     * VALUE Data format：String Template information
     */
    String NOTIFY_TEMPLATE = "notify_template";

    /**
     * Mail account cache
     * <p>
     * KEY Format：sms_template:{id}
     * VALUE Data format：String Account information
     */
    String MAIL_ACCOUNT = "mail_account";

    /**
     * Mail template cache
     * <p>
     * KEY Format：mail_template:{code}
     * VALUE Data format：String Template information
     */
    String MAIL_TEMPLATE = "mail_template";

    /**
     * SMS template cache
     * <p>
     * KEY Format：sms_template:{id}
     * VALUE Data format：String Template information
     */
    String SMS_TEMPLATE = "sms_template";
}
