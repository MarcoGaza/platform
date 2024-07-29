package cn.econets.blossom.module.trade.controller.admin.delivery.vo.express;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
* Express Delivery Company Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class DeliveryExpressBaseVO {

    @Schema(description = "Courier company code", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The courier company code cannot be empty")
    private String code;

    @Schema(description = "Express delivery company name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    @NotNull(message = "The courier company name cannot be empty")
    private String name;

    @Schema(description = "Express Delivery Companylogo")
    private String logo;

    @Schema(description = "Sort", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The sort order cannot be empty")
    private Integer sort;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    private Integer status;

}
