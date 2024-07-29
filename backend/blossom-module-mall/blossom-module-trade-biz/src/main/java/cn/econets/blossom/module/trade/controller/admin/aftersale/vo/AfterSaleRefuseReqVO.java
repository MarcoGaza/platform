package cn.econets.blossom.module.trade.controller.admin.aftersale.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Refused to receive goods after transaction Request VO")
@Data
public class AfterSaleRefuseReqVO {

    @Schema(description = "After-sales number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "After-sales number cannot be empty")
    private Long id;

    @Schema(description = "Receipt Notes", requiredMode = Schema.RequiredMode.REQUIRED, example = "Guess")
    @NotNull(message = "Delivery notes cannot be empty")
    private String refuseMemo;

}
