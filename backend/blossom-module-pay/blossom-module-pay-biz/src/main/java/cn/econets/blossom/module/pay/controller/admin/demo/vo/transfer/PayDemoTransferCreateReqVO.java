package cn.econets.blossom.module.pay.controller.admin.demo.vo.transfer;

import cn.econets.blossom.framework.common.util.validation.ValidationUtils;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.framework.pay.core.enums.transfer.PayTransferTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static cn.econets.blossom.module.pay.enums.transfer.PayTransferTypeEnum.*;


@Schema(description = "Management Backend - Example transfer order creation Request VO")
@Data
public class PayDemoTransferCreateReqVO {

    @Schema(description = "Transfer Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Transfer type cannot be empty")
    @InEnum(PayTransferTypeEnum.class)
    private Integer type;

    @Schema(description = "Transfer amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "The transfer amount cannot be empty")
    @Min(value = 1, message = "The transfer amount must be greater than zero")
    private Integer price;

    @Schema(description = "Recipient's name", example = "test1")
    @NotBlank(message = "The payee's name cannot be empty", groups = {Alipay.class})
    private String userName;

    // ========== Alipay transfer related fields ==========
    @Schema(description = "Alipay login number,Support email and mobile phone number formats", example = "test1@@sandbox.com")
    @NotBlank(message = "Alipay login number cannot be empty", groups = {Alipay.class})
    private String alipayLogonId;

    // ========== WeChat transfer related fields ==========
    @Schema(description = "WeChat openId", example = "oLefc4g5Gxx")
    @NotBlank(message = "WeChat openId Cannot be empty", groups = {WxPay.class})
    private String openid;


    // ========== Transfer to bank card and wallet related fields To be supplemented ==========

    public void validate(Validator validator) {
        PayTransferTypeEnum transferType = PayTransferTypeEnum.typeOf(type);
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

}
