package cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.table;

import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenSceneEnum;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenTemplateTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Code generation table definition creation/Modify Response VO")
@Data
public class CodegenTableSaveReqVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Generate scene，See CodegenSceneEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Import type cannot be empty")
    private Integer scene;

    @Schema(description = "Table name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    @NotNull(message = "Table name cannot be empty")
    private String tableName;

    @Schema(description = "Table description", requiredMode = Schema.RequiredMode.REQUIRED, example = "Description")
    @NotNull(message = "Table description cannot be empty")
    private String tableComment;

    @Schema(description = "Remarks", example = "I am a note")
    private String remark;

    @Schema(description = "Module name", requiredMode = Schema.RequiredMode.REQUIRED, example = "system")
    @NotNull(message = "Module name cannot be empty")
    private String moduleName;

    @Schema(description = "Business Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "codegen")
    @NotNull(message = "Business name cannot be empty")
    private String businessName;

    @Schema(description = "Class Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "CodegenTable")
    @NotNull(message = "Class name cannot be empty")
    private String className;

    @Schema(description = "Class description", requiredMode = Schema.RequiredMode.REQUIRED, example = "Table definition for code generator")
    @NotNull(message = "Class description cannot be empty")
    private String classComment;

    @Schema(description = "Author", requiredMode = Schema.RequiredMode.REQUIRED, example = "Author's name")
    @NotNull(message = "Author cannot be empty")
    private String author;

    @Schema(description = "Template type，See CodegenTemplateTypeEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Template type cannot be empty")
    private Integer templateType;

    @Schema(description = "Front-end type，See CodegenFrontTypeEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    @NotNull(message = "Front-end type cannot be empty")
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
    @Schema(description = "Name field number of tree table", example = "16384")
    private Long treeNameColumnId;

    @AssertTrue(message = "The parent menu cannot be empty，Please go to [Modify build configuration -> Generate information] Interface，Settings“Previous menu”Field")
    @JsonIgnore
    public boolean isParentMenuIdValid() {
        // When the generated scene is the management background，The parent menu must be set，Otherwise the generated menu SQL There is no parent menu
        return ObjectUtil.notEqual(getScene(), CodegenSceneEnum.ADMIN.getScene())
                || getParentMenuId() != null;
    }

    @AssertTrue(message = "The associated parent table information is incomplete")
    @JsonIgnore
    public boolean isSubValid() {
        return ObjectUtil.notEqual(getTemplateType(), CodegenTemplateTypeEnum.SUB)
                || (ObjectUtil.isAllNotEmpty(masterTableId, subJoinColumnId, subJoinMany));
    }

    @AssertTrue(message = "The associated tree table information is incomplete")
    @JsonIgnore
    public boolean isTreeValid() {
        return ObjectUtil.notEqual(templateType, CodegenTemplateTypeEnum.TREE)
                || (ObjectUtil.isAllNotEmpty(treeParentColumnId, treeNameColumnId));
    }

}
