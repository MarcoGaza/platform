package cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - Based on the table structure of the database，Create table and field definitions for code generator Request VO")
@Data
public class CodegenCreateListReqVO {

    @Schema(description = "Data source configuration number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The data source configuration number cannot be empty")
    private Long dataSourceConfigId;

    @Schema(description = "Table name array", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1, 2, 3]")
    @NotNull(message = "Table name array cannot be empty")
    private List<String> tableNames;

}
