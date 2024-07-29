package cn.econets.blossom.module.trade.controller.app.brokerage.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Personal distribution statistics Response VO")
@Data
public class AppBrokerageUserMySummaryRespVO {

    @Schema(description = "Yesterday's commission，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer yesterdayPrice;

    @Schema(description = "Withdrawal commission，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer withdrawPrice;

    @Schema(description = "Available commission，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "2408")
    private Integer brokeragePrice;

    @Schema(description = "Frozen commission，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "234")
    private Integer frozenPrice;

    @Schema(description = "Number of distribution users（Level 1）", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long firstBrokerageUserCount;

    @Schema(description = "Number of distribution users（Level 2）", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long secondBrokerageUserCount;

}
