package cn.econets.blossom.module.pay.api.order.dto;

import cn.econets.blossom.module.pay.enums.order.PayOrderStatusEnum;
import lombok.Data;

/**
 * Payment order information Response DTO
 *
 *
 */
@Data
public class PayOrderRespDTO {

    /**
     * Order Number，Database auto-increment
     */
    private Long id;
    /**
     * Channel Code
     *
     * Enumeration PayChannelEnum
     */
    private String channelCode;

    // ========== Merchant related fields ==========
    /**
     * Merchant order number
     * For example，Internal system A Order number。It is necessary to ensure that each PayMerchantDO Only
     */
    private String merchantOrderId;

    // ========== Order related fields ==========
    /**
     * Payment amount，Unit：Points
     */
    private Integer price;
    /**
     * Payment Status
     *
     * Enumeration {@link PayOrderStatusEnum}
     */
    private Integer status;

    // ========== Channel related fields ==========

}
