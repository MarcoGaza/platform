package cn.econets.blossom.framework.tenant.core.mq.rocketmq;

import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import org.apache.rocketmq.client.hook.SendMessageContext;
import org.apache.rocketmq.client.hook.SendMessageHook;

/**
 * RocketMQ Multi-tenancy of message queues {@link SendMessageHook} Implementation class
 *
 * Producer When sending a message，Will {@link TenantContextHolder} Tenant Number，Added to message Header Medium
 *
 *
 */
public class TenantRocketMQSendMessageHook implements SendMessageHook {

    @Override
    public String hookName() {
        return getClass().getSimpleName();
    }

    @Override
    public void sendMessageBefore(SendMessageContext sendMessageContext) {
        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId == null) {
            return;
        }
        sendMessageContext.getMessage().putUserProperty(WebFrameworkUtils.HEADER_TENANT_ID, tenantId.toString());
    }

    @Override
    public void sendMessageAfter(SendMessageContext sendMessageContext) {
    }

}
