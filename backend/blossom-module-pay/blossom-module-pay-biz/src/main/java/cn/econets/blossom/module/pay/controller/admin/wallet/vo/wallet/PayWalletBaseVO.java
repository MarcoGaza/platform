package cn.econets.blossom.module.pay.controller.admin.wallet.vo.wallet;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * User wallet Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class PayWalletBaseVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "20020")
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    @Schema(description = "User Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "User type cannot be empty")
    private Integer userType;

    @Schema(description = "Balance，Unit points", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Balance，Unit points cannot be empty")
    private Integer balance;

    @Schema(description = "Cumulative expenditure，Unit points", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Cumulative expenditure，Unit points cannot be empty")
    private Integer totalExpense;

    @Schema(description = "Accumulated recharge，Unit points", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Accumulated recharge，Unit points cannot be empty")
    private Integer totalRecharge;

    @Schema(description = "Frozen amount，Unit points", requiredMode = Schema.RequiredMode.REQUIRED, example = "20737")
    @NotNull(message = "Frozen amount，Unit points cannot be empty")
    private Integer freezePrice;

}
