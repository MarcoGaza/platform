package cn.econets.blossom.framework.permission.core.rule.dept;

/**
 * {@link DeptDataPermissionRule} Custom configuration interface
 *
 */
@FunctionalInterface
public interface DeptDataPermissionRuleCustomizer {

    /**
     * Customize this permission rule
     * 1. Call {@link DeptDataPermissionRule#addDeptColumn(Class, String)} Method，Configuration based on dept_id Filtering rules
     * 2. Call {@link DeptDataPermissionRule#addUserColumn(Class, String)} Method，Configuration based on user_id Filtering rules
     *
     * @param rule Permission Rules
     */
    void customize(DeptDataPermissionRule rule);

}
