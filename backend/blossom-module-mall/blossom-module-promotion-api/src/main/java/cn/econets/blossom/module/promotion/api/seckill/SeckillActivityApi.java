package cn.econets.blossom.module.promotion.api.seckill;

import cn.econets.blossom.module.promotion.api.seckill.dto.SeckillValidateJoinRespDTO;

/**
 * Second-sale event API Interface
 *
 */
public interface SeckillActivityApi {

    /**
     * Update flash sale inventory（Reduce）
     *
     * @param id    Activity number
     * @param skuId sku Number
     * @param count Quantity(Positive number)
     */
    void updateSeckillStockDecr(Long id, Long skuId, Integer count);

    /**
     * Update flash sale inventory（Increase）
     *
     * @param id    Activity number
     * @param skuId sku Number
     * @param count Quantity(Positive number)
     */
    void updateSeckillStockIncr(Long id, Long skuId, Integer count);

    /**
     * 【Before placing an order】Check whether to participate in the flash sale event
     *
     * If verification fails，Throws a business exception
     *
     * @param activityId Activity number
     * @param skuId      SKU Number
     * @param count      Quantity
     * @return Second sale information
     */
    SeckillValidateJoinRespDTO validateJoinSeckill(Long activityId, Long skuId, Integer count);

}
