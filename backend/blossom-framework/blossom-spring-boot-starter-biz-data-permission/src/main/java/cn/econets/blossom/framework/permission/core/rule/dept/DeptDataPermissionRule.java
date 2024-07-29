package cn.econets.blossom.framework.permission.core.rule.dept;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.util.MyBatisUtils;
import cn.econets.blossom.framework.permission.core.rule.DataPermissionRule;
import cn.econets.blossom.framework.security.core.LoginUser;
import cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils;
import cn.econets.blossom.module.system.api.permission.PermissionApi;
import cn.econets.blossom.module.system.api.permission.dto.DeptDataPermissionRespDTO;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Department-based {@link DataPermissionRule} Data permission rule implementation
 *
 * Attention，Use DeptDataPermissionRule Time，It is necessary to ensure that there is dept_id Department number field，Customizable。
 *
 * In actual business scenarios，There will be a classic problem？When a user modifies a department，Redundant dept_id Does it need to be modified?？
 * 1. Under normal circumstances，dept_id No modification，will cause users to not be able to see previous data。【blossom-server Adopt this solution】
 * 2. In some cases，I hope this user can still see the previous data，There are two ways to solve it：【You need to modify this DeptDataPermissionRule Implementation code】
 *  1）Write a script for cleaning data，Will dept_id Change to the number of the new department；【Suggestions】
 *      The final filter condition is WHERE dept_id = ?
 *  2）If you want to clean the data，The amount of data involved may be large，You can also use user_id Filtering method，Need to get it at this time dept_id All corresponding user_id User Number；
 *      The final filter condition is WHERE user_id IN (?, ?, ? ...)
 *  3）Want to ensure the originality dept_id Japanese user_id You can see it all，Use at this time dept_id Japanese user_id Filter together；
 *      The final filter condition is WHERE dept_id = ? OR user_id IN (?, ?, ? ...)
 *
 */
@AllArgsConstructor
@Slf4j
public class DeptDataPermissionRule implements DataPermissionRule {

    /**
     * LoginUser of Context Cache Key
     */
    protected static final String CONTEXT_KEY = DeptDataPermissionRule.class.getSimpleName();

    private static final String DEPT_COLUMN_NAME = "dept_id";
    private static final String USER_COLUMN_NAME = "user_id";

    static final Expression EXPRESSION_NULL = new NullValue();

    private final PermissionApi permissionApi;

    /**
     * Table field configuration based on department
     * Under normal circumstances，The department number field of each table is dept_id，Customize through this configuration。
     *
     * key：Table name
     * value：Field name
     */
    private final Map<String, String> deptColumns = new HashMap<>();
    /**
     * User-based table field configuration
     * Under normal circumstances，The department number field of each table is dept_id，Customize through this configuration。
     *
     * key：Table name
     * value：Field name
     */
    private final Map<String, String> userColumns = new HashMap<>();
    /**
     * All table names，Yes {@link #deptColumns} Japanese {@link #userColumns} Collection of
     */
    private final Set<String> TABLE_NAMES = new HashSet<>();

    @Override
    public Set<String> getTableNames() {
        return TABLE_NAMES;
    }

    @Override
    public Expression getExpression(String tableName, Alias tableAlias) {
        // Only when there is a logged in user，Data permissions will be processed later
        LoginUser loginUser = SecurityFrameworkUtils.getLoginUser();
        if (loginUser == null) {
            return null;
        }
        // Only administrator-type users，Data permissions will be processed later
        if (ObjectUtil.notEqual(loginUser.getUserType(), UserTypeEnum.ADMIN.getValue())) {
            return null;
        }

        // Get data permissions
        DeptDataPermissionRespDTO deptDataPermission = loginUser.getContext(CONTEXT_KEY, DeptDataPermissionRespDTO.class);
        // Cannot be obtained from the context，Then call the logic to obtain
        if (deptDataPermission == null) {
            deptDataPermission = permissionApi.getDeptDataPermission(loginUser.getId());
            if (deptDataPermission == null) {
                log.error("[getExpression][LoginUser({}) Access data permissions are null]", JsonUtils.toJsonString(loginUser));
                throw new NullPointerException(String.format("LoginUser(%d) Table(%s/%s) Data permissions not returned",
                        loginUser.getId(), tableName, tableAlias.getName()));
            }
            // Add to context，Avoid repeated counting
            loginUser.setContext(CONTEXT_KEY, deptDataPermission);
        }

        // Situation 1，If yes ALL View all，No splicing conditions are required
        if (deptDataPermission.getAll()) {
            return null;
        }

        // Situation 2，Cannot view department，Can't view myself again，Then explain 100% No permission
        if (CollUtil.isEmpty(deptDataPermission.getDeptIds())
            && Boolean.FALSE.equals(deptDataPermission.getSelf())) {
            return new EqualsTo(null, null); // WHERE null = null，The returned data can be guaranteed to be empty
        }

        // Situation 3，Splicing Dept Japanese User Conditions，The last combination
        Expression deptExpression = buildDeptExpression(tableName,tableAlias, deptDataPermission.getDeptIds());
        Expression userExpression = buildUserExpression(tableName, tableAlias, deptDataPermission.getSelf(), loginUser.getId());
        if (deptExpression == null && userExpression == null) {
            // TODO When the conditions cannot be obtained，Do not throw exceptions for now，No data is returned
            log.warn("[getExpression][LoginUser({}) Table({}/{}) DeptDataPermission({}) The build condition is empty]",
                    JsonUtils.toJsonString(loginUser), tableName, tableAlias, JsonUtils.toJsonString(deptDataPermission));
//            throw new NullPointerException(String.format("LoginUser(%d) Table(%s/%s) The build condition is empty", loginUser.getId(), tableName, tableAlias.getName()));
            return EXPRESSION_NULL;
        }
        if (deptExpression == null) {
            return userExpression;
        }
        if (userExpression == null) {
            return deptExpression;
        }
        // Currently，If there is a designated department + Can view yourself，Adopted OR Conditions。That is，WHERE (dept_id IN ? OR user_id = ?)
        return new Parenthesis(new OrExpression(deptExpression, userExpression));
    }

    private Expression buildDeptExpression(String tableName, Alias tableAlias, Set<Long> deptIds) {
        // If no configuration exists，No need to be a condition
        String columnName = deptColumns.get(tableName);
        if (StrUtil.isEmpty(columnName)) {
            return null;
        }
        // If empty，No conditions
        if (CollUtil.isEmpty(deptIds)) {
            return null;
        }
        // Joining conditions
        return new InExpression(MyBatisUtils.buildColumn(tableName, tableAlias, columnName),
                new ExpressionList(CollectionUtils.convertList(deptIds, LongValue::new)));
    }

    private Expression buildUserExpression(String tableName, Alias tableAlias, Boolean self, Long userId) {
        // If you don't check yourself，No need to be a condition
        if (Boolean.FALSE.equals(self)) {
            return null;
        }
        String columnName = userColumns.get(tableName);
        if (StrUtil.isEmpty(columnName)) {
            return null;
        }
        // Joining conditions
        return new EqualsTo(MyBatisUtils.buildColumn(tableName, tableAlias, columnName), new LongValue(userId));
    }

    // ==================== Add configuration ====================

    public void addDeptColumn(Class<? extends BaseDO> entityClass) {
        addDeptColumn(entityClass, DEPT_COLUMN_NAME);
    }

    public void addDeptColumn(Class<? extends BaseDO> entityClass, String columnName) {
        String tableName = TableInfoHelper.getTableInfo(entityClass).getTableName();
       addDeptColumn(tableName, columnName);
    }

    public void addDeptColumn(String tableName, String columnName) {
        deptColumns.put(tableName, columnName);
        TABLE_NAMES.add(tableName);
    }

    public void addUserColumn(Class<? extends BaseDO> entityClass) {
        addUserColumn(entityClass, USER_COLUMN_NAME);
    }

    public void addUserColumn(Class<? extends BaseDO> entityClass, String columnName) {
        String tableName = TableInfoHelper.getTableInfo(entityClass).getTableName();
        addUserColumn(tableName, columnName);
    }

    public void addUserColumn(String tableName, String columnName) {
        userColumns.put(tableName, columnName);
        TABLE_NAMES.add(tableName);
    }

}
