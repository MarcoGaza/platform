package cn.econets.blossom.module.pay.controller.app.wallet.vo.recharge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User APP - Create wallet recharge Resp VO")
@Data
public class AppPayWalletRechargeCreateRespVO {

    @Schema(description = "Wallet recharge number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Payment order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Long payOrderId;

}
