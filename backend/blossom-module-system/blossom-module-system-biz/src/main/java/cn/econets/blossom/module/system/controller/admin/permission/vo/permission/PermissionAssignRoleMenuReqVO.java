package cn.econets.blossom.module.system.controller.admin.permission.vo.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

@Schema(description = "Management Backend - Give character menu Request VO")
@Data
public class PermissionAssignRoleMenuReqVO {

    @Schema(description = "Role number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Role number cannot be empty")
    private Long roleId;

    @Schema(description = "Menu number list", example = "1,3,5")
    private Set<Long> menuIds = Collections.emptySet(); // Backstop

}
