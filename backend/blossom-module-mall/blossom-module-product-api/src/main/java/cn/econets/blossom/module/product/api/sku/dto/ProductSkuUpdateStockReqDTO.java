package cn.econets.blossom.module.product.api.sku.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Products SKU Update Inventory Request DTO
 *
 * 
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSkuUpdateStockReqDTO {

    /**
     * Products SKU
     */
    @NotNull(message = "Products SKU Cannot be empty")
    private List<Item> items;

    @Data
    public static class Item {

        /**
         * Products SKU Number
         */
        @NotNull(message = "Products SKU The number cannot be empty")
        private Long id;

        /**
         * Inventory change quantity
         *
         * Positive number：Increase inventory
         * Negative number：Deduct inventory
         */
        @NotNull(message = "The inventory change quantity cannot be empty")
        private Integer incrCount;

    }

}
