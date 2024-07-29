package cn.econets.blossom.module.pay.controller.app.wallet.vo.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User APP - Wallet Transaction Statistics Request VO")
@Data
public class AppPayWalletTransactionSummaryRespVO {

    @Schema(description = "Cumulative expenditure，Unit points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Integer totalExpense;

    @Schema(description = "Cumulative income，Unit points", requiredMode = Schema.RequiredMode.REQUIRED, example = "2000")
    private Integer totalIncome;

}
