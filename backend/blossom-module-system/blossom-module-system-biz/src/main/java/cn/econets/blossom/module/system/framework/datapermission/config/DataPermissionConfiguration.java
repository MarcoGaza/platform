package cn.econets.blossom.module.system.framework.datapermission.config;

import cn.econets.blossom.framework.permission.core.rule.dept.DeptDataPermissionRuleCustomizer;
import cn.econets.blossom.module.system.dal.dataobject.dept.DeptDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * system Module data permissions Configuration
 *
 */
@Configuration(proxyBeanMethods = false)
public class DataPermissionConfiguration {

    @Bean
    public DeptDataPermissionRuleCustomizer sysDeptDataPermissionRuleCustomizer() {
        return rule -> {
            // dept
            rule.addDeptColumn(AdminUserDO.class);
            rule.addDeptColumn(DeptDO.class, "id");
            // user
            rule.addUserColumn(AdminUserDO.class, "id");
        };
    }

}
