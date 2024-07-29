package cn.econets.blossom.module.infrastructure.controller.admin.db.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Data source configuration Response VO")
@Data
public class DataSourceConfigRespVO {

    @Schema(description = "Primary key number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer id;

    @Schema(description = "Data source name", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    private String name;

    @Schema(description = "Data source connection", requiredMode = Schema.RequiredMode.REQUIRED, example = "jdbc:mysql://127.0.0.1:3306/econets-vue")
    private String url;

    @Schema(description = "Username", requiredMode = Schema.RequiredMode.REQUIRED, example = "root")
    private String username;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
