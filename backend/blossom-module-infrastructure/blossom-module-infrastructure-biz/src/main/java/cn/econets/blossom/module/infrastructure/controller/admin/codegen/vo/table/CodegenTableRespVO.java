package cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Code generation table definition Response VO")
@Data
public class CodegenTableRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Generate scene，See CodegenSceneEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer scene;

    @Schema(description = "Table name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Table name")
    private String tableName;

    @Schema(description = "Table description", requiredMode = Schema.RequiredMode.REQUIRED, example = "Table description")
    private String tableComment;

    @Schema(description = "Remarks", example = "I am a note")
    private String remark;

    @Schema(description = "Module name", requiredMode = Schema.RequiredMode.REQUIRED, example = "system")
    private String moduleName;

    @Schema(description = "Business Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "codegen")
    private String businessName;

    @Schema(description = "Class name", requiredMode = Schema.RequiredMode.REQUIRED, example = "CodegenTable")
    private String className;

    @Schema(description = "Class description", requiredMode = Schema.RequiredMode.REQUIRED, example = "Table definition for code generator")
    private String classComment;

    @Schema(description = "Author", requiredMode = Schema.RequiredMode.REQUIRED, example = "Author")
    private String author;

    @Schema(description = "Template type，See CodegenTemplateTypeEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer templateType;

    @Schema(description = "Front-end type，See CodegenFrontTypeEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    private Integer frontType;

    @Schema(description = "Parent menu number", example = "1024")
    private Long parentMenuId;

    @Schema(description = "The number of the main table", example = "2048")
    private Long masterTableId;
    @Schema(description = "The field number of the sub-table associated with the main table", example = "4096")
    private Long subJoinColumnId;
    @Schema(description = "Are the main table and the sub-table one-to-many?", example = "4096")
    private Boolean subJoinMany;

    @Schema(description = "Parent field number of the tree table", example = "8192")
    private Long treeParentColumnId;
    @Schema(description = "The name field number of the tree table", example = "16384")
    private Long treeNameColumnId;

    @Schema(description = "Primary key number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer dataSourceConfigId;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Update time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime updateTime;

}
