package cn.econets.blossom.framework.permission.core.db;

import cn.econets.blossom.framework.common.util.collection.SetUtils;
import cn.econets.blossom.framework.mybatis.core.util.MyBatisUtils;
import cn.econets.blossom.framework.permission.core.rule.DataPermissionRule;
import cn.econets.blossom.framework.permission.core.rule.DataPermissionRuleFactory;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.sf.jsqlparser.expression.*;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.*;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Data permission interceptor，Passed {@link DataPermissionRule} Data permission rules，Rewrite SQL way to achieve
 * Main SQL Rewrite method，Visible {@link #builderExpression(Expression, List)} Method
 *
 * Overall code implementation，Reference {@link com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor} Realization。
 * So every time MyBatis Plus Upgrade，Required Review Whether the specific implementation has changed！
 *
 */
@RequiredArgsConstructor
public class DataPermissionDatabaseInterceptor extends JsqlParserSupport implements InnerInterceptor {

    private final DataPermissionRuleFactory ruleFactory;

    @Getter
    private final MappedStatementCache mappedStatementCache = new MappedStatementCache();

    @Override // SELECT Scene
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        // Get Mapper Corresponding data permission rules
        List<DataPermissionRule> rules = ruleFactory.getDataPermissionRule(ms.getId());
        if (mappedStatementCache.noRewritable(ms, rules)) { // If no rewrite is required，Skip
            return;
        }

        PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
        try {
            // Initialize context
            ContextHolder.init(rules);
            // Processing SQL
            mpBs.sql(parserSingle(mpBs.sql(), null));
        } finally {
            // Add whether the cache needs to be rewritten
            addMappedStatementCache(ms);
            // Clear context
            ContextHolder.clear();
        }
    }

    @Override // Only process UPDATE / DELETE Scene，Not processed INSERT Scene（Because INSERT No data permission required)
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();
        SqlCommandType sct = ms.getSqlCommandType();
        if (sct == SqlCommandType.UPDATE || sct == SqlCommandType.DELETE) {
            // Get Mapper Corresponding data permission rules
            List<DataPermissionRule> rules = ruleFactory.getDataPermissionRule(ms.getId());
            if (mappedStatementCache.noRewritable(ms, rules)) { // If no rewrite is required，Skip
                return;
            }

            PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
            try {
                // Initialize context
                ContextHolder.init(rules);
                // Processing SQL
                mpBs.sql(parserMulti(mpBs.sql(), null));
            } finally {
                // Add whether the cache needs to be rewritten
                addMappedStatementCache(ms);
                // Clear context
                ContextHolder.clear();
            }
        }
    }

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
        processSelectBody(select.getSelectBody());
        List<WithItem> withItemsList = select.getWithItemsList();
        if (!CollectionUtils.isEmpty(withItemsList)) {
            withItemsList.forEach(this::processSelectBody);
        }
    }

    /**
     * update Statement Processing
     */
    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        final Table table = update.getTable();
        update.setWhere(this.builderExpression(update.getWhere(), table));
    }

    /**
     * delete Statement Processing
     */
    @Override
    protected void processDelete(Delete delete, int index, String sql, Object obj) {
        delete.setWhere(this.builderExpression(delete.getWhere(), delete.getTable()));
    }

    // ========== Japanese TenantLineInnerInterceptor Consistent logic ==========

    protected void processSelectBody(SelectBody selectBody) {
        if (selectBody == null) {
            return;
        }
        if (selectBody instanceof PlainSelect) {
            processPlainSelect((PlainSelect) selectBody);
        } else if (selectBody instanceof WithItem) {
            WithItem withItem = (WithItem) selectBody;
            processSelectBody(withItem.getSubSelect().getSelectBody());
        } else {
            SetOperationList operationList = (SetOperationList) selectBody;
            List<SelectBody> selectBodyList = operationList.getSelects();
            if (CollectionUtils.isNotEmpty(selectBodyList)) {
                selectBodyList.forEach(this::processSelectBody);
            }
        }
    }

    /**
     * Processing PlainSelect
     */
    protected void processPlainSelect(PlainSelect plainSelect) {
        //#3087 github
        List<SelectItem> selectItems = plainSelect.getSelectItems();
        if (CollectionUtils.isNotEmpty(selectItems)) {
            selectItems.forEach(this::processSelectItem);
        }

        // Processing where Subquery in
        Expression where = plainSelect.getWhere();
        processWhereSubSelect(where);

        // Processing fromItem
        FromItem fromItem = plainSelect.getFromItem();
        List<Table> list = processFromItem(fromItem);
        List<Table> mainTables = new ArrayList<>(list);

        // Processing join
        List<Join> joins = plainSelect.getJoins();
        if (CollectionUtils.isNotEmpty(joins)) {
            mainTables = processJoins(mainTables, joins);
        }

        // When there is mainTable Time，Proceed where Conditional addition
        if (CollectionUtils.isNotEmpty(mainTables)) {
            plainSelect.setWhere(builderExpression(where, mainTables));
        }
    }

    private List<Table> processFromItem(FromItem fromItem) {
        // Handling expressions enclosed in parentheses
        while (fromItem instanceof ParenthesisFromItem) {
            fromItem = ((ParenthesisFromItem) fromItem).getFromItem();
        }

        List<Table> mainTables = new ArrayList<>();
        // None join Processing logic
        if (fromItem instanceof Table) {
            Table fromTable = (Table) fromItem;
            mainTables.add(fromTable);
        } else if (fromItem instanceof SubJoin) {
            // SubJoin The type needs to be added where Conditions
            List<Table> tables = processSubJoin((SubJoin) fromItem);
            mainTables.addAll(tables);
        } else {
            // Process it fromItem
            processOtherFromItem(fromItem);
        }
        return mainTables;
    }

    /**
     * ProcessingwhereSubquery within condition
     * <p>
     * Support is as follows:
     * 1. in
     * 2. =
     * 3. >
     * 4. <
     * 5. >=
     * 6. <=
     * 7. <>
     * 8. EXISTS
     * 9. NOT EXISTS
     * <p>
     * Prerequisites:
     * 1. Subqueries must be enclosed in parentheses
     * 2. Subqueries are usually placed on the right side of comparison operators
     *
     * @param where where Conditions
     */
    protected void processWhereSubSelect(Expression where) {
        if (where == null) {
            return;
        }
        if (where instanceof FromItem) {
            processOtherFromItem((FromItem) where);
            return;
        }
        if (where.toString().indexOf("SELECT") > 0) {
            // With subquery
            if (where instanceof BinaryExpression) {
                // Comparison symbol , and , or , Wait
                BinaryExpression expression = (BinaryExpression) where;
                processWhereSubSelect(expression.getLeftExpression());
                processWhereSubSelect(expression.getRightExpression());
            } else if (where instanceof InExpression) {
                // in
                InExpression expression = (InExpression) where;
                Expression inExpression = expression.getRightExpression();
                if (inExpression instanceof SubSelect) {
                    processSelectBody(((SubSelect) inExpression).getSelectBody());
                }
            } else if (where instanceof ExistsExpression) {
                // exists
                ExistsExpression expression = (ExistsExpression) where;
                processWhereSubSelect(expression.getRightExpression());
            } else if (where instanceof NotExpression) {
                // not exists
                NotExpression expression = (NotExpression) where;
                processWhereSubSelect(expression.getExpression());
            } else if (where instanceof Parenthesis) {
                Parenthesis expression = (Parenthesis) where;
                processWhereSubSelect(expression.getExpression());
            }
        }
    }

    protected void processSelectItem(SelectItem selectItem) {
        if (selectItem instanceof SelectExpressionItem) {
            SelectExpressionItem selectExpressionItem = (SelectExpressionItem) selectItem;
            if (selectExpressionItem.getExpression() instanceof SubSelect) {
                processSelectBody(((SubSelect) selectExpressionItem.getExpression()).getSelectBody());
            } else if (selectExpressionItem.getExpression() instanceof Function) {
                processFunction((Function) selectExpressionItem.getExpression());
            }
        }
    }

    /**
     * Processing function
     * <p>Support: 1. select fun(args..) 2. select fun1(fun2(args..),args..)<p>
     * <p> fixed gitee pulls/141</p>
     *
     * @param function
     */
    protected void processFunction(Function function) {
        ExpressionList parameters = function.getParameters();
        if (parameters != null) {
            parameters.getExpressions().forEach(expression -> {
                if (expression instanceof SubSelect) {
                    processSelectBody(((SubSelect) expression).getSelectBody());
                } else if (expression instanceof Function) {
                    processFunction((Function) expression);
                }
            });
        }
    }

    /**
     * Handling subqueries, etc.
     */
    protected void processOtherFromItem(FromItem fromItem) {
        // Remove brackets
        while (fromItem instanceof ParenthesisFromItem) {
            fromItem = ((ParenthesisFromItem) fromItem).getFromItem();
        }

        if (fromItem instanceof SubSelect) {
            SubSelect subSelect = (SubSelect) fromItem;
            if (subSelect.getSelectBody() != null) {
                processSelectBody(subSelect.getSelectBody());
            }
        } else if (fromItem instanceof ValuesList) {
            logger.debug("Perform a subQuery, if you do not give us feedback");
        } else if (fromItem instanceof LateralSubSelect) {
            LateralSubSelect lateralSubSelect = (LateralSubSelect) fromItem;
            if (lateralSubSelect.getSubSelect() != null) {
                SubSelect subSelect = lateralSubSelect.getSubSelect();
                if (subSelect.getSelectBody() != null) {
                    processSelectBody(subSelect.getSelectBody());
                }
            }
        }
    }

    /**
     * Processing sub join
     *
     * @param subJoin subJoin
     * @return Table subJoin The main table in
     */
    private List<Table> processSubJoin(SubJoin subJoin) {
        List<Table> mainTables = new ArrayList<>();
        if (subJoin.getJoinList() != null) {
            List<Table> list = processFromItem(subJoin.getLeft());
            mainTables.addAll(list);
            mainTables = processJoins(mainTables, subJoin.getJoinList());
        }
        return mainTables;
    }

    /**
     * Processing joins
     *
     * @param mainTables Can be null
     * @param joins      join Collection
     * @return List<Table> Right join query Table List
     */
    private List<Table> processJoins(List<Table> mainTables, List<Join> joins) {
        // join The final main table in the expression
        Table mainTable = null;
        // Current join Left table
        Table leftTable = null;

        if (mainTables == null) {
            mainTables = new ArrayList<>();
        } else if (mainTables.size() == 1) {
            mainTable = mainTables.get(0);
            leftTable = mainTable;
        }

        //For on The expression is written at the end join，Need to record the previous multiple on Table name
        Deque<List<Table>> onTableDeque = new LinkedList<>();
        for (Join join : joins) {
            // Processing on Expression
            FromItem joinItem = join.getRightItem();

            // Get current join Table，subJoint Can be regarded as a table
            List<Table> joinTables = null;
            if (joinItem instanceof Table) {
                joinTables = new ArrayList<>();
                joinTables.add((Table) joinItem);
            } else if (joinItem instanceof SubJoin) {
                joinTables = processSubJoin((SubJoin) joinItem);
            }

            if (joinTables != null) {

                // If it is an implicit inner join
                if (join.isSimple()) {
                    mainTables.addAll(joinTables);
                    continue;
                }

                // Whether to ignore the current table
                Table joinTable = joinTables.get(0);

                List<Table> onTables = null;
                // If not ignore，and it is a right join，Then record the current table
                if (join.isRight()) {
                    mainTable = joinTable;
                    if (leftTable != null) {
                        onTables = Collections.singletonList(leftTable);
                    }
                } else if (join.isLeft()) {
                    onTables = Collections.singletonList(joinTable);
                } else if (join.isInner()) {
                    if (mainTable == null) {
                        onTables = Collections.singletonList(joinTable);
                    } else {
                        onTables = Arrays.asList(mainTable, joinTable);
                    }
                    mainTable = null;
                }

                mainTables = new ArrayList<>();
                if (mainTable != null) {
                    mainTables.add(mainTable);
                }

                // Get join Suffix on Expression list
                Collection<Expression> originOnExpressions = join.getOnExpressions();
                // Normal join on There is only one expression，Process immediately
                if (originOnExpressions.size() == 1 && onTables != null) {
                    List<Expression> onExpressions = new LinkedList<>();
                    onExpressions.add(builderExpression(originOnExpressions.iterator().next(), onTables));
                    join.setOnExpressions(onExpressions);
                    leftTable = joinTable;
                    continue;
                }
                // Push table name onto stack，Ignored table push null，So as not to be processed later
                onTableDeque.push(onTables);
                // Multiple suffixes on Uniform processing of expressions
                if (originOnExpressions.size() > 1) {
                    Collection<Expression> onExpressions = new LinkedList<>();
                    for (Expression originOnExpression : originOnExpressions) {
                        List<Table> currentTableList = onTableDeque.poll();
                        if (CollectionUtils.isEmpty(currentTableList)) {
                            onExpressions.add(originOnExpression);
                        } else {
                            onExpressions.add(builderExpression(originOnExpression, currentTableList));
                        }
                    }
                    join.setOnExpressions(onExpressions);
                }
                leftTable = joinTable;
            } else {
                processOtherFromItem(joinItem);
                leftTable = null;
            }
        }

        return mainTables;
    }

    // ========== Japanese TenantLineInnerInterceptor The logic of differences exists：Key，Realize the splicing of permission conditions ==========

    /**
     * Processing conditions
     *
     * @param currentExpression Current where Conditions
     * @param table             Single table
     */
    protected Expression builderExpression(Expression currentExpression, Table table) {
        return this.builderExpression(currentExpression, Collections.singletonList(table));
    }

    /**
     * Processing conditions
     *
     * @param currentExpression Current where Conditions
     * @param tables Multiple tables
     */
    protected Expression builderExpression(Expression currentExpression, List<Table> tables) {
        // No table needs to be processed, return directly
        if (CollectionUtils.isEmpty(tables)) {
            return currentExpression;
        }

        // First step，Get Table Corresponding data permission conditions
        Expression dataPermissionExpression = null;
        for (Table table : tables) {
            // Build permissions for each table Expression Conditions
            Expression expression = buildDataPermissionExpression(table);
            if (expression == null) {
                continue;
            }
            // Merge to dataPermissionExpression Medium
            dataPermissionExpression = dataPermissionExpression == null ? expression
                    : new AndExpression(dataPermissionExpression, expression);
        }

        // Step 2，Merge multiple Expression Conditions
        if (dataPermissionExpression == null) {
            return currentExpression;
        }
        if (currentExpression == null) {
            return dataPermissionExpression;
        }
        // ① If the expression is Or，Required (currentExpression) AND dataPermissionExpression
        if (currentExpression instanceof OrExpression) {
            return new AndExpression(new Parenthesis(currentExpression), dataPermissionExpression);
        }
        // ② If the expression is And，Return directly where AND dataPermissionExpression
        return new AndExpression(currentExpression, dataPermissionExpression);
    }

    /**
     * Build data permissions for the specified table Expression Filter conditions
     *
     * @param table Table
     * @return Expression Filter conditions
     */
    private Expression buildDataPermissionExpression(Table table) {
        // Generation conditions
        Expression allExpression = null;
        for (DataPermissionRule rule : ContextHolder.getRules()) {
            // Judge whether the table name matches
            if (!rule.getTableNames().contains(table.getName())) {
                continue;
            }
            // If there is a matching rule，Description can be rewritten。
            // Why not? allExpression Rewrite only if not empty？Generating column = value When filtering conditions，Because value Does not exist，resulted in not being rewritten。
            // This results in no first time value，Marked as not needing to be rewritten；But there was a second time value，You will need to rewrite it at this time。
            ContextHolder.setRewrite(true);

            // Conditions of a single rule
            String tableName = MyBatisUtils.getTableName(table);
            Expression oneExpress = rule.getExpression(tableName, table.getAlias());
            if (oneExpress == null){
                continue;
            }
            // Splice to allExpression Medium
            allExpression = allExpression == null ? oneExpress
                    : new AndExpression(allExpression, oneExpress);
        }

        return allExpression;
    }

    /**
     * Judgement SQL Rewrite。If not rewritten，Then add to {@link MappedStatementCache} Medium
     *
     * @param ms MappedStatement
     */
    private void addMappedStatementCache(MappedStatement ms) {
        if (ContextHolder.getRewrite()) {
            return;
        }
        // No rewrite，Add
        mappedStatementCache.addNoRewritable(ms, ContextHolder.getRules());
    }

    /**
     * SQL Parsing context，Convenient transparent transmission {@link DataPermissionRule} Rules
     *
     */
    static final class ContextHolder {

        /**
         * This {@link MappedStatement} Corresponding rules
         */
        private static final ThreadLocal<List<DataPermissionRule>> RULES = ThreadLocal.withInitial(Collections::emptyList);
        /**
         * SQL Whether to rewrite
         */
        private static final ThreadLocal<Boolean> REWRITE = ThreadLocal.withInitial(() -> Boolean.FALSE);

        public static void init(List<DataPermissionRule> rules) {
            RULES.set(rules);
            REWRITE.set(false);
        }

        public static void clear() {
            RULES.remove();
            REWRITE.remove();
        }

        public static boolean getRewrite() {
            return REWRITE.get();
        }

        public static void setRewrite(boolean rewrite) {
            REWRITE.set(rewrite);
        }

        public static List<DataPermissionRule> getRules() {
            return RULES.get();
        }

    }

    /**
     * {@link MappedStatement} Cache
     * Currently mainly used for，Record {@link DataPermissionRule} Whether to specify {@link MappedStatement} Invalid
     * If invalid，can be avoided SQL Analysis of，Speed ​​up
     *
     */
    static final class MappedStatementCache {

        /**
         * Specify data permission rules，To specify MappedStatement No need to rewrite（Not effective)Cache
         *
         * value：{@link MappedStatement#getId()} Number
         */
        @Getter
        private final Map<Class<? extends DataPermissionRule>, Set<String>> noRewritableMappedStatements = new ConcurrentHashMap<>();

        /**
         * Judge whether rewriting is not necessary
         * ps：Although it has a bit of Chinese English，But it just needs to be easy to understand
         *
         * @param ms MappedStatement
         * @param rules Data permission rule array
         * @return Whether no rewriting is required
         */
        public boolean noRewritable(MappedStatement ms, List<DataPermissionRule> rules) {
            // If the rule is empty，Instructions do not need to be rewritten
            if (CollUtil.isEmpty(rules)) {
                return true;
            }
            // Any rule is not present noRewritableMap Medium，It may need to be rewritten
            for (DataPermissionRule rule : rules) {
                Set<String> mappedStatementIds = noRewritableMappedStatements.get(rule.getClass());
                if (!CollUtil.contains(mappedStatementIds, ms.getId())) {
                    return false;
                }
            }
            return true;
        }

        /**
         * Add without rewriting MappedStatement
         *
         * @param ms MappedStatement
         * @param rules Data permission rule array
         */
        public void addNoRewritable(MappedStatement ms, List<DataPermissionRule> rules) {
            for (DataPermissionRule rule : rules) {
                Set<String> mappedStatementIds = noRewritableMappedStatements.get(rule.getClass());
                if (CollUtil.isNotEmpty(mappedStatementIds)) {
                    mappedStatementIds.add(ms.getId());
                } else {
                    noRewritableMappedStatements.put(rule.getClass(), SetUtils.asSet(ms.getId()));
                }
            }
        }

        /**
         * Clear cache
         * Currently mainly provided for unit testing
         */
        public void clear() {
            noRewritableMappedStatements.clear();
        }

    }

}
