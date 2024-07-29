package cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.column;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Code generation field definition Response VO")
@Data
public class CodegenColumnRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Table number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long tableId;

    @Schema(description = "Field name", requiredMode = Schema.RequiredMode.REQUIRED, example = "user_age")
    private String columnName;

    @Schema(description = "Field Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "int(11)")
    private String dataType;

    @Schema(description = "Field description", requiredMode = Schema.RequiredMode.REQUIRED, example = "Age")
    private String columnComment;

    @Schema(description = "Is it allowed to be empty?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean nullable;

    @Schema(description = "Is it the primary key?", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    private Boolean primaryKey;

    @Schema(description = "Whether to auto-increment", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean autoIncrement;

    @Schema(description = "Sort", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer ordinalPosition;

    @Schema(description = "Java Attribute type", requiredMode = Schema.RequiredMode.REQUIRED, example = "userAge")
    private String javaType;

    @Schema(description = "Java Attribute name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Integer")
    private String javaField;

    @Schema(description = "Dictionary type", example = "sys_gender")
    private String dictType;

    @Schema(description = "Data Example", example = "1024")
    private String example;

    @Schema(description = "Whether Create Create operation fields", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean createOperation;

    @Schema(description = "Whether Update Update operation fields", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
    private Boolean updateOperation;

    @Schema(description = "Whether List Fields for query operations", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean listOperation;

    @Schema(description = "List Condition type of query operation，See CodegenColumnListConditionEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "LIKE")
    private String listOperationCondition;

    @Schema(description = "Whether List Return fields of query operation", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean listOperationResult;

    @Schema(description = "Display type", requiredMode = Schema.RequiredMode.REQUIRED, example = "input")
    private String htmlType;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
