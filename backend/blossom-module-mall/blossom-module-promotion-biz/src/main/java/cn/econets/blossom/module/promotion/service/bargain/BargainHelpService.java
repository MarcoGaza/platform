package cn.econets.blossom.module.promotion.service.bargain;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.controller.admin.bargain.vo.help.BargainHelpPageReqVO;
import cn.econets.blossom.module.promotion.controller.app.bargain.vo.help.AppBargainHelpCreateReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainHelpDO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Bargaining assistance Service Interface
 *
 */
public interface BargainHelpService {

    /**
     * Create bargaining assistance（Help people bargain）
     *
     * @param userId User Number
     * @param reqVO Request information
     * @return Bargaining record
     */
    BargainHelpDO createBargainHelp(Long userId, AppBargainHelpCreateReqVO reqVO);

    /**
     * 【Bargaining activity】Number of people who received support Map
     *
     * @param activityIds Activity number
     * @return Number of people who helped Map
     */
    Map<Long, Integer> getBargainHelpUserCountMapByActivity(Collection<Long> activityIds);

    /**
     * 【Bargaining Record】Number of people who received support Map
     *
     * @param recordIds Record number
     * @return Number of people who helped Map
     */
    Map<Long, Integer> getBargainHelpUserCountMapByRecord(Collection<Long> recordIds);

    /**
     * 【Bargaining activity】The number of times users have helped
     *
     * @param activityId Activity number
     * @param userId User Number
     * @return Number of assists
     */
    Long getBargainHelpCountByActivity(Long activityId, Long userId);

    /**
     * Get bargaining assistance page
     *
     * @param pageReqVO Paged query
     * @return Bargaining helps paging
     */
    PageResult<BargainHelpDO> getBargainHelpPage(BargainHelpPageReqVO pageReqVO);

    /**
     * Get the specified bargaining record number，Corresponding bargaining assistance list
     *
     * @param recordId Bargaining record number
     * @return Bargaining assistance list
     */
    List<BargainHelpDO> getBargainHelpListByRecordId(Long recordId);

    /**
     * Get support record
     *
     * @param recordId Bargaining record number
     * @param userId User Number
     * @return Assistance Record
     */
    BargainHelpDO getBargainHelp(Long recordId, Long userId);

}
