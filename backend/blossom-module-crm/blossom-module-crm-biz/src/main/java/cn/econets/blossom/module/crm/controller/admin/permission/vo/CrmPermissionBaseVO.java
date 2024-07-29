package cn.econets.blossom.module.crm.controller.admin.permission.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Data permissions Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 *
 */
@Data
public class CrmPermissionBaseVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    @Schema(description = "CRM Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @InEnum(CrmBizTypeEnum.class)
    @NotNull(message = "CRM Type cannot be empty")
    private Integer bizType;

    @Schema(description = "CRM Type data number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "CRM Type data number cannot be empty")
    private Long bizId;

    @Schema(description = "Permission level", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @InEnum(CrmPermissionLevelEnum.class)
    @NotNull(message = "Permission level cannot be empty")
    private Integer level;

}
