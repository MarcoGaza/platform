package cn.econets.blossom.module.pay.controller.admin.wallet.vo.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "User APP - Wallet transaction paging Response VO")
@Data
public class PayWalletTransactionRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Wallet Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Long walletId;

    @Schema(description = "Business Classification", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer bizType;

    @Schema(description = "Transaction amount，Unit points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Long price;

    @Schema(description = "Running title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Potatoes")
    private String title;

    @Schema(description = "Balance after transaction，Unit points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Long balance;

    @Schema(description = "Trading Time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    // TODO ：merchantOrderId Field，Need to PayWalletTransaction Store；Then，The front end also returns this field，The interface also displays the merchant name

}
