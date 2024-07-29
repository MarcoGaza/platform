package cn.econets.blossom.module.system.controller.admin.tenant.vo.tenant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Tenant streamlining Response VO")
@Data
public class TenantSimpleRespVO {

    @Schema(description = "Tenant Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Tenant Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    private String name;

}
