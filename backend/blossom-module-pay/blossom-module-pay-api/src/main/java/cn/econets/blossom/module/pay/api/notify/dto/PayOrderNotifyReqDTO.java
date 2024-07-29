package cn.econets.blossom.module.pay.api.notify.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Payment order notification Request DTO
 *
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayOrderNotifyReqDTO {

    /**
     * Merchant order number
     */
    @NotEmpty(message = "Merchant order number cannot be empty")
    private String merchantOrderId;

    /**
     * Payment order number
     */
    @NotNull(message = "The payment order number cannot be empty")
    private Long payOrderId;

}
