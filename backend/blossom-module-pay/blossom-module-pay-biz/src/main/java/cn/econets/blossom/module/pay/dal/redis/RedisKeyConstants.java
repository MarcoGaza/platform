package cn.econets.blossom.module.pay.dal.redis;

/**
 * Payment Redis Key Enumeration class
 *
 *
 */
public interface RedisKeyConstants {

    /**
     * Distributed lock of notification task
     *
     * KEY Format：pay_notify:lock:%d // Parameters from DefaultLockKeyBuilder Class
     * VALUE Data format：HASH // RLock.class：Redisson of Lock Lock，Use Hash Data structure
     * Expiration time：Not fixed
     */
    String PAY_NOTIFY_LOCK = "pay_notify:lock:%d";

    /**
     * Payment serial number cache
     *
     * KEY Format：pay_no:{prefix}
     * VALUE Data format：Number auto-increment
     */
    String PAY_NO = "pay_no:";

}
