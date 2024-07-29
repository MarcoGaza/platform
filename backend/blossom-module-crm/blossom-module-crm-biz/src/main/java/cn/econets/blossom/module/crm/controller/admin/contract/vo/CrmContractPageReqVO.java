package cn.econets.blossom.module.crm.controller.admin.contract.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.crm.enums.common.CrmAuditStatusEnum;
import cn.econets.blossom.module.crm.enums.common.CrmSceneTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - CRM Contract Pagination Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmContractPageReqVO extends PageParam {

    /**
     * Expiration type - Expiring soon
     */
    public static final Integer EXPIRY_TYPE_ABOUT_TO_EXPIRE = 1;
    /**
     * Expiration type - Expired
     */
    public static  final Integer EXPIRY_TYPE_EXPIRED = 2;

    @Schema(description = "Contract Number", example = "XYZ008")
    private String no;

    @Schema(description = "Contract Name", example = "Wang Wu")
    private String name;

    @Schema(description = "Customer Number", example = "18336")
    private Long customerId;

    @Schema(description = "Opportunity Number", example = "10864")
    private Long businessId;

    @Schema(description = "Scene Type", example = "1")
    @InEnum(CrmSceneTypeEnum.class)
    private Integer sceneType; // Scene Type，for null When it means all

    @Schema(description = "Approval Status", example = "20")
    @InEnum(CrmAuditStatusEnum.class)
    private Integer auditStatus;

    @Schema(description = "Expiration Type", example = "1")
    private Integer expiryType; // Expiration Type，for null When it means all

}
