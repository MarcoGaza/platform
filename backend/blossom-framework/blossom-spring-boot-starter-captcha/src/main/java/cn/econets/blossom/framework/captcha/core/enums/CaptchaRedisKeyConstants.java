package cn.econets.blossom.framework.captcha.core.enums;

/**
 * Verification code Redis Key Enumeration class
 *
 */
public interface CaptchaRedisKeyConstants {

    /**
     * Restriction on verification code requests
     *
     * KEY Format：AJ.CAPTCHA.REQ.LIMIT-%s-%s
     * VALUE Data type：String // For example：Verification failed 5 times，get Interface locked
     * Expiration time：60 seconds
     */
    String AJ_CAPTCHA_REQ_LIMIT = "AJ.CAPTCHA.REQ.LIMIT-%s-%s";

    /**
     * Verification code coordinates
     *
     * KEY Format：RUNNING:CAPTCHA:%s // AbstractCaptchaService.REDIS_CAPTCHA_KEY
     * VALUE Data type：String // PointVO.class {"secretKey":"PP1w2Frr2KEejD2m","x":162,"y":5}
     * Expiration time：120 seconds
     */
    String AJ_CAPTCHA_RUNNING = "RUNNING:CAPTCHA:%s";

}
