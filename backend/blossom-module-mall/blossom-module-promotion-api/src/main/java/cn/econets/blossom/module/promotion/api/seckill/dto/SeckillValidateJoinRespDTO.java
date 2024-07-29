package cn.econets.blossom.module.promotion.api.seckill.dto;

import lombok.Data;

/**
 * Check to participate in flash sales Response DTO
 */
@Data
public class SeckillValidateJoinRespDTO {

    /**
     * Name of flash sale event
     */
    private String name;
    /**
     * Total purchase limit quantity
     *
     * Purpose：Currently only trade There are specific order data，Need to be handed over trade Price calculation use
     */
    private Integer totalLimitCount;

    /**
     * Second sale amount
     */
    private Integer seckillPrice;

}
