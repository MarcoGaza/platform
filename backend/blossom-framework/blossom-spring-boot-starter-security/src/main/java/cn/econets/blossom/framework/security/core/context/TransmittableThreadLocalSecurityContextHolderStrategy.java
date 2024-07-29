package cn.econets.blossom.framework.security.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.Assert;

/**
 * Based on TransmittableThreadLocal Achieved Security Context Holder Strategy
 * The purpose is，Avoid @Async When executing asynchronously，Native ThreadLocal The problem of loss
 *
 */
public class TransmittableThreadLocalSecurityContextHolderStrategy implements SecurityContextHolderStrategy {

    /**
     * Use TransmittableThreadLocal As context
     */
    private static final ThreadLocal<SecurityContext> CONTEXT_HOLDER = new TransmittableThreadLocal<>();

    @Override
    public void clearContext() {
        CONTEXT_HOLDER.remove();
    }

    @Override
    public SecurityContext getContext() {
        SecurityContext ctx = CONTEXT_HOLDER.get();
        if (ctx == null) {
            ctx = createEmptyContext();
            CONTEXT_HOLDER.set(ctx);
        }
        return ctx;
    }

    @Override
    public void setContext(SecurityContext context) {
        Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
        CONTEXT_HOLDER.set(context);
    }

    @Override
    public SecurityContext createEmptyContext() {
        return new SecurityContextImpl();
    }

}
