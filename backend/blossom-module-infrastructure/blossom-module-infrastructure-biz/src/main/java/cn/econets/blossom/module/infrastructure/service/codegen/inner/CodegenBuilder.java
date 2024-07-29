package cn.econets.blossom.module.infrastructure.service.codegen.inner;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.infrastructure.convert.codegen.CodegenConvert;
import cn.econets.blossom.module.infrastructure.dal.dataobject.codegen.CodegenColumnDO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.codegen.CodegenTableDO;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenColumnHtmlTypeEnum;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenColumnListConditionEnum;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenTemplateTypeEnum;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

import static cn.hutool.core.text.CharSequenceUtil.*;
import static cn.hutool.core.util.RandomUtil.randomEle;
import static cn.hutool.core.util.RandomUtil.randomInt;

/**
 * Code generator Builder，Responsible：
 * 1. Database table {@link TableInfo} Definition，Build {@link CodegenTableDO}
 * 2. Change the database columns {@link TableField} Structure definition，Completed {@link CodegenColumnDO}
 */
@Component
public class CodegenBuilder {

    /**
     * Field name and {@link CodegenColumnListConditionEnum} Default mapping
     * Attention，Field matching is based on suffix
     */
    private static final Map<String, CodegenColumnListConditionEnum> COLUMN_LIST_OPERATION_CONDITION_MAPPINGS =
            MapUtil.<String, CodegenColumnListConditionEnum>builder()
                    .put("name", CodegenColumnListConditionEnum.LIKE)
                    .put("time", CodegenColumnListConditionEnum.BETWEEN)
                    .put("date", CodegenColumnListConditionEnum.BETWEEN)
                    .build();

    /**
     * Field name and {@link CodegenColumnHtmlTypeEnum} Default mapping
     * Attention，Field matching is based on suffix
     */
    private static final Map<String, CodegenColumnHtmlTypeEnum> COLUMN_HTML_TYPE_MAPPINGS =
            MapUtil.<String, CodegenColumnHtmlTypeEnum>builder()
                    .put("status", CodegenColumnHtmlTypeEnum.RADIO)
                    .put("sex", CodegenColumnHtmlTypeEnum.RADIO)
                    .put("type", CodegenColumnHtmlTypeEnum.SELECT)
                    .put("image", CodegenColumnHtmlTypeEnum.IMAGE_UPLOAD)
                    .put("file", CodegenColumnHtmlTypeEnum.FILE_UPLOAD)
                    .put("content", CodegenColumnHtmlTypeEnum.EDITOR)
                    .put("description", CodegenColumnHtmlTypeEnum.EDITOR)
                    .put("demo", CodegenColumnHtmlTypeEnum.EDITOR)
                    .put("time", CodegenColumnHtmlTypeEnum.DATETIME)
                    .put("date", CodegenColumnHtmlTypeEnum.DATETIME)
                    .build();

    /**
     * Field name of multi-tenant ID
     */
    public static final String TENANT_ID_FIELD = "tenantId";
    /**
     * {@link cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO} Fields
     */
    public static final Set<String> BASE_DO_FIELDS = new HashSet<>();
    /**
     * Add new operation，Fields that do not need to be passed
     */
    private static final Set<String> CREATE_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet("id");
    /**
     * Modify operation，Fields that do not need to be passed
     */
    private static final Set<String> UPDATE_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet();
    /**
     * List operation conditions，Fields that do not need to be passed
     */
    private static final Set<String> LIST_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet("id");
    /**
     * Results of list operations，Fields that do not need to be returned
     */
    private static final Set<String> LIST_OPERATION_RESULT_EXCLUDE_COLUMN = Sets.newHashSet();

    static {
        Arrays.stream(ReflectUtil.getFields(BaseDO.class)).forEach(field -> BASE_DO_FIELDS.add(field.getName()));
        BASE_DO_FIELDS.add(TENANT_ID_FIELD);
        // Processing OPERATION Related fields
        CREATE_OPERATION_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        UPDATE_OPERATION_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        LIST_OPERATION_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        LIST_OPERATION_EXCLUDE_COLUMN.remove("createTime"); // Creation time，May still need to be passed
        LIST_OPERATION_RESULT_EXCLUDE_COLUMN.addAll(BASE_DO_FIELDS);
        LIST_OPERATION_RESULT_EXCLUDE_COLUMN.remove("createTime"); // Creation time，Still need to return
    }

    public CodegenTableDO buildTable(TableInfo tableInfo) {
        CodegenTableDO table = CodegenConvert.INSTANCE.convert(tableInfo);
        initTableDefault(table);
        return table;
    }

    /**
     * Initialization Table Default fields of the table
     *
     * @param table Table definition
     */
    private void initTableDefault(CodegenTableDO table) {
        // With system_dept Give an example。moduleName for system、businessName for dept、className for Dept
        // If you want to System Prefix，You can manually change the【Code Generation - Modify build configuration - Basic Information】，Change the entity class name to SystemDept That's it
        String tableName = table.getTableName().toLowerCase();
        // First step，_ Before the prefix，As module Name；Step 2，moduleName Must be lowercase；
        table.setModuleName(subBefore(tableName, '_', false).toLowerCase());
        // First step，The first one _ Behind the prefix，As module Name; Step 2，There may be multiple _ Situation，Convert to camel case; Step 3，businessName Must be lowercase；
        table.setBusinessName(toCamelCase(subAfter(tableName, '_', false)).toLowerCase());
        // Hump + Capitalize the first letter；First step，The first one _ Behind the prefix，As class Name；Step 2，CamelCase
        table.setClassName(upperFirst(toCamelCase(subAfter(tableName, '_', false))));
        // Remove the end table，As a class description
        table.setClassComment(StrUtil.removeSuffixIgnoreCase(table.getTableComment(), "Table"));
        table.setTemplateType(CodegenTemplateTypeEnum.ONE.getType());
    }

    public List<CodegenColumnDO> buildColumns(Long tableId, List<TableField> tableFields) {
        List<CodegenColumnDO> columns = CodegenConvert.INSTANCE.convertList(tableFields);
        int index = 1;
        for (CodegenColumnDO column : columns) {
            column.setTableId(tableId);
            column.setOrdinalPosition(index++);
            // Special treatment：Byte => Integer
            if (Byte.class.getSimpleName().equals(column.getJavaType())) {
                column.setJavaType(Integer.class.getSimpleName());
            }
            // Initialization Column Default field of the column
            processColumnOperation(column); // Processing CRUD Default values ​​of related fields
            processColumnUI(column); // Processing UI Default values ​​of related fields
            processColumnExample(column); // Processing fields swagger example Example
        }
        return columns;
    }

    private void processColumnOperation(CodegenColumnDO column) {
        // Processing createOperation Field
        column.setCreateOperation(!CREATE_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                && !column.getPrimaryKey()); // For primary key，No need to pass when creating
        // Processing updateOperation Field
        column.setUpdateOperation(!UPDATE_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                || column.getPrimaryKey()); // For primary key，Need to pass when updating
        // Processing listOperation Field
        column.setListOperation(!LIST_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                && !column.getPrimaryKey()); // For primary key，List filtering does not require passing
        // Processing listOperationCondition Field
        COLUMN_LIST_OPERATION_CONDITION_MAPPINGS.entrySet().stream()
                .filter(entry -> StrUtil.endWithIgnoreCase(column.getJavaField(), entry.getKey()))
                .findFirst().ifPresent(entry -> column.setListOperationCondition(entry.getValue().getCondition()));
        if (column.getListOperationCondition() == null) {
            column.setListOperationCondition(CodegenColumnListConditionEnum.EQ.getCondition());
        }
        // Processing listOperationResult Field
        column.setListOperationResult(!LIST_OPERATION_RESULT_EXCLUDE_COLUMN.contains(column.getJavaField()));
    }

    private void processColumnUI(CodegenColumnDO column) {
        // Match based on suffix
        COLUMN_HTML_TYPE_MAPPINGS.entrySet().stream()
                .filter(entry -> StrUtil.endWithIgnoreCase(column.getJavaField(), entry.getKey()))
                .findFirst().ifPresent(entry -> column.setHtmlType(entry.getValue().getType()));
        // If yes Boolean Type，Set to radio Type.
        if (Boolean.class.getSimpleName().equals(column.getJavaType())) {
            column.setHtmlType(CodegenColumnHtmlTypeEnum.RADIO.getType());
        }
        // If yes LocalDateTime Type，Then set to datetime Type
        if (LocalDateTime.class.getSimpleName().equals(column.getJavaType())) {
            column.setHtmlType(CodegenColumnHtmlTypeEnum.DATETIME.getType());
        }
        // Backstop，Set the default to input Type
        if (column.getHtmlType() == null) {
            column.setHtmlType(CodegenColumnHtmlTypeEnum.INPUT.getType());
        }
    }

    /**
     * Processing fields swagger example Example
     *
     * @param column Field
     */
    private void processColumnExample(CodegenColumnDO column) {
        // id、price、count It may be an integer suffix
        if (StrUtil.endWithAnyIgnoreCase(column.getJavaField(), "id", "price", "count")) {
            column.setExample(String.valueOf(randomInt(1, Short.MAX_VALUE)));
            return;
        }
        // name
        if (StrUtil.endWithIgnoreCase(column.getJavaField(), "name")) {
            column.setExample(randomEle(new String[]{"Zhang San", "Li Si", "Wang Wu", "Zhao Liu", "econets"}));
            return;
        }
        // status
        if (StrUtil.endWithAnyIgnoreCase(column.getJavaField(), "status", "type")) {
            column.setExample(randomEle(new String[]{"1", "2"}));
            return;
        }
        // url
        if (StrUtil.endWithIgnoreCase(column.getColumnName(), "url")) {
            column.setExample("https://www.econets.cn");
            return;
        }
        // reason
        if (StrUtil.endWithIgnoreCase(column.getColumnName(), "reason")) {
            column.setExample(randomEle(new String[]{"Dislike", "Incorrect", "Not good", "Not fragrant"}));
            return;
        }
        // description、memo、remark
        if (StrUtil.endWithAnyIgnoreCase(column.getColumnName(), "description", "memo", "remark")) {
            column.setExample(randomEle(new String[]{"Guess", "Whatever", "You are right"}));
            return;
        }
    }

}
