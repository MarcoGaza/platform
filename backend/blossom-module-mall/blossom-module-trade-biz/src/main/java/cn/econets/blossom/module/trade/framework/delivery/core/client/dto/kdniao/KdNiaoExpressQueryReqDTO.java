package cn.econets.blossom.module.trade.framework.delivery.core.client.dto.kdniao;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * ExpressBird Express Query Req DTO
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KdNiaoExpressQueryReqDTO {

    /**
     * Express delivery company code
     */
    @JsonProperty("ShipperCode")
    private String expressCode;
    /**
     * Express delivery number
     */
    @JsonProperty("LogisticCode")
    private String logisticsNo;
    /**
     * Order Number
     */
    @JsonProperty("OrderCode")
    private String orderNo;

}
