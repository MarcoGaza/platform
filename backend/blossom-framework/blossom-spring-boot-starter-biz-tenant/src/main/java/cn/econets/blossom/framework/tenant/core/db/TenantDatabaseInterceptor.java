package cn.econets.blossom.framework.tenant.core.db;

import cn.econets.blossom.framework.tenant.config.TenantProperties;
import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

import java.util.HashSet;
import java.util.Set;

/**
 * Based on MyBatis Plus Multi-tenant function，Realization DB Multi-tenant functionality at the level
 *
 */
public class TenantDatabaseInterceptor implements TenantLineHandler {

    private final Set<String> ignoreTables = new HashSet<>();

    public TenantDatabaseInterceptor(TenantProperties properties) {
        // Different DB Next，Different uppercase and lowercase usage，So you need to add them all
        properties.getIgnoreTables().forEach(table -> {
            ignoreTables.add(table.toLowerCase());
            ignoreTables.add(table.toUpperCase());
        });
        // In OracleKeyGenerator Medium，When generating primary key，Will query this table，After querying this table，will automatically splice TENANT_ID Results in an error
        ignoreTables.add("DUAL");
    }

    @Override
    public Expression getTenantId() {
        return new LongValue(TenantContextHolder.getRequiredTenantId());
    }

    @Override
    public boolean ignoreTable(String tableName) {
        return TenantContextHolder.isIgnore() // Situation 1，Ignore multi-tenancy globally
            || CollUtil.contains(ignoreTables, tableName); // Case 2，Ignore multi-tenant tables
    }

}
