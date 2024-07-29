package cn.econets.blossom.module.trade.controller.app.brokerage.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Distribution ranking users（Based on the number of users） Response VO")
@Data
public class AppBrokerageUserRankByPriceRespVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Long id;

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "Xiao Wang")
    private String nickname;

    @Schema(description = "User avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xxx.jpg")
    private String avatar;

    @Schema(description = "Commission amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer brokeragePrice;

}
