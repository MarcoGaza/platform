package cn.econets.blossom.module.trade.controller.admin.delivery.vo.expresstemplate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* Express delivery fee template Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class DeliveryExpressTemplateBaseVO {

    @Schema(description = "Template name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wang Wu")
    @NotNull(message = "Template name cannot be empty")
    private String name;

    @Schema(description = "Delivery billing method 1:By item 2:By weight 3:By volume", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Delivery billing method 1:By item 2:By weight 3:Cannot be empty by volume")
    private Integer chargeMode;

    @Schema(description = "Sort", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The sort order cannot be empty")
    private Integer sort;

}
