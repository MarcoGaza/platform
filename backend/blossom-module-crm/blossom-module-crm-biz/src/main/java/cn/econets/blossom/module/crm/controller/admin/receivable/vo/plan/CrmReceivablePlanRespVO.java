package cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "Management Backend - CRM Payment Refund Plan Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmReceivablePlanRespVO extends CrmReceivablePlanBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25153")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Customer Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    private String customerName;

    @Schema(description = "Contract Number", example = "Q110")
    private String contractNo;

    @Schema(description = "Person in charge", example = "test")
    private String ownerUserName;

    @Schema(description = "Creator", example = "25682")
    private String creator;

    @Schema(description = "Creator's name", example = "test")
    private String creatorName;

    @Schema(description = "Completion status", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Boolean finishStatus;

    @Schema(description = "Payment method", example = "1") // From Receivable of returnType Field
    private Integer returnType;

}
