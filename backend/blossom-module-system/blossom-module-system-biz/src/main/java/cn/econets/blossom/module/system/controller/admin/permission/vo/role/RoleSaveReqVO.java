package cn.econets.blossom.module.system.controller.admin.permission.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(description = "Management Backend - Character Creation Request VO")
@Data
public class RoleSaveReqVO {

    @Schema(description = "Role number", example = "1")
    private Long id;

    @Schema(description = "Role name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Administrator")
    @NotBlank(message = "Role name cannot be empty")
    @Size(max = 30, message = "The role name cannot be longer than30Characters")
    private String name;

    @NotBlank(message = "Role flag cannot be empty")
    @Size(max = 100, message = "The length of the role logo cannot exceed100Characters")
    @Schema(description = "Role Code", requiredMode = Schema.RequiredMode.REQUIRED, example = "ADMIN")
    private String code;

    @Schema(description = "The display order cannot be empty", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The display order cannot be empty")
    private Integer sort;

    @Schema(description = "Remarks", example = "I am a character")
    private String remark;

}
