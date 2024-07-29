package cn.econets.blossom.module.promotion.service.bargain;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.bargain.vo.activity.BargainActivityCreateReqVO;
import cn.econets.blossom.module.promotion.controller.admin.bargain.vo.activity.BargainActivityPageReqVO;
import cn.econets.blossom.module.promotion.controller.admin.bargain.vo.activity.BargainActivityUpdateReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainActivityDO;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Bargaining activity Service Interface
 *
 */
public interface BargainActivityService {

    /**
     * Create a bargaining activity
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createBargainActivity(@Valid BargainActivityCreateReqVO createReqVO);

    /**
     * Update bargaining activity
     *
     * @param updateReqVO Update information
     */
    void updateBargainActivity(@Valid BargainActivityUpdateReqVO updateReqVO);

    /**
     * Update bargaining activity inventory
     *
     * If the update fails（Insufficient inventory），Throws a business exception
     *
     * @param id    Bargaining activity number
     * @param count Purchase quantity
     */
    void updateBargainActivityStock(Long id, Integer count);

    /**
     * Close the bargaining activity
     *
     * @param id Bargaining activity number
     */
    void closeBargainActivityById(Long id);

    /**
     * Delete bargaining activity
     *
     * @param id Number
     */
    void deleteBargainActivity(Long id);

    /**
     * Get bargaining activity
     *
     * @param id Number
     * @return Bargaining activity
     */
    BargainActivityDO getBargainActivity(Long id);

    /**
     * Get the list of bargaining activities
     *
     * @param ids Number array
     * @return Bargaining Activity List
     */
    List<BargainActivityDO> getBargainActivityList(Set<Long> ids);

    /**
     * Check the bargaining activity，Can I participate?（Initiate bargaining、Place an order、Help friends bargain）
     *
     * @param id Number
     * @return Bargaining activity
     */
    BargainActivityDO validateBargainActivityCanJoin(Long id);

    /**
     * Get the bargaining activity page
     *
     * @param pageReqVO Paged query
     * @return Bargaining Activity Page
     */
    PageResult<BargainActivityDO> getBargainActivityPage(BargainActivityPageReqVO pageReqVO);

    /**
     * Get the ongoing active paging data
     *
     * @param pageReqVO Pagination request
     * @return Bargaining Activity Page
     */
    PageResult<BargainActivityDO> getBargainActivityPage(PageParam pageReqVO);

    /**
     * Get the ongoing active paging data
     *
     * @param count Required quantity
     * @return Bargaining Activity Page
     */
    List<BargainActivityDO> getBargainActivityListByCount(Integer count);

    /**
     * Get the specified value spu Number of the most recently participated event，Each spuId Only one record is returned
     *
     * @param spuIds   spu Number
     * @param status   Status
     * @param dateTime Date and time
     * @return Bargaining Activity List
     */
    List<BargainActivityDO> getBargainActivityBySpuIdsAndStatusAndDateTimeLt(Collection<Long> spuIds, Integer status, LocalDateTime dateTime);

}
