package cn.econets.blossom.module.statistics.controller.admin.member.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Member Statistics Response VO")
@Data
public class MemberSummaryRespVO {

    @Schema(description = "Number of members", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer userCount;

    @Schema(description = "Number of top-up members", requiredMode = Schema.RequiredMode.REQUIRED, example = "221")
    private Integer rechargeUserCount;

    @Schema(description = "Recharge amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer rechargePrice;

    // TODO Why not just change this field to：orderPayPrice？？
    @Schema(description = "Expenditure amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer expensePrice; // Calculate only mall The payment amount of the transaction order，No refund considered

}
