package cn.econets.blossom.framework.permission.core.rule;

import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;

import java.util.Set;

/**
 * Data permission rule interface
 * By implementing the interface，Custom data rules。For example，
 *
 */
public interface DataPermissionRule {

    /**
     * Return the array of table names that need to take effect
     * Why is this method needed?？Data Permission Array based on SQL Rewrite，Passed Where Return only authorized data
     *
     * If you need to get the table name based on the entity name，Callable {@link TableInfoHelper#getTableInfo(Class)} Obtain
     *
     * @return Table name array
     */
    Set<String> getTableNames();

    /**
     * Based on table name and alias，Generate corresponding WHERE / OR Filter conditions
     *
     * @param tableName Table name
     * @param tableAlias Alias，May be empty
     * @return Filter conditions Expression Expression
     */
    Expression getExpression(String tableName, Alias tableAlias);

}
