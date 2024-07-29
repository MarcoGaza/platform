package cn.econets.blossom.module.trade.framework.delivery.core.client.dto.kd100;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Express Delivery 100 Express Tracking Req DTO
 *
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Kd100ExpressQueryReqDTO {

    /**
     * Express delivery company code
     */
    @JsonProperty("com")
    private String expressCode;

    /**
     * Express delivery number
     */
    @JsonProperty("num")
    private String logisticsNo;

    /**
     * Receive、Sender's phone number
     */
    private String phone;

}
