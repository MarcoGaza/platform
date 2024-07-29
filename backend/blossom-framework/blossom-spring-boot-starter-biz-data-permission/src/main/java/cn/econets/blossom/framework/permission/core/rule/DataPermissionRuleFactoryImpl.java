package cn.econets.blossom.framework.permission.core.rule;

import cn.econets.blossom.framework.permission.core.annotation.DataPermission;
import cn.econets.blossom.framework.permission.core.aop.DataPermissionContextHolder;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Default DataPermissionRuleFactoryImpl Implementation class
 * Supported by {@link DataPermissionContextHolder} Filter data permissions
 *
 */
@RequiredArgsConstructor
public class DataPermissionRuleFactoryImpl implements DataPermissionRuleFactory {

    /**
     * Data permission rule array
     */
    private final List<DataPermissionRule> rules;

    @Override
    public List<DataPermissionRule> getDataPermissionRules() {
        return rules;
    }

    @Override // mappedStatementId Parameters，Not used yet。Later，Can be based on mappedStatementId + DataPermission Cache
    public List<DataPermissionRule> getDataPermissionRule(String mappedStatementId) {
        // 1. No data permission
        if (CollUtil.isEmpty(rules)) {
            return Collections.emptyList();
        }
        // 2. Not configured，Enabled by default
        DataPermission dataPermission = DataPermissionContextHolder.get();
        if (dataPermission == null) {
            return rules;
        }
        // 3. Configured，But disabled
        if (!dataPermission.enable()) {
            return Collections.emptyList();
        }

        // 4. Configured，Select only some rules
        if (ArrayUtil.isNotEmpty(dataPermission.includeRules())) {
            return rules.stream().filter(rule -> ArrayUtil.contains(dataPermission.includeRules(), rule.getClass()))
                    .collect(Collectors.toList()); // Generally there won't be too many rules，So it is not adopted HashSet Query
        }
        // 5. Configured，Exclude only some rules
        if (ArrayUtil.isNotEmpty(dataPermission.excludeRules())) {
            return rules.stream().filter(rule -> !ArrayUtil.contains(dataPermission.excludeRules(), rule.getClass()))
                    .collect(Collectors.toList()); // Generally there won't be too many rules，So it is not adopted HashSet Query
        }
        // 6. Configured，All rules
        return rules;
    }

}
