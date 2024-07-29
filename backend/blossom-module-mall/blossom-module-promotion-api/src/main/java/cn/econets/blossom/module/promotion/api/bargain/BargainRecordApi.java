package cn.econets.blossom.module.promotion.api.bargain;

import cn.econets.blossom.module.promotion.api.bargain.dto.BargainValidateJoinRespDTO;

/**
 * Bargaining Record API Interface

 */
public interface BargainRecordApi {

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

}
