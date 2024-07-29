package cn.econets.blossom.module.promotion.service.combination;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity.CombinationActivityCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity.CombinationActivityPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity.CombinationActivityUpdateReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationProductDO;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Group buying activity Service Interface
 *
 */
public interface CombinationActivityService {

    /**
     * Create a group buying activity
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createCombinationActivity(@Valid CombinationActivityCreateReqVO createReqVO);

    /**
     * Update group buying activities
     *
     * @param updateReqVO Update information
     */
    void updateCombinationActivity(@Valid CombinationActivityUpdateReqVO updateReqVO);

    /**
     * Close group buying activities
     *
     * @param id Group buying activity number
     */
    void closeCombinationActivityById(Long id);

    /**
     * Delete group buying activity
     *
     * @param id Number
     */
    void deleteCombinationActivity(Long id);

    /**
     * Check if group buying activity exists
     *
     * @param id Number
     * @return Group buying activity
     */
    CombinationActivityDO validateCombinationActivityExists(Long id);

    /**
     * Get group buying activity
     *
     * @param id Number
     * @return Group buying activity
     */
    CombinationActivityDO getCombinationActivity(Long id);

    /**
     * Get the group buying activity page
     *
     * @param pageReqVO Paged query
     * @return Group buying activity page
     */
    PageResult<CombinationActivityDO> getCombinationActivityPage(CombinationActivityPageReqVO pageReqVO);

    /**
     * Get the list of group buying products
     *
     * @param activityId Group buying activity id
     * @return List of products for group buying activities
     */
    default List<CombinationProductDO> getCombinationProductsByActivityId(Long activityId) {
        return getCombinationProductListByActivityIds(Collections.singletonList(activityId));
    }

    /**
     * Get the list of group buying products
     *
     * @param activityIds Group buying activity ids
     * @return List of products for group buying activities
     */
    List<CombinationProductDO> getCombinationProductListByActivityIds(Collection<Long> activityIds);

    /**
     * Get the list of group buying activities
     *
     * @param ids Group buying activity ids
     * @return List of group buying activities
     */
    List<CombinationActivityDO> getCombinationActivityListByIds(Collection<Long> ids);

    /**
     * Get the ongoing active paging data
     *
     * @param count Required quantity
     * @return Group buying activity page
     */
    List<CombinationActivityDO> getCombinationActivityListByCount(Integer count);

    /**
     * Get the ongoing active paging data
     *
     * @param pageParam Pagination request
     * @return Group buying activity page
     */
    PageResult<CombinationActivityDO> getCombinationActivityPage(PageParam pageParam);

    /**
     * Get the specified activity、Specify sku Numbered product
     *
     * @param activityId Activity number
     * @param skuId      sku Number
     * @return Activity product information
     */
    CombinationProductDO selectByActivityIdAndSkuId(Long activityId, Long skuId);

    /**
     * Get the specified value spu Number of the most recently participated event，Each spuId Only one record is returned
     *
     * @param spuIds   spu Number
     * @param status   Status
     * @param dateTime Date time
     * @return Group buying activity list
     */
    List<CombinationActivityDO> getCombinationActivityBySpuIdsAndStatusAndDateTimeLt(Collection<Long> spuIds, Integer status, LocalDateTime dateTime);

}
