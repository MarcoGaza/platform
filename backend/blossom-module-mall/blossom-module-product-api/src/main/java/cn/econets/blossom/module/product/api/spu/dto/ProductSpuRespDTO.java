package cn.econets.blossom.module.product.api.spu.dto;

import cn.econets.blossom.module.product.enums.spu.ProductSpuStatusEnum;
import lombok.Data;

// TODO LeeYan9: ProductSpuRespDTO
/**
 * Products SPU Information Response DTO
 *
 * 
 */
@Data
public class ProductSpuRespDTO {

    /**
     * Products SPU Number，Self-increment
     */
    private Long id;

    // ========== Basic Information =========

    /**
     * Product Name
     */
    private String name;
    /**
     * Unit
     *
     * Corresponding product_unit Data dictionary
     */
    private Integer unit;

    /**
     * Product Category Number
     */
    private Long categoryId;
    /**
     * Product cover image
     */
    private String picUrl;

    /**
     * Product Status
     * <p>
     * Enumeration {@link ProductSpuStatusEnum}
     */
    private Integer status;

    // ========== SKU Related fields =========

    /**
     * Specification type
     *
     * false - Single specification
     * true - Multiple specifications
     */
    private Boolean specType;
    /**
     * Product price，Unit use：Points
     */
    private Integer price;
    /**
     * Market price，Unit use：Points
     */
    private Integer marketPrice;
    /**
     * Cost price，Unit use：Points
     */
    private Integer costPrice;
    /**
     * Inventory
     */
    private Integer stock;

    // ========== Logistics related fields =========

    /**
     * Logistics configuration template number
     *
     * Corresponding TradeDeliveryExpressTemplateDO of id Number
     */
    private Long deliveryTemplateId;

    // ========== Marketing related fields =========

    /**
     * Send points
     */
    private Integer giveIntegral;

    // ========== Distribution related fields =========

    /**
     * Distribution Type
     *
     * false - Default
     * true - Set by yourself
     */
    private Boolean subCommissionType;

}
