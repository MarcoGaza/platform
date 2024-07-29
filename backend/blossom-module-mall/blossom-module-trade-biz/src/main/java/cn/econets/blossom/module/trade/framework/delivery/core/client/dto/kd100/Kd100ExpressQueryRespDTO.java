package cn.econets.blossom.module.trade.framework.delivery.core.client.dto.kd100;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.econets.blossom.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * Express Delivery 100 Real-time express tracking Resp DTO
 *
 * See  <a href="https://api.kuaidi100.com/document/5f0ffb5ebc8da837cbd8aefc">Express Delivery 100 Document</a>
 *
 */
@Data
public class Kd100ExpressQueryRespDTO {

    /**
     * Express delivery company code
     */
    @JsonProperty("com")
    private String expressCompanyCode;
    /**
     * Express delivery number
     */
    @JsonProperty("nu")
    private String logisticsNo;
    /**
     * Current status of the express delivery order
     */
    private String state;

    /**
     * Query results
     *
     * Failed return "false"
     */
    private String result;
    /**
     * Error message when query result fails
     */
    private String message;

    /**
     * Trace array
     */
    @JsonProperty("data")
    private List<ExpressTrack> tracks;

    @Data
    public static class ExpressTrack {

        /**
         * Track occurrence time
         */
        @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
        private LocalDateTime time;

        /**
         * Track description
         */
        private String context;

    }

}
