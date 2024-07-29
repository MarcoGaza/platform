package cn.econets.blossom.module.crm.service.permission.bo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * crm Data permissions Create Req BO
 *
 */
@Data
public class CrmPermissionCreateReqBO {

    /**
     * Currently logged in user ID
     */
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    /**
     * Crm Type
     */
    @NotNull(message = "Crm Type cannot be empty")
    @InEnum(CrmBizTypeEnum.class)
    private Integer bizType;
    /**
     * Data number
     */
    @NotNull(message = "Crm Data number cannot be empty")
    private Long bizId;

    /**
     * Permission Level
     */
    @NotNull(message = "Permission level cannot be empty")
    @InEnum(CrmPermissionLevelEnum.class)
    private Integer level;

}
