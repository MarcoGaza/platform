package cn.econets.blossom.module.pay.api.order.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Payment order creation Request DTO
 */
@Data
public class PayOrderCreateReqDTO implements Serializable {

    public static final int SUBJECT_MAX_LENGTH = 32;

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
     * Product Title
     */
    @NotEmpty(message = "Product title cannot be empty")
    @Length(max = SUBJECT_MAX_LENGTH, message = "Product title cannot exceed 32")
    private String subject;
    /**
     * Product Description
     */
    @Length(max = 128, message = "The length of product description cannot exceed128")
    private String body;

    // ========== Order related fields ==========

    /**
     * Payment amount，Unit：Points
     */
    @NotNull(message = "The payment amount cannot be empty")
    @DecimalMin(value = "0", inclusive = false, message = "The payment amount must be greater than zero")
    private Integer price;

    /**
     * Payment expiration time
     */
    @NotNull(message = "The payment expiration time cannot be empty")
    private LocalDateTime expireTime;

}
