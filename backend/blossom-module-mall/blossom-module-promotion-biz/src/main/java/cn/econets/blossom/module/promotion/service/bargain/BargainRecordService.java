package cn.econets.blossom.module.promotion.service.bargain;


import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.api.bargain.dto.BargainValidateJoinRespDTO;
import cn.econets.blossom.module.promotion.controller.admin.bargain.vo.recrod.BargainRecordPageReqVO;
import cn.econets.blossom.module.promotion.controller.app.bargain.vo.record.AppBargainRecordCreateReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.bargain.BargainRecordDO;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Bargaining Record service Interface
 *
 */
public interface BargainRecordService {

    /**
     * 【Member】Create bargaining record（Participate in activities）
     *
     * @param userId User Number
     * @param reqVO Create information
     * @return Bargaining record number
     */
    Long createBargainRecord(Long userId, AppBargainRecordCreateReqVO reqVO);

    /**
     * Update the bargaining amount in the bargaining record
     *
     * If the conditions for successful bargaining are met，Then update the bargaining record status to success
     *
     * @param id Bargaining record number
     * @param whereBargainPrice Current bargaining amount
     * @param reducePrice Reduced bargaining amount
     * @param success Has the bargaining been successful?
     * @return Is the update successful?。Attention，If concurrent updates occur，The update will fail
     */
    Boolean updateBargainRecordBargainPrice(Long id, Integer whereBargainPrice,
                                            Integer reducePrice, Boolean success);

    /**
     * 【Before placing an order】Check whether to participate in bargaining activities
     * <p>
     * If verification fails，Throws a business exception
     *
     * @param userId          User Number
     * @param bargainRecordId Bargaining activity number
     * @param skuId           SKU Number
     * @return Bargaining information
     */
    BargainValidateJoinRespDTO validateJoinBargain(Long userId, Long bargainRecordId, Long skuId);

    /**
     * Update the order number of the bargaining record
     *
     * After successfully bargaining，After the user places an order，The order number will be recorded
     *
     * @param id     Bargaining record number
     * @param orderId Order Number
     */
    void updateBargainRecordOrderId(Long id, Long orderId);

    /**
     * Get bargaining record
     *
     * @param id Bargaining record number
     * @return Bargaining Record
     */
    BargainRecordDO getBargainRecord(Long id);

    /**
     * Get the last bargaining record of the user in the current bargaining activity
     *
     * @param userId User Number
     * @param activityId Bargaining record number
     * @return Bargaining Record
     */
    BargainRecordDO getLastBargainRecord(Long userId, Long activityId);

    /**
     * Get the number of bargaining numbers Map
     *
     * @param activityIds Activity number
     * @param status Bargaining record status
     * @return Number of people bargaining Map
     */
    Map<Long, Integer> getBargainRecordUserCountMap(Collection<Long> activityIds, @Nullable Integer status);

    /**
     * Get the number of bargaining numbers
     *
     * @param status Bargaining record status
     * @return Number of people bargaining
     */
    Integer getBargainRecordUserCount(Integer status);

    /**
     * Get the number of bargaining numbers
     *
     * @param activityId Bargaining activity number
     * @param status Bargaining record status
     * @return Number of people bargaining
     */
    Integer getBargainRecordUserCount(Long activityId, Integer status);

    /**
     * 【Administrator】Get bargaining record paging
     *
     * @param pageReqVO Paged query
     * @return Bargaining record paging
     */
    PageResult<BargainRecordDO> getBargainRecordPage(BargainRecordPageReqVO pageReqVO);

    /**
     * 【Member】Get bargaining record paging
     *
     * @param userId User Number
     * @param pageParam Paged query
     * @return Bargaining record paging
     */
    PageResult<BargainRecordDO> getBargainRecordPage(Long userId, PageParam pageParam);

    /**
     * Get the bargaining record list
     *
     * @param status Bargaining record status
     * @param count Number of items
     * @return Bargaining record list
     */
    List<BargainRecordDO> getBargainRecordList(Integer status, Integer count);

}
