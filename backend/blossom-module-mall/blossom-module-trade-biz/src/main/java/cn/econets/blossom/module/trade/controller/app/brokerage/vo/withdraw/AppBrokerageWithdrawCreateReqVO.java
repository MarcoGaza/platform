package cn.econets.blossom.module.trade.controller.app.brokerage.vo.withdraw;

import cn.econets.blossom.framework.common.util.validation.ValidationUtils;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.Validator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Schema(description = "User App - Distribution withdrawal creation Request VO")
@Data
public class AppBrokerageWithdrawCreateReqVO {

    @Schema(description = "Withdrawal method", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @InEnum(value = BrokerageWithdrawTypeEnum.class, message = "Withdrawal method must be {value}")
    private Integer type;

    @Schema(description = "Withdrawal amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    @PositiveOrZero(message = "Withdrawal amount cannot be less than 0")
    @NotNull(message = "Withdrawal amount cannot be empty")
    private Integer price;

    // ========== Bank card、WeChat、Alipay Withdrawal related fields ==========

    @Schema(description = "Withdrawal Account", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456789")
    @NotBlank(message = "Withdrawal account cannot be empty", groups = {Bank.class, Wechat.class, Alipay.class})
    private String accountNo;

    // ========== WeChat、Alipay Withdrawal related fields ==========

    @Schema(description = "Picture of payment code", example = "https://www.econets.cn/1.png")
    @URL(message = "Picture of payment code，Must be one URL")
    private String accountQrCodeUrl;

    // ========== Bank card Withdrawal related fields ==========

    @Schema(description = "Cardholder Name", example = "Zhang San")
    @NotBlank(message = "Cardholder name cannot be empty", groups = {Bank.class})
    private String name;
    @Schema(description = "Withdrawal bank", example = "1")
    @NotNull(message = "Withdrawal bank cannot be empty", groups = {Bank.class})
    private Integer bankName;
    @Schema(description = "Account opening address", example = "Haidian Branch")
    private String bankAddress;

    public interface Wallet {
    }

    public interface Bank {
    }

    public interface Wechat {
    }

    public interface Alipay {
    }

    public void validate(Validator validator) {
        if (BrokerageWithdrawTypeEnum.WALLET.getType().equals(type)) {
            ValidationUtils.validate(validator, this, Wallet.class);
        } else if (BrokerageWithdrawTypeEnum.BANK.getType().equals(type)) {
            ValidationUtils.validate(validator, this, Bank.class);
        } else if (BrokerageWithdrawTypeEnum.WECHAT.getType().equals(type)) {
            ValidationUtils.validate(validator, this, Wechat.class);
        } else if (BrokerageWithdrawTypeEnum.ALIPAY.getType().equals(type)) {
            ValidationUtils.validate(validator, this, Alipay.class);
        }
    }

}
