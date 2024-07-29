package cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.column;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Code generation field definition creation/Modify Request VO")
@Data
public class CodegenColumnSaveReqVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Table number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Table number cannot be empty")
    private Long tableId;

    @Schema(description = "Field name", requiredMode = Schema.RequiredMode.REQUIRED, example = "user_age")
    @NotNull(message = "Field name cannot be empty")
    private String columnName;

    @Schema(description = "Field Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "int(11)")
    @NotNull(message = "Field type cannot be empty")
    private String dataType;

    @Schema(description = "Field description", requiredMode = Schema.RequiredMode.REQUIRED, example = "Age")
    @NotNull(message = "Field description cannot be empty")
    private String columnComment;

    @Schema(description = "Is it allowed to be empty?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Is it allowed to be empty? It cannot be empty")
    private Boolean nullable;

    @Schema(description = "Is it the primary key?", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    @NotNull(message = "Whether the primary key cannot be empty")
    private Boolean primaryKey;

    @Schema(description = "Whether to auto-increment", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether the auto-increment value cannot be empty")
    private Boolean autoIncrement;

    @Schema(description = "Sort", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "The sort order cannot be empty")
    private Integer ordinalPosition;

    @Schema(description = "Java Attribute type", requiredMode = Schema.RequiredMode.REQUIRED, example = "userAge")
    @NotNull(message = "Java The property type cannot be empty")
    private String javaType;

    @Schema(description = "Java Attribute name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Integer")
    @NotNull(message = "Java The attribute name cannot be empty")
    private String javaField;

    @Schema(description = "Dictionary type", example = "sys_gender")
    private String dictType;

    @Schema(description = "Data Example", example = "1024")
    private String example;

    @Schema(description = "Whether Create Create operation fields", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether Create The fields for creating operations cannot be empty")
    private Boolean createOperation;

    @Schema(description = "Whether Update Update operation fields", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    @NotNull(message = "Whether Update The update operation field cannot be empty")
    private Boolean updateOperation;

    @Schema(description = "Whether List Fields for query operations", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether List The query operation field cannot be empty")
    private Boolean listOperation;

    @Schema(description = "List Condition type of query operation，See CodegenColumnListConditionEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "LIKE")
    @NotNull(message = "List The condition type of the query operation cannot be empty")
    private String listOperationCondition;

    @Schema(description = "Whether List Return fields of query operation", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether List The return field of the query operation cannot be empty")
    private Boolean listOperationResult;

    @Schema(description = "Display type", requiredMode = Schema.RequiredMode.REQUIRED, example = "input")
    @NotNull(message = "Display type cannot be empty")
    private String htmlType;

}
