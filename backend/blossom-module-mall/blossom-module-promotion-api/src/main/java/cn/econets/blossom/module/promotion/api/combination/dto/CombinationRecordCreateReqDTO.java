package cn.econets.blossom.module.promotion.api.combination.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Creation of group purchase record Request DTO
 *
 */
@Data
public class CombinationRecordCreateReqDTO {

    /**
     * Group buying activity number
     */
    @NotNull(message = "The group purchase activity number cannot be empty")
    private Long activityId;
    /**
     * spu Number
     */
    @NotNull(message = "spu The number cannot be empty")
    private Long spuId;
    /**
     * sku Number
     */
    @NotNull(message = "sku The number cannot be empty")
    private Long skuId;
    /**
     * Quantity of goods purchased
     */
    @NotNull(message = "The purchase quantity cannot be empty")
    private Integer count;
    /**
     * Order Number
     */
    @NotNull(message = "Order number cannot be empty")
    private Long orderId;
    /**
     * User Number
     */
    @NotNull(message = "User ID cannot be empty")
    private Long userId;
    /**
     * Team Leader Number
     */
    private Long headId;
    /**
     * Unit price of group purchase product
     */
    @NotNull(message = "The unit price of group purchase products cannot be empty")
    private Integer combinationPrice;

}
