package cn.econets.blossom.framework.security.core.service;

/**
 * Security Framework Service Interface，Define permission-related verification operations
 *\
 */
public interface SecurityFrameworkService {

    /**
     * Judge whether there is permission
     *
     * @param permission Permissions
     * @return Yes
     */
    boolean hasPermission(String permission);

    /**
     * Judge whether you have permission，Any one is fine
     *
     * @param permissions Permissions
     * @return Yes
     */
    boolean hasAnyPermissions(String... permissions);

    /**
     * Judge whether there is a role
     *
     * Attention，The character uses SysRoleDO of code Logo
     *
     * @param role Role
     * @return Yes
     */
    boolean hasRole(String role);

    /**
     * Judge whether there is a role，Any one is fine
     *
     * @param roles Role array
     * @return Yes
     */
    boolean hasAnyRoles(String... roles);

    /**
     * Judge whether it is authorized
     *
     * @param scope Authorization
     * @return Yes
     */
    boolean hasScope(String scope);

    /**
     * Judge whether there is an authorization scope，Any one is fine
     *
     * @param scope Authorization scope array
     * @return Yes
     */
    boolean hasAnyScopes(String... scope);
}
