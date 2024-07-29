package cn.econets.blossom.module.trade.dal.dataobject.order;

import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.trade.dal.dataobject.aftersale.AfterSaleDO;
import cn.econets.blossom.module.trade.dal.dataobject.cart.CartDO;
import cn.econets.blossom.module.trade.enums.order.TradeOrderItemAfterSaleStatusEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Transaction order item DO
 *
 */
@TableName(value = "trade_order_item", autoResultMap = true)
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class TradeOrderItemDO extends BaseDO {

    // ========== Basic information of order items ==========
    /**
     * Number
     */
    private Long id;
    /**
     * User Number
     *
     * Relationship MemberUserDO of id Number
     */
    private Long userId;
    /**
     * Order number
     *
     * Relationship {@link TradeOrderDO#getId()}
     */
    private Long orderId;
    /**
     * Shopping cart item number
     *
     * Relationship {@link CartDO#getId()}
     */
    private Long cartId;

    // ========== Basic information of the product; Too many redundant fields，Reduce associated queries ==========
    /**
     * Products SPU Number
     *
     * Relationship ProductSkuDO of spuId Number
     */
    private Long spuId;
    /**
     * Products SPU Name
     *
     * Redundant ProductSkuDO of spuName Number
     */
    private String spuName;
    /**
     * Products SKU Number
     *
     * Relationship ProductSkuDO of id Number
     */
    private Long skuId;
    /**
     * Attribute array，JSON Format
     *
     * Redundant ProductSkuDO of properties Field
     */
    @TableField(typeHandler = PropertyTypeHandler.class)
    private List<Property> properties;
    /**
     * Product images
     */
    private String picUrl;
    /**
     * Purchase quantity
     */
    private Integer count;
    /**
     * Do you want to comment?
     *
     * true - Evaluated
     * false - Not rated yet
     */
    private Boolean commentStatus;

    // ========== Price + Basic payment information ==========

    /**
     * Original price of product（Single），Unit：Points
     *
     * Corresponding ProductSkuDO of price Field
     * Corresponding taobao of order.price Field
     */
    private Integer price;
    /**
     * Discount amount（Total），Unit：Points
     *
     * Corresponding taobao of order.discount_fee Field
     */
    private Integer discountPrice;
    /**
     * Shipping amount（Total），Unit：Points
     */
    private Integer deliveryPrice;
    /**
     * Order price adjustment（Total），Unit：Points
     *
     * Positive number，Price increase；Negative number，Price reduction
     */
    private Integer adjustPrice;
    /**
     * Amount payable（Total），Unit：Points
     *
     * = {@link #price} * {@link #count}
     * - {@link #couponPrice}
     * - {@link #pointPrice}
     * - {@link #discountPrice}
     * + {@link #deliveryPrice}
     * + {@link #adjustPrice}
     * - {@link #vipPrice}
     */
    private Integer payPrice;

    // ========== Basic marketing information ==========

    /**
     * Coupon reduction amount，Unit：Points
     *
     * Corresponding taobao of trade.coupon_fee Field
     */
    private Integer couponPrice;
    /**
     * Amount of points deduction，Unit：Points
     *
     * Corresponding taobao of trade.point_fee Field
     */
    private Integer pointPrice;
    /**
     * Points used
     *
     * Purpose：Used for subsequent cancellation or after-sales orders，Need to return the gift
     */
    private Integer usePoint;
    /**
     * Gifted points
     *
     * Purpose：Used for subsequent cancellation or after-sales orders，Need to deduct the gift
     */
    private Integer givePoint;
    /**
     * VIP Amount of reduction，Unit：Points
     */
    private Integer vipPrice;

    // ========== Basic after-sales information ==========

    /**
     * After-sales order number
     *
     * Relationship {@link AfterSaleDO#getId()} Field
     */
    private Long afterSaleId;
    /**
     * After-sales status
     *
     * Enumeration {@link TradeOrderItemAfterSaleStatusEnum}
     */
    private Integer afterSaleStatus;

    /**
     * Product attributes
     */
    @Data
    public static class Property implements Serializable {

        /**
         * Property number
         *
         * Relationship ProductPropertyDO of id Number
         */
        private Long propertyId;
        /**
         * Attribute name
         *
         * Relationship ProductPropertyDO of name Field
         */
        private String propertyName;

        /**
         * Attribute value number
         *
         * Relationship ProductPropertyValueDO of id Number
         */
        private Long valueId;
        /**
         * Attribute value name
         *
         * Relationship ProductPropertyValueDO of name Field
         */
        private String valueName;

    }

    // TODO ：You can find some new ideas
    public static class PropertyTypeHandler extends AbstractJsonTypeHandler<List<Property>> {

        @Override
        protected List<Property> parse(String json) {
            return JsonUtils.parseArray(json, Property.class);
        }

        @Override
        protected String toJson(List<Property> obj) {
            return JsonUtils.toJsonString(obj);
        }

    }

}

