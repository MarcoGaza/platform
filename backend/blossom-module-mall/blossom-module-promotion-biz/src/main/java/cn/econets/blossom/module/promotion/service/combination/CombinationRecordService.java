package cn.econets.blossom.module.promotion.service.combination;

import cn.econets.blossom.framework.common.core.KeyValue;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.promotion.api.combination.dto.CombinationRecordCreateReqDTO;
import cn.econets.blossom.module.promotion.api.combination.dto.CombinationValidateJoinRespDTO;
import cn.econets.blossom.module.promotion.controller.admin.combination.vo.recrod.CombinationRecordReqPageVO;
import cn.econets.blossom.module.promotion.controller.app.combination.vo.record.AppCombinationRecordPageReqVO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationProductDO;
import cn.econets.blossom.module.promotion.dal.dataobject.combination.CombinationRecordDO;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Group buying record Service Interface
 *
 */
public interface CombinationRecordService {

    /**
     * 【Before placing an order】Check whether the conditions for group buying activities are met
     *
     * If verification fails，Throws a business exception
     *
     * @param userId     User Number
     * @param activityId Activity number
     * @param headId     Team Leader Number
     * @param skuId      sku Number
     * @param count      Quantity
     * @return Group buying information
     */
    KeyValue<CombinationActivityDO, CombinationProductDO> validateCombinationRecord(Long userId, Long activityId, Long headId,
                                                                                    Long skuId, Integer count);

    /**
     * Create a group purchase record
     *
     * @param reqDTO Create information
     * @return Group Information
     */
    CombinationRecordDO createCombinationRecord(CombinationRecordCreateReqDTO reqDTO);

    /**
     * Get group purchase record
     *
     * @param userId  User Number
     * @param orderId Order Number
     * @return Group buying record
     */
    CombinationRecordDO getCombinationRecord(Long userId, Long orderId);

    /**
     * 【Before placing an order】Check whether the conditions for group buying activities are met
     *
     * If verification fails，Throws a business exception
     *
     * @param userId     User Number
     * @param activityId Activity number
     * @param headId     Team Leader Number
     * @param skuId      sku Number
     * @param count      Quantity
     * @return Group buying information
     */
    CombinationValidateJoinRespDTO validateJoinCombination(Long userId, Long activityId, Long headId, Long skuId, Integer count);

    /**
     * Get the number of group purchase records
     *
     * @param status       Status-Allow to be empty
     * @param virtualGroup Whether to form a virtual group-Allow to be empty
     * @param headId       Team Leader Number，Allow empty space。Purpose headId Set to {@link CombinationRecordDO#HEAD_ID_GROUP} Time，Can be set
     * @return Number of records
     */
    Long getCombinationRecordCount(@Nullable Integer status, @Nullable Boolean virtualGroup, Long headId);

    /**
     * Query user group purchase records（DISTINCT Deduplication），That is to say, when checking the member table to see how many users have participated in group buying activities, each person is only counted once
     *
     * @return Number of users who have participated in group buying
     */
    Long getCombinationUserCount();

    /**
     * Get the nearest count Group buying records
     *
     * @param count Limit quantity
     * @return Group buying record list
     */
    List<CombinationRecordDO> getLatestCombinationRecordList(int count);

    /**
     * Get the latest n Group buying records（Initiated by the team leader）
     *
     * @param activityId Group buying activity number
     * @param status     Status
     * @param count      Quantity
     * @return Group buying record list
     */
    List<CombinationRecordDO> getHeadCombinationRecordList(Long activityId, Integer status, Integer count);

    /**
     * Get the group purchase record of the specified number
     *
     * @param id Group buying record number
     * @return Group buying record
     */
    CombinationRecordDO getCombinationRecordById(Long id);

    /**
     * Get the group record of the specified group leader number
     *
     * @param headId Team Leader Number
     * @return Group buying record list
     */
    List<CombinationRecordDO> getCombinationRecordListByHeadId(Long headId);

    /**
     * Get group purchase record paging data
     *
     * @param pageVO Pagination request
     * @return Group buying record paging data
     */
    PageResult<CombinationRecordDO> getCombinationRecordPage(CombinationRecordReqPageVO pageVO);

    /**
     * 【Group buying activity】Get the number of group purchase records Map
     *
     * @param activityIds Activity record number array
     * @param status      Group buying status，Allow empty space
     * @param headId      Team Leader Number，Allow empty space。Purpose headId Set to {@link CombinationRecordDO#HEAD_ID_GROUP} Time，Can be set
     * @return Number of group purchase records Map
     */
    Map<Long, Integer> getCombinationRecordCountMapByActivity(Collection<Long> activityIds,
                                                              @Nullable Integer status,
                                                              @Nullable Long headId);

    /**
     * Get group purchase records
     *
     * @param userId User Number
     * @param id     Group buying record number
     * @return Group buying record
     */
    CombinationRecordDO getCombinationRecordByIdAndUser(Long userId, Long id);

    /**
     * Cancel group buy
     *
     * @param userId User Number
     * @param id     Group buying record number
     * @param headId Team Leader Number
     */
    void cancelCombinationRecord(Long userId, Long id, Long headId);

    /**
     * Handle expired group purchases
     *
     * @return key Number of expired group purchases, value Number of virtual groups
     */
    KeyValue<Integer, Integer> expireCombinationRecord();

    /**
     * Get group purchase record paging data
     *
     * @param userId User Number
     * @param pageReqVO Pagination request
     * @return Group buying record paging data
     */
    PageResult<CombinationRecordDO> getCombinationRecordPage(Long userId, AppCombinationRecordPageReqVO pageReqVO);

}
