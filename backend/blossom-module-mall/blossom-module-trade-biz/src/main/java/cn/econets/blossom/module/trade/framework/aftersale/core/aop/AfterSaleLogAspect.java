package cn.econets.blossom.module.trade.framework.aftersale.core.aop;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderLogDO;
import cn.econets.blossom.module.trade.framework.aftersale.core.annotations.AfterSaleLog;
import cn.econets.blossom.module.trade.service.aftersale.AfterSaleLogService;
import cn.econets.blossom.module.trade.service.aftersale.bo.AfterSaleLogCreateReqBO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import javax.annotation.Resource;
import java.util.Map;

import static cn.econets.blossom.framework.common.util.json.JsonUtils.toJsonString;
import static java.util.Collections.emptyMap;

/**
 * After-sales order operation records AOP Cut surface
 *
 */
@Slf4j
@Aspect
public class AfterSaleLogAspect {

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
     * Order Number
     */
    private static final ThreadLocal<Long> AFTER_SALE_ID = new ThreadLocal<>();
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
    private AfterSaleLogService afterSaleLogService;

    @AfterReturning(pointcut = "@annotation(afterSaleLog)")
    public void doAfterReturning(JoinPoint joinPoint, AfterSaleLog afterSaleLog) {
        try {
            // 1.1 Operation User
            Integer userType = getUserType();
            Long userId = getUserId();
            // 1.2 After-sales information
            Long afterSaleId = AFTER_SALE_ID.get();
            if (afterSaleId == null) { // If not set，Annotations only，Indicates that no log is required
                return;
            }
            Integer beforeStatus = BEFORE_STATUS.get();
            Integer afterStatus = AFTER_STATUS.get();
            Map<String, Object> exts = ObjectUtil.defaultIfNull(EXTS.get(), emptyMap());
            String content = StrUtil.format(afterSaleLog.operateType().getContent(), exts);

            // 2. Record log
            AfterSaleLogCreateReqBO createBO = new AfterSaleLogCreateReqBO()
                    .setUserId(userId).setUserType(userType)
                    .setAfterSaleId(afterSaleId).setBeforeStatus(beforeStatus).setAfterStatus(afterStatus)
                    .setOperateType(afterSaleLog.operateType().getType()).setContent(content);
            afterSaleLogService.createAfterSaleLog(createBO);
        } catch (Exception exception) {
            log.error("[doAfterReturning][afterSaleLog({}) Logging error]", toJsonString(afterSaleLog), exception);
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

    public static void setAfterSale(Long id, Integer beforeStatus, Integer afterStatus, Map<String, Object> exts) {
        AFTER_SALE_ID.set(id);
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
        AFTER_SALE_ID.remove();
        BEFORE_STATUS.remove();
        AFTER_STATUS.remove();
        EXTS.remove();
    }

}
