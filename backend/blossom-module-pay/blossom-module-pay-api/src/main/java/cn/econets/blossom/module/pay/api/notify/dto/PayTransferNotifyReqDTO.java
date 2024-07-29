package cn.econets.blossom.module.pay.api.notify.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Notification of transfer order Request DTO
 *
 *
 */
@Data
public class PayTransferNotifyReqDTO {

    /**
     * Merchant transfer order number
     */
    @NotEmpty(message = "Merchant transfer order number cannot be empty")
    private String merchantTransferId;

    /**
     * Transfer order number
     */
    @NotNull(message = "The transfer order number cannot be empty")
    private Long payTransferId;
}
