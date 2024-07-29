package cn.econets.blossom.module.crm.controller.admin.receivable.vo.receivable;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.crm.enums.common.CrmAuditStatusEnum;
import cn.econets.blossom.module.crm.enums.common.CrmSceneTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - CRM Payment Collection Pagination Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmReceivablePageReqVO extends PageParam {

    @Schema(description = "Payment Number")
    private String no;

    @Schema(description = "Payment collection plan number", example = "31177")
    private Long planId;

    @Schema(description = "Customer Number", example = "4963")
    private Long customerId;

    @Schema(description = "Scene Type", example = "1")
    @InEnum(CrmSceneTypeEnum.class)
    private Integer sceneType; // Scene Type，for null When it means all

    @Schema(description = "Approval Status", example = "20")
    @InEnum(CrmAuditStatusEnum.class)
    private Integer auditStatus;

}
