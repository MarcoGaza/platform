package cn.econets.blossom.framework.pay.core.client.dto.order;

import cn.econets.blossom.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * Unified order Request DTO
 *
 */
@Data
public class PayOrderUnifiedReqDTO {

    /**
     * User IP
     */
    @NotEmpty(message = "User IP Cannot be empty")
    private String userIp;

    // ========== Merchant related fields ==========

    /**
     * External order number
     *
     * Corresponding PayOrderExtensionDO of no Field
     */
    @NotEmpty(message = "External order number cannot be empty")
    private String outTradeNo;
    /**
     * Product Title
     */
    @NotEmpty(message = "Product title cannot be empty")
    @Length(max = 32, message = "Product title cannot exceed 32")
    private String subject;
    /**
     * Product description information
     */
    @Length(max = 128, message = "The length of product description information cannot exceed128")
    private String body;
    /**
     * Payment results notify Callback address
     */
    @NotEmpty(message = "The callback address of payment result cannot be empty")
    @URL(message = "Payment results notify The callback address must be URL Format")
    private String notifyUrl;
    /**
     * Payment results return Callback address
     */
    @URL(message = "Payment results return The callback address must be URL Format")
    private String returnUrl;

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

    // ========== Extended parameters ==========
    /**
     * Additional parameters for payment channels
     *
     * For example，WeChat public account needs to be passed openid Parameters
     */
    private Map<String, String> channelExtras;

    /**
     * Display Mode
     *
     * If not passed，Each payment channel uses the default method
     *
     * Enumeration {@link PayOrderDisplayModeEnum}
     */
    private String displayMode;

}
