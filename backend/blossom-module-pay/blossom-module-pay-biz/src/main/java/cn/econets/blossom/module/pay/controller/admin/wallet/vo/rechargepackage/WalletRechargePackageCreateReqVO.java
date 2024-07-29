package cn.econets.blossom.module.pay.controller.admin.wallet.vo.rechargepackage;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Create a recharge package Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WalletRechargePackageCreateReqVO extends WalletRechargePackageBaseVO {

}
