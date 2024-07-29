package cn.econets.blossom.module.crm.controller.admin.contact.vo;

import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - CRM Contact transfer Request VO")
@Data
public class CrmContactTransferReqVO {

    @Schema(description = "Contact Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10430")
    @NotNull(message = "Contact number cannot be empty")
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
