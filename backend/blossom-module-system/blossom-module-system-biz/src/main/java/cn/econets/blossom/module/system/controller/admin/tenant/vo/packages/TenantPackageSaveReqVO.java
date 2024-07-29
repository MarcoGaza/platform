package cn.econets.blossom.module.system.controller.admin.tenant.vo.packages;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Schema(description = "Management Backend - Tenant package creation/Modify Request VO")
@Data
public class TenantPackageSaveReqVO {

    @Schema(description = "Package number", example = "1024")
    private Long id;

    @Schema(description = "Package Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "VIP")
    @NotEmpty(message = "The package name cannot be empty")
    private String name;

    @Schema(description = "Status，See CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    @InEnum(value = CommonStatusEnum.class, message = "The status must be {value}")
    private Integer status;

    @Schema(description = "Remarks", example = "Good")
    private String remark;

    @Schema(description = "The associated menu number", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The associated menu number cannot be empty")
    private Set<Long> menuIds;

}
