package cn.econets.blossom.module.crm.controller.admin.customer.vo;

import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - CRM Customer transfer Request VO")
@Data
public class CrmCustomerTransferReqVO {

    @Schema(description = "Customer Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10430")
    @NotNull(message = "Customer number cannot be empty")
    private Long id;

    /**
     * User ID of the new person in charge
     */
    @Schema(description = "User ID of the new person in charge", requiredMode = Schema.RequiredMode.REQUIRED, example = "10430")
    @NotNull(message = "The user ID of the new person in charge cannot be empty")
    private Long newOwnerUserId;

    /**
     * The authority level of the old person in charge after joining the team。If null Description removed
     *
     * Relationship {@link CrmPermissionLevelEnum}
     */
    @Schema(description = "The authority level of the old person in charge after joining the team", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer oldOwnerPermissionLevel;

}
