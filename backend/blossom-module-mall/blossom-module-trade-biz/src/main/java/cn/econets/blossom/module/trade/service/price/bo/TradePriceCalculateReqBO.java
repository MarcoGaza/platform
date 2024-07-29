package cn.econets.blossom.module.trade.service.price.bo;

import cn.econets.blossom.module.trade.enums.delivery.DeliveryTypeEnum;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Price calculation Request BO
 *
 */
@Data
public class TradePriceCalculateReqBO {

    /**
     * User Number
     *
     * Corresponding MemberUserDO of id Number
     */
    private Long userId;

    /**
     * Coupon number
     *
     * Corresponding CouponDO of id Number
     */
    private Long couponId;

    /**
     * Whether to use points
     */
    @NotNull(message = "Whether to use points cannot be empty")
    private Boolean pointStatus;

    /**
     * Delivery method
     *
     * Enumeration {@link DeliveryTypeEnum}
     */
    private Integer deliveryType;
    /**
     * Delivery address number
     *
     * Corresponding MemberAddressDO of id Number
     */
    private Long addressId;
    /**
     * Self-pickup store number
     *
     * Corresponding PickUpStoreDO of id Number
     */
    private Long pickUpStoreId;

    /**
     * Products SKU Array
     */
    @NotNull(message = "Product array cannot be empty")
    private List<Item> items;

    // ========== Second sale related fields ==========
    /**
     * Second sale activity number
     */
    private Long seckillActivityId;

    // ========== Group buying activity related fields ==========
    /**
     * Group buying activity number
     */
    private Long combinationActivityId;

    /**
     * Group leader number
     */
    private Long combinationHeadId;

    // ========== Bargaining activity related fields ==========
    /**
     * Bargaining record number
     */
    private Long bargainRecordId;

    /**
     * Products SKU
     */
    @Data
    @Valid
    public static class Item {

        /**
         * SKU Number
         */
        @NotNull(message = "Products SKU The number cannot be empty")
        private Long skuId;

        /**
         * SKU Quantity
         */
        @NotNull(message = "Products SKU The quantity cannot be empty")
        @Min(value = 0L, message = "Products SKU The quantity must be greater than or equal to 0")
        private Integer count;

        /**
         * The shopping cart item ID
         */
        private Long cartId;

        /**
         * Is it selected?
         */
        @NotNull(message = "Whether the selection is selected cannot be empty")
        private Boolean selected;

    }
}
