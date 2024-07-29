package cn.econets.blossom.module.system.controller.admin.tenant.vo.packages;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Schema(description = "Management Backend - Tenant Package Response VO")
@Data
public class TenantPackageRespVO {

    @Schema(description = "Package number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Package Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "VIP")
    private String name;

    @Schema(description = "Status，See CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Remarks", example = "Good")
    private String remark;

    @Schema(description = "The associated menu number", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<Long> menuIds;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
