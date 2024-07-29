package cn.econets.blossom.module.pay.controller.app.wallet.vo.recharge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User APP - User recharge package Response VO")
@Data
public class AppPayWalletPackageRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;
    @Schema(description = "Package Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Small set meal")
    private String name;

    @Schema(description = "Payment amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer payPrice;
    @Schema(description = "Gift amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    private Integer bonusPrice;

}
