package cn.econets.blossom.module.promotion.controller.admin.seckill.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Second-sale participating products Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 *
 */
@Data
public class SeckillProductBaseVO {

    @Schema(description = "Productssku_id", requiredMode = Schema.RequiredMode.REQUIRED, example = "30563")
    @NotNull(message = "Productssku_idCannot be empty")
    private Long skuId;

    @Schema(description = "Second kill amount，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "6689")
    @NotNull(message = "Second kill amount，Unit：The score cannot be empty")
    private Integer seckillPrice;

    @Schema(description = "Second kill inventory", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "Second sale inventory cannot be empty")
    private Integer stock;

}
