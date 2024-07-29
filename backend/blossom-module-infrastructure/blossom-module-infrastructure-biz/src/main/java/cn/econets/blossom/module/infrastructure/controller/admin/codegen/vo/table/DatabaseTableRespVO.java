package cn.econets.blossom.module.infrastructure.controller.admin.codegen.vo.table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Database table definition Response VO")
@Data
public class DatabaseTableRespVO {

    @Schema(description = "Table name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Table name")
    private String name;

    @Schema(description = "Table description", requiredMode = Schema.RequiredMode.REQUIRED, example = "Table description")
    private String comment;

}
