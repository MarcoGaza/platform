package cn.econets.blossom.module.trade.controller.admin.delivery.vo.express;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Express delivery company simplified information Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryExpressSimpleRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "6592")
    @NotNull(message = "The number cannot be empty")
    private Long id;

    @Schema(description = "Express delivery company name", requiredMode = Schema.RequiredMode.REQUIRED, example = "SF Express")
    @NotNull(message = "The courier company name cannot be empty")
    private String name;

}
