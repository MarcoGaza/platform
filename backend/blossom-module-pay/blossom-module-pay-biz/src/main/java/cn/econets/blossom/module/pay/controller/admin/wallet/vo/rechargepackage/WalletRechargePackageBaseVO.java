package cn.econets.blossom.module.pay.controller.admin.wallet.vo.rechargepackage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Recharge Package Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class WalletRechargePackageBaseVO {

    @Schema(description = "Package Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    @NotNull(message = "Package name cannot be empty")
    private String name;

    @Schema(description = "Payment amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "16454")
    @NotNull(message = "The payment amount cannot be empty")
    private Integer payPrice;

    @Schema(description = "Gift amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "20887")
    @NotNull(message = "The gift amount cannot be empty")
    private Integer bonusPrice;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "Status cannot be empty")
    private Byte status;

}
