package cn.econets.blossom.module.pay.api.refund.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Refund order creation Request DTO
 *
 *
 */
@Data
public class PayRefundCreateReqDTO {

    /**
     * Application number
     */
    @NotNull(message = "Application number cannot be empty")
    private Long appId;
    /**
     * User IP
     */
    @NotEmpty(message = "User IP Cannot be empty")
    private String userIp;

    // ========== Merchant related fields ==========
    /**
     * Merchant order number
     */
    @NotEmpty(message = "Merchant order number cannot be empty")
    private String merchantOrderId;

    /**
     * Merchant refund number
     */
    @NotEmpty(message = "Merchant refund number cannot be empty")
    private String merchantRefundId;

    /**
     * Refund Description
     */
    @NotEmpty(message = "Refund description cannot be empty")
    @Length(max = 128, message = "Refund description length cannot exceed 128")
    private String reason;

    // ========== Order related fields ==========

    /**
     * Refund amount，Unit：Points
     */
    @NotNull(message = "Refund amount cannot be empty")
    @Min(value = 1, message = "Refund amount must be greater than zero")
    private Integer price;

}
