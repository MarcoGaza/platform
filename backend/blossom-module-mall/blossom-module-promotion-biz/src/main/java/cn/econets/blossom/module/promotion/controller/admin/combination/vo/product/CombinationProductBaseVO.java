package cn.econets.blossom.module.promotion.controller.admin.combination.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Group buy product Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class CombinationProductBaseVO {

    @Schema(description = "Products spuId", requiredMode = Schema.RequiredMode.REQUIRED, example = "30563")
    @NotNull(message = "Products spuId Cannot be empty")
    private Long spuId;

    @Schema(description = "Products skuId", requiredMode = Schema.RequiredMode.REQUIRED, example = "30563")
    @NotNull(message = "Products skuId Cannot be empty")
    private Long skuId;

    @Schema(description = "Group buying price，Unit points", requiredMode = Schema.RequiredMode.REQUIRED, example = "27682")
    @NotNull(message = "The group purchase price cannot be empty")
    private Integer combinationPrice;

}
