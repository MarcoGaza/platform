package cn.econets.blossom.module.infrastructure.service.codegen;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.CodegenCreateListReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.CodegenUpdateReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.table.CodegenTablePageReqVO;
import cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.table.DatabaseTableRespVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.codegen.CodegenColumnDO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.codegen.CodegenTableDO;

import java.util.List;
import java.util.Map;

/**
 * Code Generation Service Interface
 *
 *
 */
public interface CodegenService {

    /**
     * Based on the table structure of the database，Create table definition for code generator
     *
     * @param userId User Number
     * @param reqVO Table information
     * @return The number array of the created table definition
     */
    List<Long> createCodegenList(Long userId, CodegenCreateListReqVO reqVO);

    /**
     * Update database table and field definitions
     *
     * @param updateReqVO Update information
     */
    void updateCodegen(CodegenUpdateReqVO updateReqVO);

    /**
     * Based on the table structure of the database，Synchronize database table and field definitions
     *
     * @param tableId Table number
     */
    void syncCodegenFromDB(Long tableId);

    /**
     * Delete the table and field definitions of the database
     *
     * @param tableId Data number
     */
    void deleteCodegen(Long tableId);

    /**
     * Get table definition list
     *
     * @param dataSourceConfigId Data source configuration number
     * @return Table definition list
     */
    List<CodegenTableDO> getCodegenTableList(Long dataSourceConfigId);

    /**
     * Get table definition paging
     *
     * @param pageReqVO Pagination conditions
     * @return Table definition paging
     */
    PageResult<CodegenTableDO> getCodegenTablePage(CodegenTablePageReqVO pageReqVO);

    /**
     * Get table definition
     *
     * @param id Table number
     * @return Table definition
     */
    CodegenTableDO getCodegenTable(Long id);

    /**
     * Get the field definition array of the specified table
     *
     * @param tableId Table number
     * @return Field definition array
     */
    List<CodegenColumnDO> getCodegenColumnListByTableId(Long tableId);

    /**
     * Execute code generation for the specified table
     *
     * @param tableId Table number
     * @return Generate results。key File path，value Corresponding code content
     */
    Map<String, String> generationCodes(Long tableId);

    /**
     * Get the table definition list that comes with the database
     *
     * @param dataSourceConfigId Data source configuration number
     * @param name Table name
     * @param comment Table description
     * @return Table definition list
     */
    List<DatabaseTableRespVO> getDatabaseTableList(Long dataSourceConfigId, String name, String comment);

}
