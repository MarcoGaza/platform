package cn.econets.blossom.module.trade.controller.app.config.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "User App - Transaction Configuration Response VO")
@Data
public class AppTradeConfigRespVO {

    @Schema(description = "Tencent Map KEY", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    private String tencentLbsKey;

    // ========== Delivery related ==========

    @Schema(description = "Whether to enable self-collection", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether to enable self-collection cannot be empty")
    private Boolean deliveryPickUpEnabled;

    // ========== After-sales related ==========

    @Schema(description = "Reason for after-sales refund", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> afterSaleRefundReasons;

    @Schema(description = "Reasons for after-sales returns", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> afterSaleReturnReasons;

    // ========== Distribution related ==========

    @Schema(description = "Distribution poster address array", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> brokeragePosterUrls;

    @Schema(description = "Commission freeze time（Sky）", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer brokerageFrozenDays;

    @Schema(description = "Minimum commission withdrawal amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer brokerageWithdrawMinPrice;

    @Schema(description = "Withdrawal method", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1, 2]")
    private List<Integer> brokerageWithdrawTypes;

}
