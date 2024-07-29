package cn.econets.blossom.module.pay.controller.admin.wallet.vo.wallet;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - User wallet details Request VO")
@Data
public class PayWalletUserReqVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

}
