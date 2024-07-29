package cn.econets.blossom.framework.permission.core.rule;

import java.util.List;

/**
 * {@link DataPermissionRule} Factory Interface
 * As {@link DataPermissionRule} Container，Provide management capabilities
 *
 */
public interface DataPermissionRuleFactory {

    /**
     * Get all data permission rule arrays
     *
     * @return Data permission rule array
     */
    List<DataPermissionRule> getDataPermissionRules();

    /**
     * Get the designation Mapper Data permission rule array
     *
     * @param mappedStatementId Specify Mapper Number
     * @return Data permission rule array
     */
    List<DataPermissionRule> getDataPermissionRule(String mappedStatementId);

}
