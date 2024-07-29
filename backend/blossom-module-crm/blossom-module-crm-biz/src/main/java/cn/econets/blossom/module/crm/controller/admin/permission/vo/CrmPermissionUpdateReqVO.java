package cn.econets.blossom.module.crm.controller.admin.permission.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - CRM Data permission update Request VO")
@Data
public class CrmPermissionUpdateReqVO {

    @Schema(description = "Data permission number list", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1,2]")
    @NotNull(message = "The data permission number list cannot be empty")
    private List<Long> ids;

    @Schema(description = "Crm Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @InEnum(CrmBizTypeEnum.class)
    @NotNull(message = "Crm Type cannot be empty")
    private Integer bizType;

    @Schema(description = "Crm Type data number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Crm Type data number cannot be empty")
    private Long bizId;

    @Schema(description = "Permission level", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @InEnum(CrmPermissionLevelEnum.class)
    @NotNull(message = "Permission level cannot be empty")
    private Integer level;

}
