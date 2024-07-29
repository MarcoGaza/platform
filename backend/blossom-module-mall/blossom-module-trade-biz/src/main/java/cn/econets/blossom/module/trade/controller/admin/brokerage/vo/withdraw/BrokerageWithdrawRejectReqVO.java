package cn.econets.blossom.module.trade.controller.admin.brokerage.vo.withdraw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Reject application Request VO")
@Data
@ToString(callSuper = true)
public class BrokerageWithdrawRejectReqVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "7161")
    @NotNull(message = "The number cannot be empty")
    private Integer id;

    @Schema(description = "Reason for rejection of review", example = "Incorrect")
    @NotEmpty(message = "Rejection reason cannot be empty")
    private String auditReason;

}
