package cn.econets.blossom.module.crm.controller.admin.receivable.vo.receivable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

// TODO Exported VO，You can consider using @Excel Annotation，Realize the export function
@Schema(description = "Management Backend - CRM Payback Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CrmReceivableRespVO extends CrmReceivableBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "25787")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Customer Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "test")
    private String customerName;

    @Schema(description = "Approval Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer auditStatus;

    @Schema(description = "Contract Number", example = "Q110")
    private String contractNo;

    @Schema(description = "Person in charge", example = "test")
    private String ownerUserName;

    @Schema(description = "Creator", example = "25682")
    private String creator;

    @Schema(description = "Creator's name", example = "test")
    private String creatorName;

}
