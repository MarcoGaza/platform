package cn.econets.blossom.module.pay.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Schema(description = "Management Backend - Payment order submission Request VO")
@Data
public class PayOrderSubmitReqVO {

    @Schema(description = "Payment order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The payment order number cannot be empty")
    private Long id;

    @Schema(description = "Payment channel", requiredMode = Schema.RequiredMode.REQUIRED, example = "wx_pub")
    @NotEmpty(message = "Payment channel cannot be empty")
    private String channelCode;

    @Schema(description = "Additional parameters for payment channels，For example，WeChat public account needs to be passed openid Parameters")
    private Map<String, String> channelExtras;

    @Schema(description = "Display Mode", example = "url") // See {@link PayDisplayModeEnum} Enumeration。If not passed，Each payment channel uses the default method
    private String displayMode;

    @Schema(description = "Return address")
    @URL(message = "The format of the jump back address must be URL")
    private String returnUrl;

}
