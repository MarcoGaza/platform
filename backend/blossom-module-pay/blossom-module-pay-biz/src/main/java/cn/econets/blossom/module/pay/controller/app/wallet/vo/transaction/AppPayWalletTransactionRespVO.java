package cn.econets.blossom.module.pay.controller.app.wallet.vo.transaction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "User APP - Wallet transaction pagination Response VO")
@Data
public class AppPayWalletTransactionRespVO {

    @Schema(description = "Business Classification", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer bizType;

    @Schema(description = "Transaction amount，Unit points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Long price;

    @Schema(description = "Running title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Potatoes")
    private String title;

    @Schema(description = "Trading Time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
