package cn.econets.blossom.module.crm.controller.admin.clue.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Cue transfer Request VO")
@Data
public class CrmClueTransferReqVO {

    @Schema(description = "Clue number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10430")
    @NotNull(message = "The clue number cannot be empty")
    private Long id;

    @Schema(description = "User ID of the new person in charge", requiredMode = Schema.RequiredMode.REQUIRED, example = "10430")
    @NotNull(message = "The user ID of the new person in charge cannot be empty")
    private Long newOwnerUserId;

    @Schema(description = "The authority level of the old person in charge after joining the team", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @InEnum(value = CrmPermissionLevelEnum.class)
    private Integer oldOwnerPermissionLevel; // The authority level of the old person in charge after joining the team。If null Description removed

}
