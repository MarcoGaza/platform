package cn.econets.blossom.module.infrastructure.controller.admin.db.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Data source configuration creation/Modify Request VO")
@Data
public class DataSourceConfigSaveReqVO {

    @Schema(description = "Primary key number", example = "1024")
    private Long id;

    @Schema(description = "Data source name", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    @NotNull(message = "Data source name cannot be empty")
    private String name;

    @Schema(description = "Data source connection", requiredMode = Schema.RequiredMode.REQUIRED, example = "jdbc:mysql://127.0.0.1:3306/econets-vue")
    @NotNull(message = "The data source connection cannot be empty")
    private String url;

    @Schema(description = "Username", requiredMode = Schema.RequiredMode.REQUIRED, example = "root")
    @NotNull(message = "Username cannot be empty")
    private String username;

    @Schema(description = "Password", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotNull(message = "The password cannot be empty")
    private String password;

}
