package cn.econets.blossom.module.pay.controller.admin.transfer.vo;

import cn.econets.blossom.framework.common.util.validation.ValidationUtils;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.module.pay.enums.transfer.PayTransferTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Validator;
import javax.validation.constraints.*;
import java.util.Map;

import static cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants.NOT_IMPLEMENTED;
import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.pay.enums.transfer.PayTransferTypeEnum.*;

@Schema(description = "Management Backend - Initiate transfer Request VO")
@Data
public class PayTransferCreateReqVO {

    @Schema(description = "Application number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Application number cannot be empty")
    private Long appId;

    @Schema(description = "Merchant transfer order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Merchant transfer order number cannot be empty")
    private String merchantTransferId;

    @Schema(description = "Transfer Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Transfer type cannot be empty")
    @InEnum(PayTransferTypeEnum.class)
    private Integer type;

    @Schema(description = "Transfer channel", requiredMode = Schema.RequiredMode.REQUIRED, example = "alipay_pc")
    @NotEmpty(message = "The transfer channel cannot be empty")
    private String channelCode;

    @Min(value = 1, message = "The transfer amount must be greater than zero")
    @NotNull(message = "The transfer amount cannot be empty")
    private Integer price;

    @Schema(description = "Transfer Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Example transfer")
    @NotEmpty(message = "The transfer title cannot be empty")
    private String subject;

    @Schema(description = "Name of the recipient", example = "test1")
    @NotBlank(message = "The payee's name cannot be empty", groups = {Alipay.class})
    private String userName;

    @Schema(description = "Alipay login number",  example = "test1@sandbox.com")
    @NotBlank(message = "Alipay login number cannot be empty", groups = {Alipay.class})
    private String alipayLogonId;

    @Schema(description = "WeChat openId",  example = "oLefc4g5Gxx")
    @NotBlank(message = "WeChat openId Cannot be empty", groups = {WxPay.class})
    private String openid;

    @Schema(description = "Additional parameters for transfer channels")
    private Map<String, String> channelExtras;

    public void validate(Validator validator) {
       PayTransferTypeEnum transferType = typeOf(type);
        switch (transferType) {
            case ALIPAY_BALANCE: {
                ValidationUtils.validate(validator, this, Alipay.class);
                break;
            }
            case WX_BALANCE: {
                ValidationUtils.validate(validator, this, WxPay.class);
                break;
            }
            default: {
                throw new UnsupportedOperationException("To be implemented");
            }
        }
    }

    @AssertTrue(message = "The transfer type and transfer channel do not match")
    public boolean isValidChannelCode() {
        PayTransferTypeEnum transferType = typeOf(type);
        switch (transferType) {
            case ALIPAY_BALANCE: {
                return PayChannelEnum.isAlipay(channelCode);
            }
            case WX_BALANCE:
            case BANK_CARD:
            case WALLET_BALANCE: {
                throw exception(NOT_IMPLEMENTED);
            }
        }
        return Boolean.FALSE;
    }

}
