package cn.econets.blossom.module.trade.controller.admin.delivery.vo.expresstemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Express delivery template delivery fee settings Base VO，For adding shipping fee template
 */
@Data
public class DeliveryExpressTemplateChargeBaseVO {

    @Schema(description = "Number", example = "6592", hidden = true) // Because I want to keep it simple，Reuse this VO In update operation，So hidden for false
    private Long id;

    @Schema(description = "Region number list", requiredMode = Schema.RequiredMode.REQUIRED, example = "[1,120000]")
    @NotEmpty(message = "The area number list cannot be empty")
    private List<Integer> areaIds;

    @Schema(description = "First Quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    @NotNull(message = "The first quantity cannot be empty")
    private Double startCount;

    @Schema(description = "Starting price", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    @NotNull(message = "The starting price cannot be empty")
    private Integer startPrice;

    @Schema(description = "Number of follow-up items", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "The number of continuation items cannot be empty")
    private Double extraCount;

    @Schema(description = "Additional price", requiredMode = Schema.RequiredMode.REQUIRED, example = "2000")
    @NotNull(message = "Additional price cannot be empty")
    private Integer extraPrice;
}
