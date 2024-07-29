package cn.econets.blossom.module.trade.controller.app.brokerage.vo.withdraw;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "User App - Distribution and withdrawal Response VO")
@Data
public class AppBrokerageWithdrawRespVO {

    @Schema(description = "Withdrawal number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long id;

    @Schema(description = "Withdrawal status", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer status;

    @Schema(description = "Withdrawal status name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Under review")
    private String statusName;

    @Schema(description = "Withdrawal amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer price;

    @Schema(description = "Withdrawal time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
