package cn.econets.blossom.module.trade.controller.admin.aftersale.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Transaction rejected after sale Request VO")
@Data
public class AfterSaleDisagreeReqVO {

    @Schema(description = "After-sales number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "After-sales number cannot be empty")
    private Long id;

    @Schema(description = "Approval Notes", requiredMode = Schema.RequiredMode.REQUIRED, example = "Guess")
    @NotEmpty(message = "Approval remarks cannot be empty")
    private String auditReason;

}
