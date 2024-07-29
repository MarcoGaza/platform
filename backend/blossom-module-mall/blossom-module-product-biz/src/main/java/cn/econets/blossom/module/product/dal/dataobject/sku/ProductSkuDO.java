package cn.econets.blossom.module.product.dal.dataobject.sku;

import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.product.dal.dataobject.property.ProductPropertyDO;
import cn.econets.blossom.module.product.dal.dataobject.property.ProductPropertyValueDO;
import cn.econets.blossom.module.product.dal.dataobject.spu.ProductSpuDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import lombok.*;

import java.util.List;

/**
 * Products SKU DO
 *
 */
@TableName(value = "product_sku", autoResultMap = true)
@KeySequence("product_sku_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSkuDO extends BaseDO {

    /**
     * Products SKU Number，Self-increment
     */
    @TableId
    private Long id;
    /**
     * SPU Number
     *
     * Relationship {@link ProductSpuDO#getId()}
     */
    private Long spuId;
    /**
     * Attribute array，JSON Format
     */
    @TableField(typeHandler = PropertyTypeHandler.class)
    private List<Property> properties;
    /**
     * Product price，Unit：Points
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
     * Product barcode
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

    // ========== Marketing related fields =========

    // ========== Statistics related fields =========
    /**
     * Product sales
     */
    private Integer salesCount;

    /**
     * Product attributes
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Property {

        /**
         * Property number
         * Relationship {@link ProductPropertyDO#getId()}
         */
        private Long propertyId;
        /**
         * Attribute name
         * Redundant {@link ProductPropertyDO#getName()}
         *
         * Attention：Every time the attribute name changes，The redundancy needs to be updated
         */
        private String propertyName;

        /**
         * Property value number
         * Relationship {@link ProductPropertyValueDO#getId()}
         */
        private Long valueId;
        /**
         * Attribute value name
         * Redundant {@link ProductPropertyValueDO#getName()}
         *
         * Attention：Every time the attribute value name changes，The redundancy needs to be updated
         */
        private String valueName;

    }

    // TODO ：You can find some new ideas
    public static class PropertyTypeHandler extends AbstractJsonTypeHandler<Object> {

        @Override
        protected Object parse(String json) {
            return JsonUtils.parseArray(json, Property.class);
        }

        @Override
        protected String toJson(Object obj) {
            return JsonUtils.toJsonString(obj);
        }

    }

    // TODO integral from y
    // TODO pinkPrice from y
    // TODO seckillPrice from y
    // TODO pinkStock from y
    // TODO seckillStock from y

}

