package cn.econets.blossom.module.trade.controller.app.cart.vo;

import cn.econets.blossom.module.trade.controller.app.base.sku.AppProductSkuBaseRespVO;
import cn.econets.blossom.module.trade.controller.app.base.spu.AppProductSpuBaseRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "User App - User's shopping list Response VO")
@Data
public class AppCartListRespVO {

    /**
     * A valid shopping item array
     */
    private List<Cart> validList;

    /**
     * Invalid shopping item array
     */
    private List<Cart> invalidList;

    @Schema(description = "Shopping Item")
    @Data
    public static class Cart {

        @Schema(description = "The number of the shopping item", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
        private Long id;

        @Schema(description = "Quantity of goods", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Integer count;

        @Schema(description = "Is it selected?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
        private Boolean selected;

        /**
         * Products SPU
         */
        private AppProductSpuBaseRespVO spu;
        /**
         * Products SKU
         */
        private AppProductSkuBaseRespVO sku;

    }

}
