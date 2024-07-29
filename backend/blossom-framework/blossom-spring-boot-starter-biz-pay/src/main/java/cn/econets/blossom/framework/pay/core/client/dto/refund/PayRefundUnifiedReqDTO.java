package cn.econets.blossom.framework.pay.core.client.dto.refund;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Unification Refund Request DTO
 *
 */
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PayRefundUnifiedReqDTO {

    /**
     * External order number
     *
     * Corresponding PayOrderExtensionDO of no Field
     */
    @NotEmpty(message = "External order number cannot be empty")
    private String outTradeNo;

    /**
     * External refund number
     *
     * Corresponding PayRefundDO of no Field
     */
    @NotEmpty(message = "Refund request number cannot be empty")
    private String outRefundNo;

    /**
     * Refund reason
     */
    @NotEmpty(message = "Refund reason cannot be empty")
    private String reason;

    /**
     * Payment amount，Unit：Points
     *
     * Currently WeChat Pay is refunding，This field must be passed
     */
    @NotNull(message = "The payment amount cannot be empty")
    @DecimalMin(value = "0", inclusive = false, message = "The payment amount must be greater than zero")
    private Integer payPrice;
    /**
     * Refund amount，Unit：Points
     */
    @NotNull(message = "Refund amount cannot be empty")
    @DecimalMin(value = "0", inclusive = false, message = "The payment amount must be greater than zero")
    private Integer refundPrice;

    /**
     * Refund result notify Callback address
     */
    @NotEmpty(message = "The callback address of payment result cannot be empty")
    @URL(message = "Payment results notify The callback address must be URL Format")
    private String notifyUrl;

}
