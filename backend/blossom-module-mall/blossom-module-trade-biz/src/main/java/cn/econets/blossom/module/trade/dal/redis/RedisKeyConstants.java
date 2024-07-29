package cn.econets.blossom.module.trade.dal.redis;

/**
 * Transaction Redis Key Enumeration class
 *
 */
public interface RedisKeyConstants {

    /**
     * Transaction sequence number cache
     *
     * KEY Format：trade_no:{prefix}
     * VALUE Data format：Number auto-increment
     */
    String TRADE_NO = "trade_no:";

    /**
     * Transaction sequence number cache
     *
     * KEY Format：express_track:{code-logisticsNo-receiverMobile}
     * VALUE Data format String, Logistics information collection
     */
    String EXPRESS_TRACK = "express_track";

}
