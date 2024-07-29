package cn.econets.blossom.module.trade.controller.admin.delivery.vo.expresstemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Express delivery template free shipping Base VO，For adding shipping fee template
 */
@Data
public class DeliveryExpressTemplateFreeBaseVO {

    @Schema(description = "Region number list", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1,120000]")
    @NotEmpty(message = "The area number list cannot be empty")
    private List<Integer> areaIds;

    @Schema(description = "Free shipping amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "5000")
    @NotNull(message = "The free shipping amount cannot be empty")
    private Integer freePrice;

    @Schema(description = "Number of package mails", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "The number of package mails cannot be empty")
    private Integer freeCount;

}
