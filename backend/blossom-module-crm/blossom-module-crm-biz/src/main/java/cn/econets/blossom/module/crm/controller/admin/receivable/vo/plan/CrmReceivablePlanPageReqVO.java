package cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.crm.enums.common.CrmSceneTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - CRM Payment Refund Plan Pagination Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmReceivablePlanPageReqVO extends PageParam {

    /**
     * Reminder type - Waiting for payment
     */
    public final static Integer REMIND_TYPE_NEEDED = 1;
    /**
     * Reminder type - Overdue
     */
    public final static Integer REMIND_TYPE_EXPIRED = 2;
    /**
     * Reminder type - Payment received
     */
    public final static Integer REMIND_TYPE_RECEIVED = 3;

    @Schema(description = "Customer Number", example = "18026")
    private Long customerId;

    // TODO @This search should be for the contract number no
    @Schema(description = "Contract Name", example = "3473")
    private Long contractId;

    @Schema(description = "Scene Type", example = "1")
    @InEnum(CrmSceneTypeEnum.class)
    private Integer sceneType; // Scene Type，for null When it means all

    @Schema(description = "Reminder type", example = "1")
    private Integer remindType; // Reminder type，for null When it means all

}
