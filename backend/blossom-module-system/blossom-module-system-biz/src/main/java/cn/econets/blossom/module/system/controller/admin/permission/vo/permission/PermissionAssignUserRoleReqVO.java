package cn.econets.blossom.module.system.controller.admin.permission.vo.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

@Schema(description = "Management Backend - Give user roles Request VO")
@Data
public class PermissionAssignUserRoleReqVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    @Schema(description = "List of character numbers", example = "1,3,5")
    private Set<Long> roleIds = Collections.emptySet(); // Backstop

}
