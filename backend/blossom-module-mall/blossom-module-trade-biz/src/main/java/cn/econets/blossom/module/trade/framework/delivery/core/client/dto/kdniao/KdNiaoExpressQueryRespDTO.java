package cn.econets.blossom.module.trade.framework.delivery.core.client.dto.kdniao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.econets.blossom.framework.common.util.date.DateUtils.TIME_ZONE_DEFAULT;

/**
 * ExpressBird Express Query Resp DTO
 *
 * See <a href="https://www.yuque.com/kdnjishuzhichi/dfcrg1/wugo6k">Expressbird interface documentation</a>
 *
 */
@Data
public class KdNiaoExpressQueryRespDTO {

    /**
     * Express delivery company code
     */
    @JsonProperty("ShipperCode")
    private String shipperCode;

    /**
     * Express delivery number
     */
    @JsonProperty("LogisticCode")
    private String logisticsNo;

    /**
     * Order number
     */
    @JsonProperty("OrderCode")
    private String orderNo;

    /**
     * User ID
     */
    @JsonProperty("EBusinessID")
    private String businessId;

    /**
     * Normal logistics status
     *
     * 0 - No track information
     * 1 - Has been collected
     * 2 - On the way
     * 3 - Sign for
     * 4 - Problem item
     * 5 - Forward
     * 6 - Customs clearance
     */
    @JsonProperty("State")
    private String state;

    /**
     * Success or failure
     */
    @JsonProperty("Success")
    private Boolean success;
    /**
     * Reason for failure
     */
    @JsonProperty("Reason")
    private String reason;

    /**
     * Trace array
     */
    @JsonProperty("Traces")
    private List<ExpressTrack> tracks;

    @Data
    public static class ExpressTrack {

        /**
         * Occurrence time
         */
        @JsonProperty("AcceptTime")
        @JsonFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = TIME_ZONE_DEFAULT)
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        private LocalDateTime acceptTime;

        /**
         * Track description
         */
        @JsonProperty("AcceptStation")
        private String acceptStation;

    }

}
