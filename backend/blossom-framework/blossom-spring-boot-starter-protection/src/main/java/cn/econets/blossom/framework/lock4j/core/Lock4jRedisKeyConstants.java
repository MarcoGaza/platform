package cn.econets.blossom.framework.lock4j.core;

/**
 * Lock4j Redis Key Enumeration class
 *
 */
public interface Lock4jRedisKeyConstants {

    /**
     * Distributed lock
     *
     * KEY Format：lock4j:%s // Parameters from DefaultLockKeyBuilder Class
     * VALUE Data format：HASH // RLock.class：Redisson of Lock Lock，Use Hash Data structure
     * Expiration time：Not fixed
     */
    String LOCK4J = "lock4j:%s";

}
