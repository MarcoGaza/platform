package cn.econets.blossom.module.pay.api.notify.dto;

import cn.econets.blossom.module.pay.enums.refund.PayRefundStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Notification of refund Request DTO
 *
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayRefundNotifyReqDTO {

    /**
     * Merchant refund order number
     */
    @NotEmpty(message = "Merchant refund order number cannot be empty")
    private String merchantOrderId;

    /**
     * Payment refund number
     */
    @NotNull(message = "The payment refund number cannot be empty")
    private Long payRefundId;

}
