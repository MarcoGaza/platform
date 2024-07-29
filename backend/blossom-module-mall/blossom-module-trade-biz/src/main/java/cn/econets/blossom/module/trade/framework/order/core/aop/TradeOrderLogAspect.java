package cn.econets.blossom.module.trade.framework.order.core.aop;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderLogDO;
import cn.econets.blossom.module.trade.framework.order.core.annotations.TradeOrderLog;
import cn.econets.blossom.module.trade.service.order.TradeOrderLogService;
import cn.econets.blossom.module.trade.service.order.bo.TradeOrderLogCreateReqBO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

import static cn.econets.blossom.framework.common.util.json.JsonUtils.toJsonString;
import static java.util.Collections.emptyMap;

/**
 * Transaction order operation log record AOP Cut surface
 *
 */
@Component
@Aspect
@Slf4j
public class TradeOrderLogAspect {

    /**
     * User Number
     *
     * Current usage scenario：When payment is called back，The user ID needs to be set compulsorily
     */
    private static final ThreadLocal<Long> USER_ID = new ThreadLocal<>();
    /**
     * User Type
     */
    private static final ThreadLocal<Integer> USER_TYPE = new ThreadLocal<>();
    /**
     * Order number
     */
    private static final ThreadLocal<Long> ORDER_ID = new ThreadLocal<>();
    /**
     * Status before operation
     */
    private static final ThreadLocal<Integer> BEFORE_STATUS = new ThreadLocal<>();
    /**
     * Status after operation
     */
    private static final ThreadLocal<Integer> AFTER_STATUS = new ThreadLocal<>();
    /**
     * Extended parameters Map，Used to format operation content
     */
    private static final ThreadLocal<Map<String, Object>> EXTS = new ThreadLocal<>();

    @Resource
    private TradeOrderLogService orderLogService;

    @AfterReturning("@annotation(orderLog)")
    public void doAfterReturning(JoinPoint joinPoint, TradeOrderLog orderLog) {
        try {
            // 1.1 Operation User
            Integer userType = getUserType();
            Long userId = getUserId();
            // 1.2 Order information
            Long orderId = ORDER_ID.get();
            if (orderId == null) { // If not set，Only annotations，Indicates that no log is required
                return;
            }
            Integer beforeStatus = BEFORE_STATUS.get();
            Integer afterStatus = AFTER_STATUS.get();
            Map<String, Object> exts = ObjectUtil.defaultIfNull(EXTS.get(), emptyMap());
            String content = StrUtil.format(orderLog.operateType().getContent(), exts);

            // 2. Record log
            TradeOrderLogCreateReqBO createBO = new TradeOrderLogCreateReqBO()
                    .setUserId(userId).setUserType(userType)
                    .setOrderId(orderId).setBeforeStatus(beforeStatus).setAfterStatus(afterStatus)
                    .setOperateType(orderLog.operateType().getType()).setContent(content);
            orderLogService.createOrderLog(createBO);
        } catch (Exception ex) {
            log.error("[doAfterReturning][orderLog({}) Order log error]", toJsonString(orderLog), ex);
        } finally {
            clear();
        }
    }

    /**
     * Get user type
     *
     * If not，Then the agreement is {@link TradeOrderLogDO#getUserType()} System
     *
     * @return User Type
     */
    private static Integer getUserType() {
        return ObjectUtil.defaultIfNull(WebFrameworkUtils.getLoginUserType(), TradeOrderLogDO.USER_TYPE_SYSTEM);
    }

    /**
     * Get user ID
     *
     * If not，Then the agreement is {@link TradeOrderLogDO#getUserId()} System
     *
     * @return User Type
     */
    private static Long getUserId() {
        return ObjectUtil.defaultIfNull(WebFrameworkUtils.getLoginUserId(), TradeOrderLogDO.USER_ID_SYSTEM);
    }

    public static void setOrderInfo(Long id, Integer beforeStatus, Integer afterStatus, Map<String, Object> exts) {
        ORDER_ID.set(id);
        BEFORE_STATUS.set(beforeStatus);
        AFTER_STATUS.set(afterStatus);
        EXTS.set(exts);
    }

    public static void setUserInfo(Long userId, Integer userType) {
        USER_ID.set(userId);
        USER_TYPE.set(userType);
    }

    private static void clear() {
        USER_ID.remove();
        USER_TYPE.remove();
        ORDER_ID.remove();
        BEFORE_STATUS.remove();
        AFTER_STATUS.remove();
        EXTS.remove();
    }

}
