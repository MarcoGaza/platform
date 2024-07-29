package cn.econets.blossom.module.system.controller.admin.tenant.vo.packages;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Tenant package streamlined Response VO")
@Data
public class TenantPackageSimpleRespVO {

    @Schema(description = "Package number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The package number cannot be empty")
    private Long id;

    @Schema(description = "Package Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "VIP")
    @NotNull(message = "The package name cannot be empty")
    private String name;

}
