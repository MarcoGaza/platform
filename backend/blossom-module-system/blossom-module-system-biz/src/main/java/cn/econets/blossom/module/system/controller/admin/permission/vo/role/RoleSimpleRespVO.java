package cn.econets.blossom.module.system.controller.admin.permission.vo.role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Character simplified information Response VO")
@Data
public class RoleSimpleRespVO {

    @Schema(description = "Role number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Role name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    private String name;

}
