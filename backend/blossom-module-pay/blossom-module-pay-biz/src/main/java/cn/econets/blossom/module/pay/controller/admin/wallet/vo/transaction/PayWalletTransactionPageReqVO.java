package cn.econets.blossom.module.pay.controller.admin.wallet.vo.transaction;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Wallet transaction paging Request VO")
@Data
public class PayWalletTransactionPageReqVO extends PageParam  {

    @Schema(description = "Wallet Number",  example = "1")
    private Long walletId;

}
