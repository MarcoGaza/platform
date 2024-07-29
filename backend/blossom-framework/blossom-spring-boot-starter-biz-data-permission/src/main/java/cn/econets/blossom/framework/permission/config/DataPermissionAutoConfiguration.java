package cn.econets.blossom.framework.permission.config;

import cn.econets.blossom.framework.mybatis.core.util.MyBatisUtils;
import cn.econets.blossom.framework.permission.core.aop.DataPermissionAnnotationAdvisor;
import cn.econets.blossom.framework.permission.core.db.DataPermissionDatabaseInterceptor;
import cn.econets.blossom.framework.permission.core.rule.DataPermissionRule;
import cn.econets.blossom.framework.permission.core.rule.DataPermissionRuleFactory;
import cn.econets.blossom.framework.permission.core.rule.DataPermissionRuleFactoryImpl;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Automatic configuration class for data permissions
 *
 */
@AutoConfiguration
public class DataPermissionAutoConfiguration {

    @Bean
    public DataPermissionRuleFactory dataPermissionRuleFactory(List<DataPermissionRule> rules) {
        return new DataPermissionRuleFactoryImpl(rules);
    }

    @Bean
    public DataPermissionDatabaseInterceptor dataPermissionDatabaseInterceptor(MybatisPlusInterceptor interceptor,
                                                                               DataPermissionRuleFactory ruleFactory) {
        // Create DataPermissionDatabaseInterceptor Interceptor
        DataPermissionDatabaseInterceptor inner = new DataPermissionDatabaseInterceptor(ruleFactory);
        // Add to interceptor Medium
        // Need to be added to the first one，Mainly for the paging plugin。This is MyBatis Plus Regulations
        MyBatisUtils.addInterceptor(interceptor, inner, 0);
        return inner;
    }

    @Bean
    public DataPermissionAnnotationAdvisor dataPermissionAnnotationAdvisor() {
        return new DataPermissionAnnotationAdvisor();
    }

}
