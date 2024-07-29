package cn.econets.blossom.module.trade.controller.app.brokerage.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "User App - Subordinate distribution statistics Response VO")
@Data
public class AppBrokerageUserChildSummaryRespVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long id;

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "Xiao Wang")
    private String nickname;

    @Schema(description = "User avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xxx.jpg")
    private String avatar;

    @Schema(description = "Commission amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer brokeragePrice;

    @Schema(description = "Distribution order quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    private Integer brokerageOrderCount;

    @Schema(description = "Number of distribution users", requiredMode = Schema.RequiredMode.REQUIRED, example = "30")
    private Integer brokerageUserCount;

    @Schema(description = "Time to bind the promoter", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime brokerageTime;

}
