package cn.econets.blossom.module.system.controller.admin.errorcode.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Error code creation/Modify Request VO")
@Data
public class ErrorCodeSaveReqVO {

    @Schema(description = "Error code number", example = "1024")
    private Long id;

    @Schema(description = "Application name", requiredMode = Schema.RequiredMode.REQUIRED, example = "dashboard")
    @NotNull(message = "Application name cannot be empty")
    private String applicationName;

    @Schema(description = "Error code", requiredMode = Schema.RequiredMode.REQUIRED, example = "1234")
    @NotNull(message = "Error code cannot be empty")
    private Integer code;

    @Schema(description = "Error code error prompt", requiredMode = Schema.RequiredMode.REQUIRED, example = "Handsome")
    @NotNull(message = "Error code error prompt cannot be empty")
    private String message;

    @Schema(description = "Remarks", example = "Hahaha")
    private String memo;

}
