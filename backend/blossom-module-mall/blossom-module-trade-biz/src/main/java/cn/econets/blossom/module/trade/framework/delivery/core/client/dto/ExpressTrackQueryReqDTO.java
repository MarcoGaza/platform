package cn.econets.blossom.module.trade.framework.delivery.core.client.dto;

import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryExpressDO;
import lombok.Data;

/**
 * Express delivery track query Req DTO
 *
 */
@Data
public class ExpressTrackQueryReqDTO {

    /**
     * Express delivery company code
     *
     * Corresponding {@link DeliveryExpressDO#getCode()}
     */
    private String expressCode;

    /**
     * Shipping express number
     */
    private String logisticsNo;

    /**
     * Receive、Sender's phone number
     */
    private String phone;

}
