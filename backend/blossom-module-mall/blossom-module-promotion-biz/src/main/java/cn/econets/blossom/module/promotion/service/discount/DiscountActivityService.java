package cn.econets.blossom.module.promotion.service.discount;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.discount.vo.DiscountActivityCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.discount.vo.DiscountActivityPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.discount.vo.DiscountActivityUpdateReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.discount.DiscountActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.discount.DiscountProductDO;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Limited time discount Service Interface
 *
 */
public interface DiscountActivityService {

    /**
     * Based on the specified SKU Number array，Get matching limited-time discount items
     *
     * Attention，Matching conditions，Only the date matches，and is in the open state
     *
     * @param skuIds SKU Number array
     * @return Matching limited-time discount items
     */
    List<DiscountProductDO> getMatchDiscountProductList(Collection<Long> skuIds);

    /**
     * Create a limited-time discount event
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createDiscountActivity(@Valid DiscountActivityCreateReqVO createReqVO);

    /**
     * Update limited time discount event
     *
     * @param updateReqVO Update information
     */
    void updateDiscountActivity(@Valid DiscountActivityUpdateReqVO updateReqVO);

    /**
     * Close the limited time discount event
     *
     * @param id Number
     */
    void closeDiscountActivity(Long id);

    /**
     * Delete limited-time discount event
     *
     * @param id Number
     */
    void deleteDiscountActivity(Long id);

    /**
     * Get a limited time discount
     *
     * @param id Number
     * @return Limited time discount event
     */
    DiscountActivityDO getDiscountActivity(Long id);

    /**
     * Get the limited-time discount event page
     *
     * @param pageReqVO Paged query
     * @return Limited time discount event page
     */
    PageResult<DiscountActivityDO> getDiscountActivityPage(DiscountActivityPageReqVO pageReqVO);

    /**
     * Get the activity number，Corresponding product list
     *
     * @param activityId Activity number
     * @return Activity product list
     */
    List<DiscountProductDO> getDiscountProductsByActivityId(Long activityId);

    /**
     * Get the activity number，Corresponding product list
     *
     * @param activityIds Activity number
     * @return Activity product list
     */
    List<DiscountProductDO> getDiscountProductsByActivityId(Collection<Long> activityIds);

    /**
     * Get the specified value spu Number of the most recently participated event，Each spuId Only one record is returned
     *
     * @param spuIds   spu Number
     * @param status   Status
     * @param dateTime Current date and time
     * @return Discount activity list
     */
    List<DiscountActivityDO> getDiscountActivityBySpuIdsAndStatusAndDateTimeLt(
            Collection<Long> spuIds, Integer status, LocalDateTime dateTime);

}
