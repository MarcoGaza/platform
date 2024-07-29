package cn.econets.blossom.framework.idempotent.core.keyresolver.impl;

import cn.econets.blossom.framework.idempotent.core.annotation.Idempotent;
import cn.econets.blossom.framework.idempotent.core.keyresolver.IdempotentKeyResolver;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import org.aspectj.lang.JoinPoint;

/**
 * Default idempotence Key Parser，Usage method name + Method parameters，Assemble into one Key
 *
 * In order to avoid Key Too long，Use MD5 Proceed“Compression”
 *
 */
public class DefaultIdempotentKeyResolver implements IdempotentKeyResolver {

    @Override
    public String resolver(JoinPoint joinPoint, Idempotent idempotent) {
        String methodName = joinPoint.getSignature().toString();
        String argsStr = StrUtil.join(",", joinPoint.getArgs());
        return SecureUtil.md5(methodName + argsStr);
    }

}
