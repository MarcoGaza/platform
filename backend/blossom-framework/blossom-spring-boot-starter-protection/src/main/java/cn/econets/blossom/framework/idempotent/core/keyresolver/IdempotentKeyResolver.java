package cn.econets.blossom.framework.idempotent.core.keyresolver;

import cn.econets.blossom.framework.idempotent.core.annotation.Idempotent;
import org.aspectj.lang.JoinPoint;

/**
 * Idempotence Key Parser interface
 *
 */
public interface IdempotentKeyResolver {

    /**
     * Parse one Key
     *
     * @param idempotent Idempotent Annotation
     * @param joinPoint  AOP Cut surface
     * @return Key
     */
    String resolver(JoinPoint joinPoint, Idempotent idempotent);

}
