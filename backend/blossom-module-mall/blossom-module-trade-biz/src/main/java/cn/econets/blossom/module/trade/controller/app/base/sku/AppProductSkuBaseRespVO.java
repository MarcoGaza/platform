package cn.econets.blossom.module.trade.controller.app.base.sku;

import cn.econets.blossom.module.trade.controller.app.base.property.AppProductPropertyValueDetailRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Products SKU Basics Response VO
 *
 */
@Data
public class AppProductSkuBaseRespVO {

    @Schema(description = "Primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Image address", example = "https://www.econets.cn/xx.png")
    private String picUrl;

    @Schema(description = "Sales Price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer price;

    @Schema(description = "Inventory", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer stock;

    /**
     * Attribute array
     */
    private List<AppProductPropertyValueDetailRespVO> properties;

}
