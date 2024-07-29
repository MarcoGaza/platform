package cn.econets.blossom.module.promotion.api.combination;

import cn.econets.blossom.module.promotion.api.combination.dto.CombinationRecordCreateReqDTO;
import cn.econets.blossom.module.promotion.api.combination.dto.CombinationRecordCreateRespDTO;
import cn.econets.blossom.module.promotion.api.combination.dto.CombinationValidateJoinRespDTO;

import javax.validation.Valid;

/**
 * Group buying record API Interface

 */
public interface CombinationRecordApi {

    /**
     * Check whether the group buying conditions are met
     *
     * @param userId     User Number
     * @param activityId Activity number
     * @param headId     Team Leader Number
     * @param skuId      sku Number
     * @param count      Quantity
     */
    void validateCombinationRecord(Long userId, Long activityId, Long headId, Long skuId, Integer count);

    /**
     * Create a group record
     *
     * @param reqDTO Request DTO
     * @return Group buying information
     */
    CombinationRecordCreateRespDTO createCombinationRecord(@Valid CombinationRecordCreateReqDTO reqDTO);

    /**
     * Check whether the group purchase record is successful
     *
     * @param userId  User Number
     * @param orderId Order Number
     * @return Is the group purchase successful?
     */
    boolean isCombinationRecordSuccess(Long userId, Long orderId);

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
    CombinationValidateJoinRespDTO validateJoinCombination(Long userId, Long activityId, Long headId,
                                                           Long skuId, Integer count);

}
