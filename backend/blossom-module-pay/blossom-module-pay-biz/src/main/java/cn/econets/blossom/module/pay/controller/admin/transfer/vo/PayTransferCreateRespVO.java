package cn.econets.blossom.module.pay.controller.admin.transfer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Initiate transfer Response VO")
@Data
public class PayTransferCreateRespVO {

    @Schema(description = "Transfer order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Transfer Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1") // See PayTransferStatusEnum Enumeration
    private Integer status;

}
