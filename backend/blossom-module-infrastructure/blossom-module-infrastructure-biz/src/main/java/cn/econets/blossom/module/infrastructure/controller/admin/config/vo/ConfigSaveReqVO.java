package cn.econets.blossom.module.infrastructure.controller.admin.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(description = "Management Backend - Parameter configuration creation/Modify Request VO")
@Data
public class ConfigSaveReqVO {

    @Schema(description = "Parameter configuration serial number", example = "1024")
    private Long id;

    @Schema(description = "Parameter grouping", requiredMode = Schema.RequiredMode.REQUIRED, example = "biz")
    @NotEmpty(message = "Parameter grouping cannot be empty")
    @Size(max = 50, message = "Parameter name cannot exceed 50 Characters")
    private String category;

    @Schema(description = "Parameter name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Database name")
    @NotBlank(message = "Parameter name cannot be empty")
    @Size(max = 100, message = "Parameter name cannot exceed 100 Characters")
    private String name;

    @Schema(description = "Parameter key name", requiredMode = Schema.RequiredMode.REQUIRED, example = "ximu.db.username")
    @NotBlank(message = "Parameter key name length cannot be empty")
    @Size(max = 100, message = "Parameter key name length cannot exceed 100 Characters")
    private String key;

    @Schema(description = "Parameter key value", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotBlank(message = "Parameter key value cannot be empty")
    @Size(max = 500, message = "Parameter key value length cannot exceed 500 Characters")
    private String value;

    @Schema(description = "Is it visible?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether it is visible cannot be empty")
    private Boolean visible;

    @Schema(description = "Remarks", example = "Note that it is very handsome！")
    private String remark;

}
