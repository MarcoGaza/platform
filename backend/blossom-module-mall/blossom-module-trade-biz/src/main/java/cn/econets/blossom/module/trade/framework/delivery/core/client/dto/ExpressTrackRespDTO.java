package cn.econets.blossom.module.trade.framework.delivery.core.client.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Express tracking track Resp DTO
 *
 */
@Data
public class ExpressTrackRespDTO {

    /**
     * Occurrence time
     */
    private LocalDateTime time;

    /**
     * Express Delivery Status
     */
    private String content;

}
