package cn.econets.blossom.framework.permission.config;

import cn.econets.blossom.framework.permission.core.rule.dept.DeptDataPermissionRule;
import cn.econets.blossom.framework.permission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import cn.econets.blossom.framework.security.core.LoginUser;
import cn.econets.blossom.module.system.api.permission.PermissionApi;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Data permissions based on department AutoConfiguration
 *
 */
@AutoConfiguration
@ConditionalOnClass(LoginUser.class)
@ConditionalOnBean(value = {PermissionApi.class, DeptDataPermissionRuleCustomizer.class})
public class DeptDataPermissionAutoConfiguration {

    @Bean
    public DeptDataPermissionRule deptDataPermissionRule(PermissionApi permissionApi,
                                                         List<DeptDataPermissionRuleCustomizer> customizers) {
        // Create DeptDataPermissionRule Object
        DeptDataPermissionRule rule = new DeptDataPermissionRule(permissionApi);
        // Completion table configuration
        customizers.forEach(customizer -> customizer.customize(rule));
        return rule;
    }

}
