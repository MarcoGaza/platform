package cn.econets.blossom.module.promotion.service.seckill;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.api.seckill.dto.SeckillValidateJoinRespDTO;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.activity.SeckillActivityCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.activity.SeckillActivityPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.activity.SeckillActivityUpdateReqVO;
import cn.econets.blossom.module.promotion.controller.app.seckill.vo.activity.AppSeckillActivityPageReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillProductDO;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Second-sale event Service Interface
 *
 */
public interface SeckillActivityService {

    /**
     * Create a flash sale event
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createSeckillActivity(@Valid SeckillActivityCreateReqVO createReqVO);

    /**
     * Update flash sale activity
     *
     * @param updateReqVO Update information
     */
    void updateSeckillActivity(@Valid SeckillActivityUpdateReqVO updateReqVO);

    /**
     * Update flash sale inventory（Reduce）
     *
     * @param id    Activity number
     * @param skuId sku Number
     * @param count Quantity（Positive number）
     */
    void updateSeckillStockDecr(Long id, Long skuId, Integer count);

    /**
     * Update flash sale inventory（Increase）
     *
     * @param id    Activity number
     * @param skuId sku Number
     * @param count Quantity（Positive number）
     */
    void updateSeckillStockIncr(Long id, Long skuId, Integer count);

    /**
     * Close the flash sale
     *
     * @param id Number
     */
    void closeSeckillActivity(Long id);

    /**
     * Delete flash sale activity
     *
     * @param id Number
     */
    void deleteSeckillActivity(Long id);

    /**
     * Get flash sale
     *
     * @param id Number
     * @return Second-sale event
     */
    SeckillActivityDO getSeckillActivity(Long id);

    /**
     * Get the flash sale activity page
     *
     * @param pageReqVO Paged query
     * @return Second sale activity page
     */
    PageResult<SeckillActivityDO> getSeckillActivityPage(SeckillActivityPageReqVO pageReqVO);

    /**
     * Get the event products by event ID
     *
     * @param activityId Activity number
     * @return Activity Product List
     */
    List<SeckillProductDO> getSeckillProductListByActivityId(Long activityId);

    /**
     * Get the event products by event ID
     *
     * @param activityIds Activity number
     * @return Activity Product List
     */
    List<SeckillProductDO> getSeckillProductListByActivityId(Collection<Long> activityIds);

    /**
     * Get the specified time slot by activity period number status Second-sale event
     *
     * @param configId Time period configuration number
     * @param status   Status
     * @return Second sale activity list
     */
    List<SeckillActivityDO> getSeckillActivityListByConfigIdAndStatus(Long configId, Integer status);

    /**
     * Get flash sales through event periods
     *
     * @param pageReqVO Request
     * @return Second sale activity list
     */
    PageResult<SeckillActivityDO> getSeckillActivityAppPageByConfigId(AppSeckillActivityPageReqVO pageReqVO);

    /**
     * Check whether to participate in flash sale products
     *
     * If verification fails，Throws a business exception
     *
     * @param activityId Activity number
     * @param skuId      SKU Number
     * @param count      Quantity
     * @return Second sale information
     */
    SeckillValidateJoinRespDTO validateJoinSeckill(Long activityId, Long skuId, Integer count);

    /**
     * Get the specified value spu Number of the most recently participated event，Each spuId Only one record is returned
     *
     * @param spuIds   spu Number
     * @param status   Status
     * @param dateTime Date and time
     * @return Second sale activity list
     */
    List<SeckillActivityDO> getSeckillActivityBySpuIdsAndStatusAndDateTimeLt(Collection<Long> spuIds, Integer status, LocalDateTime dateTime);

}
