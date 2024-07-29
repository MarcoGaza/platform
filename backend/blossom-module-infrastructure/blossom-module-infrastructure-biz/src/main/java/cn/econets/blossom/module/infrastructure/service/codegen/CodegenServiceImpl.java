package cn.econets.blossom.module.infrastructure.service.codegen;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.CodegenCreateListReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.CodegenUpdateReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.table.CodegenTablePageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.table.DatabaseTableRespVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.codegen.CodegenColumnDO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.codegen.CodegenTableDO;
import cn.econets.blossom.module.infrastructure.dal.mysql.codegen.CodegenColumnMapper;
import cn.econets.blossom.module.infrastructure.dal.mysql.codegen.CodegenTableMapper;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenSceneEnum;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenTemplateTypeEnum;
import cn.econets.blossom.module.infrastructure.framework.codegen.config.CodegenProperties;
import cn.econets.blossom.module.infrastructure.service.codegen.inner.CodegenBuilder;
import cn.econets.blossom.module.infrastructure.service.codegen.inner.CodegenEngine;
import cn.econets.blossom.module.infrastructure.service.db.DatabaseTableService;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.module.infrastructure.enums.ErrorCodeConstants.*;

/**
 * Code Generation Service Implementation class
 *
 *
 */
@Service
public class CodegenServiceImpl implements CodegenService {

    @Resource
    private DatabaseTableService databaseTableService;

    @Resource
    private CodegenTableMapper codegenTableMapper;
    @Resource
    private CodegenColumnMapper codegenColumnMapper;

    @Resource
    private AdminUserApi userApi;

    @Resource
    private CodegenBuilder codegenBuilder;
    @Resource
    private CodegenEngine codegenEngine;

    @Resource
    private CodegenProperties codegenProperties;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> createCodegenList(Long userId, CodegenCreateListReqVO reqVO) {
        List<Long> ids = new ArrayList<>(reqVO.getTableNames().size());
        // Traverse and add。Although the efficiency will be lower，But there is no need to make it in full batches，Because it won't be so large
        reqVO.getTableNames().forEach(tableName -> ids.add(createCodegen(userId, reqVO.getDataSourceConfigId(), tableName)));
        return ids;
    }

    private Long createCodegen(Long userId, Long dataSourceConfigId, String tableName) {
        // From the database，Get the database table structure
        TableInfo tableInfo = databaseTableService.getTable(dataSourceConfigId, tableName);
        // Import
        return createCodegen0(userId, dataSourceConfigId, tableInfo);
    }

    private Long createCodegen0(Long userId, Long dataSourceConfigId, TableInfo tableInfo) {
        // Verify that the imported tables and fields are not empty
        validateTableInfo(tableInfo);
        // Check if it already exists
        if (codegenTableMapper.selectByTableNameAndDataSourceConfigId(tableInfo.getName(),
                dataSourceConfigId) != null) {
            throw exception(CODEGEN_TABLE_EXISTS);
        }

        // Build CodegenTableDO Object，Insert into DB Medium
        CodegenTableDO table = codegenBuilder.buildTable(tableInfo);
        table.setDataSourceConfigId(dataSourceConfigId);
        table.setScene(CodegenSceneEnum.ADMIN.getScene()); // Under default configuration，Use the template of the management backend
        table.setFrontType(codegenProperties.getFrontType());
        table.setAuthor(userApi.getUser(userId).getNickname());
        codegenTableMapper.insert(table);

        // Build CodegenColumnDO Array，Insert into DB Medium
        List<CodegenColumnDO> columns = codegenBuilder.buildColumns(table.getId(), tableInfo.getFields());
        // If there is no primary key，Use the first field as the primary key
        if (!tableInfo.isHavePrimaryKey()) {
            columns.get(0).setPrimaryKey(true);
        }
        codegenColumnMapper.insertBatch(columns);
        return table.getId();
    }

    @VisibleForTesting
    void validateTableInfo(TableInfo tableInfo) {
        if (tableInfo == null) {
            throw exception(CODEGEN_IMPORT_TABLE_NULL);
        }
        if (StrUtil.isEmpty(tableInfo.getComment())) {
            throw exception(CODEGEN_TABLE_INFO_TABLE_COMMENT_IS_NULL);
        }
        if (CollUtil.isEmpty(tableInfo.getFields())) {
            throw exception(CODEGEN_IMPORT_COLUMNS_NULL);
        }
        tableInfo.getFields().forEach(field -> {
            if (StrUtil.isEmpty(field.getComment())) {
                throw exception(CODEGEN_TABLE_INFO_COLUMN_COMMENT_IS_NULL, field.getName());
            }
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCodegen(CodegenUpdateReqVO updateReqVO) {
        // Check if it already exists
        if (codegenTableMapper.selectById(updateReqVO.getTable().getId()) == null) {
            throw exception(CODEGEN_TABLE_NOT_EXISTS);
        }
        // Check if the main table fields exist
        if (Objects.equals(updateReqVO.getTable().getTemplateType(), CodegenTemplateTypeEnum.SUB.getType())) {
            if (codegenTableMapper.selectById(updateReqVO.getTable().getMasterTableId()) == null) {
                throw exception(CODEGEN_MASTER_TABLE_NOT_EXISTS, updateReqVO.getTable().getMasterTableId());
            }
            if (CollUtil.findOne(updateReqVO.getColumns(),  // The field of the associated main table does not exist
                    column -> column.getId().equals(updateReqVO.getTable().getSubJoinColumnId())) == null) {
                throw exception(CODEGEN_SUB_COLUMN_NOT_EXISTS, updateReqVO.getTable().getSubJoinColumnId());
            }
        }

        // Update table Table definition
        CodegenTableDO updateTableObj = BeanUtils.toBean(updateReqVO.getTable(), CodegenTableDO.class);
        codegenTableMapper.updateById(updateTableObj);
        // Update column Field definition
        List<CodegenColumnDO> updateColumnObjs = BeanUtils.toBean(updateReqVO.getColumns(), CodegenColumnDO.class);
        updateColumnObjs.forEach(updateColumnObj -> codegenColumnMapper.updateById(updateColumnObj));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncCodegenFromDB(Long tableId) {
        // Check if it already exists
        CodegenTableDO table = codegenTableMapper.selectById(tableId);
        if (table == null) {
            throw exception(CODEGEN_TABLE_NOT_EXISTS);
        }
        // From the database，Get the database table structure
        TableInfo tableInfo = databaseTableService.getTable(table.getDataSourceConfigId(), table.getTableName());
        // Execute synchronization
        syncCodegen0(tableId, tableInfo);
    }

    private void syncCodegen0(Long tableId, TableInfo tableInfo) {
        // 1. Verify that the imported tables and fields are not empty
        validateTableInfo(tableInfo);
        List<TableField> tableFields = tableInfo.getFields();

        // 2. Build CodegenColumnDO Array，Only synchronize newly added fields
        List<CodegenColumnDO> codegenColumns = codegenColumnMapper.selectListByTableId(tableId);
        Set<String> codegenColumnNames = convertSet(codegenColumns, CodegenColumnDO::getColumnName);

        // 3.1 Calculation required【Modify】Fields，Reinsert when inserting，Delete the original when deleting
        Map<String, CodegenColumnDO> codegenColumnDOMap = convertMap(codegenColumns, CodegenColumnDO::getColumnName);
        BiPredicate<TableField, CodegenColumnDO> primaryKeyPredicate =
                (tableField, codegenColumn) -> tableField.getMetaInfo().getJdbcType().name().equals(codegenColumn.getDataType())
                        && tableField.getMetaInfo().isNullable() == codegenColumn.getNullable()
                        && tableField.isKeyFlag() == codegenColumn.getPrimaryKey()
                        && tableField.getComment().equals(codegenColumn.getColumnComment());
        Set<String> modifyFieldNames = tableFields.stream()
                .filter(tableField -> codegenColumnDOMap.get(tableField.getColumnName()) != null
                        && !primaryKeyPredicate.test(tableField, codegenColumnDOMap.get(tableField.getColumnName())))
                .map(TableField::getColumnName)
                .collect(Collectors.toSet());
        // 3.2 Calculation required【Delete】Fields
        Set<String> tableFieldNames = convertSet(tableFields, TableField::getName);
        Set<Long> deleteColumnIds = codegenColumns.stream()
                .filter(column -> (!tableFieldNames.contains(column.getColumnName())) || modifyFieldNames.contains(column.getColumnName()))
                .map(CodegenColumnDO::getId).collect(Collectors.toSet());
        // Remove existing fields
        tableFields.removeIf(column -> codegenColumnNames.contains(column.getColumnName()) && (!modifyFieldNames.contains(column.getColumnName())));
        if (CollUtil.isEmpty(tableFields) && CollUtil.isEmpty(deleteColumnIds)) {
            throw exception(CODEGEN_SYNC_NONE_CHANGE);
        }

        // 4.1 Insert new fields
        List<CodegenColumnDO> columns = codegenBuilder.buildColumns(tableId, tableFields);
        codegenColumnMapper.insertBatch(columns);
        // 4.2 Delete non-existent fields
        if (CollUtil.isNotEmpty(deleteColumnIds)) {
            codegenColumnMapper.deleteBatchIds(deleteColumnIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCodegen(Long tableId) {
        // Check if it already exists
        if (codegenTableMapper.selectById(tableId) == null) {
            throw exception(CODEGEN_TABLE_NOT_EXISTS);
        }

        // Delete table Table definition
        codegenTableMapper.deleteById(tableId);
        // Delete column Field definition
        codegenColumnMapper.deleteListByTableId(tableId);
    }

    @Override
    public List<CodegenTableDO> getCodegenTableList(Long dataSourceConfigId) {
        return codegenTableMapper.selectListByDataSourceConfigId(dataSourceConfigId);
    }

    @Override
    public PageResult<CodegenTableDO> getCodegenTablePage(CodegenTablePageReqVO pageReqVO) {
        return codegenTableMapper.selectPage(pageReqVO);
    }

    @Override
    public CodegenTableDO getCodegenTable(Long id) {
        return codegenTableMapper.selectById(id);
    }

    @Override
    public List<CodegenColumnDO> getCodegenColumnListByTableId(Long tableId) {
        return codegenColumnMapper.selectListByTableId(tableId);
    }

    @Override
    public Map<String, String> generationCodes(Long tableId) {
        // Check if it already exists
        CodegenTableDO table = codegenTableMapper.selectById(tableId);
        if (table == null) {
            throw exception(CODEGEN_TABLE_NOT_EXISTS);
        }
        List<CodegenColumnDO> columns = codegenColumnMapper.selectListByTableId(tableId);
        if (CollUtil.isEmpty(columns)) {
            throw exception(CODEGEN_COLUMN_NOT_EXISTS);
        }

        // If it is a master-subtable，Then load the corresponding sub-table information
        List<CodegenTableDO> subTables = null;
        List<List<CodegenColumnDO>> subColumnsList = null;
        if (CodegenTemplateTypeEnum.isMaster(table.getTemplateType())) {
            // Check subtable exists
            subTables = codegenTableMapper.selectListByTemplateTypeAndMasterTableId(
                    CodegenTemplateTypeEnum.SUB.getType(), tableId);
            if (CollUtil.isEmpty(subTables)) {
                throw exception(CODEGEN_MASTER_GENERATION_FAIL_NO_SUB_TABLE);
            }
            // Check that the associated fields of the child table exist
            subColumnsList = new ArrayList<>();
            for (CodegenTableDO subTable : subTables) {
                List<CodegenColumnDO> subColumns = codegenColumnMapper.selectListByTableId(subTable.getId());
                if (CollUtil.findOne(subColumns, column -> column.getId().equals(subTable.getSubJoinColumnId())) == null) {
                    throw exception(CODEGEN_SUB_COLUMN_NOT_EXISTS, subTable.getId());
                }
                subColumnsList.add(subColumns);
            }
        }

        // Execute Generate
        return codegenEngine.execute(table, columns, subTables, subColumnsList);
    }

    @Override
    public List<DatabaseTableRespVO> getDatabaseTableList(Long dataSourceConfigId, String name, String comment) {
        List<TableInfo> tables = databaseTableService.getTableList(dataSourceConfigId, name, comment);
        // Remove Codegen Medium，Already exists
        Set<String> existsTables = convertSet(
                codegenTableMapper.selectListByDataSourceConfigId(dataSourceConfigId), CodegenTableDO::getTableName);
        tables.removeIf(table -> existsTables.contains(table.getName()));
        return BeanUtils.toBean(tables, DatabaseTableRespVO.class);
    }

}
