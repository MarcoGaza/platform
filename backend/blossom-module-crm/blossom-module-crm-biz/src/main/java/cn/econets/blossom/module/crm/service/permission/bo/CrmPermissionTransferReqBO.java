package cn.econets.blossom.module.crm.service.permission.bo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Data permission transfer Request BO
 *
 */
@Data
public class CrmPermissionTransferReqBO {

    /**
     * Currently logged in user ID
     */
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    /**
     * CRM Type
     */
    @NotNull(message = "Crm Type cannot be empty")
    @InEnum(CrmBizTypeEnum.class)
    private Integer bizType;
    /**
     * Data number
     */
    @NotNull(message = "CRM Data number cannot be empty")
    private Long bizId;

    /**
     * User ID of the new person in charge
     */
    @NotNull(message = "The user ID of the new person in charge cannot be empty")
    private Long newOwnerUserId;

    /**
     * The authority level of the old person in charge after joining the team。If null Description removed
     *
     * Relationship {@link CrmPermissionLevelEnum}
     */
    private Integer oldOwnerPermissionLevel;

}
