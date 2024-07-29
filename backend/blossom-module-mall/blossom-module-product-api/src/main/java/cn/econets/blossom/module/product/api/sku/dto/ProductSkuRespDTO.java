package cn.econets.blossom.module.product.api.sku.dto;

import cn.econets.blossom.module.product.api.property.dto.ProductPropertyValueDetailRespDTO;
import lombok.Data;

import java.util.List;

/**
 * Products SKU Information Response DTO
 *
 * 
 */
@Data
public class ProductSkuRespDTO {

    /**
     * Products SKU Number，Self-increment
     */
    private Long id;
    /**
     * SPU Number
     */
    private Long spuId;

    /**
     * Attribute array
     */
    private List<ProductPropertyValueDetailRespDTO> properties;
    /**
     * Sales Price，Unit：Points
     */
    private Integer price;
    /**
     * Market price，Unit：Points
     */
    private Integer marketPrice;
    /**
     * Cost price，Unit：Points
     */
    private Integer costPrice;
    /**
     * SKU Barcode
     */
    private String barCode;
    /**
     * Image address
     */
    private String picUrl;
    /**
     * Inventory
     */
    private Integer stock;
    /**
     * Product weight，Unit：kg kilogram
     */
    private Double weight;
    /**
     * Product volume，Unit：m^3 square meters
     */
    private Double volume;
    /**
     * First-level distribution commission，Unit：Points
     */
    private Integer firstBrokeragePrice;
    /**
     * Second-level distribution commission，Unit：Points
     */
    private Integer secondBrokeragePrice;

}
