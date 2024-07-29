package cn.econets.blossom.module.pay.controller.app.wallet.vo.recharge;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import java.util.Objects;

@Schema(description = "User APP - Create wallet recharge Request VO")
@Data
public class AppPayWalletRechargeCreateReqVO {

    @Schema(description = "Payment amount",  example = "1000")
    @Min(value = 1,  message = "The payment amount must be greater than zero")
    private Integer payPrice;

    @Schema(description = "Recharge package number", example = "1024")
    private Long packageId;

    @AssertTrue(message = "Recharge amount and recharge package cannot be empty at the same time")
    public boolean isValidPayPriceAndPackageId() {
        return Objects.nonNull(payPrice) || Objects.nonNull(packageId);
    }

}
