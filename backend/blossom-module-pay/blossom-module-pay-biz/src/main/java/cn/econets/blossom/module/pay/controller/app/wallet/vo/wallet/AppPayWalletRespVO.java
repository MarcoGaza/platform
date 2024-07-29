package cn.econets.blossom.module.pay.controller.app.wallet.vo.wallet;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User APP - User wallet Response VO")
@Data
public class AppPayWalletRespVO {

    @Schema(description = "Wallet Balance，Unit points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer balance;

    @Schema(description = "Cumulative expenditure，Unit points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer totalExpense;

    @Schema(description = "Accumulated recharge，Unit points", requiredMode = Schema.RequiredMode.REQUIRED, example = "2000")
    private Integer totalRecharge;

}
