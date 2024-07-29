package cn.econets.blossom.module.product.dal.dataobject.spu;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.IntegerListTypeHandler;
import cn.econets.blossom.module.product.dal.dataobject.brand.ProductBrandDO;
import cn.econets.blossom.module.product.dal.dataobject.category.ProductCategoryDO;
import cn.econets.blossom.module.product.dal.dataobject.sku.ProductSkuDO;
import cn.econets.blossom.module.product.enums.spu.ProductSpuStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.util.List;

/**
 * Products SPU DO
 *
 */
@TableName(value = "product_spu", autoResultMap = true)
@KeySequence("product_spu_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpuDO extends BaseDO {

    /**
     * Products SPU Number，Self-increment
     */
    @TableId
    private Long id;

    // ========== Basic Information =========

    /**
     * Product Name
     */
    private String name;
    /**
     * Keywords
     */
    private String keyword;
    /**
     * Product Introduction
     */
    private String introduction;
    /**
     * Product details
     */
    private String description;

    /**
     * Product Category Number
     *
     * Relationship {@link ProductCategoryDO#getId()}
     */
    private Long categoryId;
    /**
     * Product brand number
     *
     * Relationship {@link ProductBrandDO#getId()}
     */
    private Long brandId;
    /**
     * Product cover image
     */
    private String picUrl;
    /**
     * Product Carousel
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> sliderPicUrls;

    /**
     * Sort Field
     */
    private Integer sort;
    /**
     * Product Status
     *
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
     *
     * Based on its corresponding {@link ProductSkuDO#getPrice()} skuThe product with the lowest unit price
     */
    private Integer price;
    /**
     * Market price，Unit use：Points
     *
     * Based on its corresponding {@link ProductSkuDO#getMarketPrice()} skuThe product with the lowest unit price
     */
    private Integer marketPrice;
    /**
     * Cost price，Unit use：Points
     *
     * Based on its corresponding {@link ProductSkuDO#getCostPrice()} skuThe product with the lowest unit price
     */
    private Integer costPrice;
    /**
     * Inventory
     *
     * Based on its corresponding {@link ProductSkuDO#getStock()} Summation
     */
    private Integer stock;

    // ========== Logistics related fields =========

    /**
     * Shipping method array
     *
     * Corresponding DeliveryTypeEnum Enumeration
     */
    @TableField(typeHandler = IntegerListTypeHandler.class)
    private List<Integer> deliveryTypes;
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

    // TODO The field is expected to be changed to brokerageType
    /**
     * Distribution Type
     *
     * false - Default
     * true - Set by yourself
     */
    private Boolean subCommissionType;

    // ========== Statistics related fields =========

    /**
     * Product Sales
     */
    private Integer salesCount;
    /**
     * Virtual sales
     */
    private Integer virtualSalesCount;
    /**
     * Views
     */
    private Integer browseCount;
}
