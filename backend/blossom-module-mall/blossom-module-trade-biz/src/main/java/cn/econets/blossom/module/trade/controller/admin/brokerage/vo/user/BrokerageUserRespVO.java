package cn.econets.blossom.module.trade.controller.admin.brokerage.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Distribution User Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BrokerageUserRespVO extends BrokerageUserBaseVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "20019")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    // ========== User Information ==========

    @Schema(description = "User avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xxx.png")
    private String avatar;
    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    private String nickname;

    // ========== Promotional information ========== Attention：Yes, included 1 + 2 Level data

    @Schema(description = "Number of promoted users", requiredMode = Schema.RequiredMode.REQUIRED, example = "20019")
    private Integer brokerageUserCount;
    @Schema(description = "Number of promotion orders", requiredMode = Schema.RequiredMode.REQUIRED, example = "20019")
    private Integer brokerageOrderCount;
    @Schema(description = "Promotion order amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "20019")
    private Integer brokerageOrderPrice;

    // ========== Withdrawal information ==========

    @Schema(description = "Amount withdrawn", requiredMode = Schema.RequiredMode.REQUIRED, example = "20019")
    private Integer withdrawPrice;
    @Schema(description = "Number of withdrawals", requiredMode = Schema.RequiredMode.REQUIRED, example = "20019")
    private Integer withdrawCount;

}
