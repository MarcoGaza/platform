package cn.econets.blossom.module.promotion.api.seckill;

import cn.econets.blossom.module.promotion.api.seckill.dto.SeckillValidateJoinRespDTO;
import cn.econets.blossom.module.promotion.service.seckill.SeckillActivityService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * Second-sale activity interface Api Interface implementation class
 *
 */
@Service
@Validated
public class SeckillActivityApiImpl implements SeckillActivityApi {

    @Resource
    private SeckillActivityService activityService;

    @Override
    public void updateSeckillStockDecr(Long id, Long skuId, Integer count) {
        activityService.updateSeckillStockDecr(id, skuId, count);
    }

    @Override
    public void updateSeckillStockIncr(Long id, Long skuId, Integer count) {
        activityService.updateSeckillStockIncr(id, skuId, count);
    }

    @Override
    public SeckillValidateJoinRespDTO validateJoinSeckill(Long activityId, Long skuId, Integer count) {
        return activityService.validateJoinSeckill(activityId, skuId, count);
    }

}
