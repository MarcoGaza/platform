package cn.econets.blossom.framework.tenant.core.mq.rocketmq;

import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import org.apache.rocketmq.client.hook.ConsumeMessageContext;
import org.apache.rocketmq.client.hook.ConsumeMessageHook;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.util.List;


/**
 * RocketMQ Multi-tenancy of message queues {@link ConsumeMessageHook} Implementation class
 *
 * Consumer When consuming messages，The message Header Tenant number，Add to {@link TenantContextHolder} Medium，Passed {@link InvocableHandlerMethod} Realization
 *
 */
public class TenantRocketMQConsumeMessageHook implements ConsumeMessageHook {

    @Override
    public String hookName() {
        return getClass().getSimpleName();
    }

    @Override
    public void consumeMessageBefore(ConsumeMessageContext context) {
        // Verification，The message must be a single message，Otherwise the tenant settings may be incorrect
        List<MessageExt> messages = context.getMsgList();
        Assert.isTrue(messages.size() == 1, "Number of messages({})Incorrect", messages.size());
        // Set tenant ID
        String tenantId = messages.get(0).getUserProperty(WebFrameworkUtils.HEADER_TENANT_ID);
        if (StrUtil.isNotEmpty(tenantId)) {
            TenantContextHolder.setTenantId(Long.parseLong(tenantId));
        }
    }

    @Override
    public void consumeMessageAfter(ConsumeMessageContext context) {
        TenantContextHolder.clear();
    }

}
