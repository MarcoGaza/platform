package cn.econets.blossom.module.pay.api.refund.dto;

import cn.econets.blossom.module.pay.enums.refund.PayRefundStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Refund order information Response DTO
 *
 *
 */
@Data
public class PayRefundRespDTO {

    /**
     * Refund order number
     */
    private Long id;

    // ========== Refund related fields ==========
    /**
     * Refund Status
     *
     * Enumeration {@link PayRefundStatusEnum}
     */
    private Integer status;
    /**
     * Refund amount，Unit：Points
     */
    private Integer refundPrice;

    // ========== Merchant related fields ==========
    /**
     * Merchant order number
     */
    private String merchantOrderId;
    /**
     * Refund success time
     */
    private LocalDateTime successTime;

}
