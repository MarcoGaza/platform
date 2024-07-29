package cn.econets.blossom.framework.common.util.spring;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.framework.AopProxy;
import org.springframework.aop.support.AopUtils;

/**
 * Spring AOP Tools
 *
 */
public class SpringAopUtils {

    /**
     * Get the target object of the proxy
     *
     * @param proxy Proxy object
     * @return Target object
     */
    public static Object getTarget(Object proxy) throws Exception {
        // Not a proxy object
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;
        }
        // Jdk Proxy
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        }
        // Cglib Proxy
        return getCglibProxyTargetObject(proxy);
    }

    private static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Object dynamicAdvisedInterceptor = BeanUtil.getFieldValue(proxy, "CGLIB$CALLBACK_0");
        AdvisedSupport advisedSupport = (AdvisedSupport) BeanUtil.getFieldValue(dynamicAdvisedInterceptor, "advised");
        return advisedSupport.getTargetSource().getTarget();
    }

    private static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        AopProxy aopProxy = (AopProxy) BeanUtil.getFieldValue(proxy, "h");
        AdvisedSupport advisedSupport = (AdvisedSupport) BeanUtil.getFieldValue(aopProxy, "advised");
        return advisedSupport.getTargetSource().getTarget();
    }

}
