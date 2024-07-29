package cn.econets.blossom.module.system.controller.admin.permission.vo.permission;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.system.enums.permission.DataScopeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

@Schema(description = "Management Backend - Give data permissions to the role Request VO")
@Data
public class PermissionAssignRoleDataScopeReqVO {

    @Schema(description = "Role number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Role number cannot be empty")
    private Long roleId;

    @Schema(description = "Data Range，See DataScopeEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "The data range cannot be empty")
    @InEnum(value = DataScopeEnum.class, message = "The data range must be {value}")
    private Integer dataScope;

    @Schema(description = "Department number list，Only range types are DEPT_CUSTOM Time，This field is required", example = "1,3,5")
    private Set<Long> dataScopeDeptIds = Collections.emptySet(); // Backstop

}
