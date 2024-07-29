package cn.econets.blossom.module.pay.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Payment order submission Response VO")
@Data
public class PayOrderSubmitRespVO {

    @Schema(description = "Payment Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "10") // See PayOrderStatusEnum Enumeration
    private Integer status;

    @Schema(description = "Display Mode", requiredMode = Schema.RequiredMode.REQUIRED, example = "url") // See PayDisplayModeEnum Enumeration
    private String displayMode;
    @Schema(description = "Display content", requiredMode = Schema.RequiredMode.REQUIRED)
    private String displayContent;

}
