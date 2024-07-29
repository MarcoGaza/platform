package cn.econets.blossom.framework.tenant.core.mq.rabbitmq;

import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;


/**
 * RabbitMQ Multi-tenancy of message queues {@link ProducerInterceptor} Implementation class
 *
 * 1. Producer When sending a message，Will {@link TenantContextHolder} Tenant Number，Added to the message Header Medium
 * 2. Consumer Consumption
 */
public class TenantRabbitMQMessagePostProcessor implements MessagePostProcessor {

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId != null) {
            message.getMessageProperties().getHeaders().put(WebFrameworkUtils.HEADER_TENANT_ID, tenantId);
        }
        return message;
    }

}
