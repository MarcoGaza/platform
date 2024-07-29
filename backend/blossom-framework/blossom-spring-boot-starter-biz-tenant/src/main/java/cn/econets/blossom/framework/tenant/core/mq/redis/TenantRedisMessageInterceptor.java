package cn.econets.blossom.framework.tenant.core.mq.redis;

import cn.econets.blossom.framework.mq.redis.core.interceptor.RedisMessageInterceptor;
import cn.econets.blossom.framework.mq.redis.core.message.AbstractRedisMessage;
import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import cn.hutool.core.util.StrUtil;

/**
 * Multi-tenancy {@link AbstractRedisMessage} Interceptor
 *
 * 1. Producer When sending a message，Will {@link TenantContextHolder} Tenant Number，Added to the message Header Medium
 * 2. Consumer When consuming messages，The message Header Tenant number，Add to {@link TenantContextHolder} Medium
 *
 */
public class TenantRedisMessageInterceptor implements RedisMessageInterceptor {

    @Override
    public void sendMessageBefore(AbstractRedisMessage message) {
        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId != null) {
            message.addHeader(WebFrameworkUtils.HEADER_TENANT_ID, tenantId.toString());
        }
    }

    @Override
    public void consumeMessageBefore(AbstractRedisMessage message) {
        String tenantIdStr = message.getHeader(WebFrameworkUtils.HEADER_TENANT_ID);
        if (StrUtil.isNotEmpty(tenantIdStr)) {
            TenantContextHolder.setTenantId(Long.valueOf(tenantIdStr));
        }
    }

    @Override
    public void consumeMessageAfter(AbstractRedisMessage message) {
        // Attention，Consumer It is a logical entrance，So the situation where the tenant number already exists in the original context is not considered
        TenantContextHolder.clear();
    }

}
