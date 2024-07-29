package cn.econets.blossom.module.statistics.controller.admin.pay.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Payment Statistics Response VO")
@Data
public class PaySummaryRespVO {

    @Schema(description = "Recharge amount，Unit points", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer rechargePrice;

}
