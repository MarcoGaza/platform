package cn.econets.blossom.framework.tenant.core.mq.kafka;

import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.framework.web.core.util.WebFrameworkUtils;
import cn.hutool.core.util.ReflectUtil;
import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.Headers;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;

import java.util.Map;


/**
 * Kafka Multi-tenancy of message queues {@link ProducerInterceptor} Implementation class
 *
 * 1. Producer When sending a message，Will {@link TenantContextHolder} Tenant Number，Added to the message Header Medium
 * 2. Consumer When consuming messages，The message Header Tenant number，Add to {@link TenantContextHolder} Medium，Passed {@link InvocableHandlerMethod} Realization
 *
 */
public class TenantKafkaProducerInterceptor implements ProducerInterceptor<Object, Object> {

    @Override
    public ProducerRecord<Object, Object> onSend(ProducerRecord<Object, Object> record) {
        Long tenantId = TenantContextHolder.getTenantId();
        if (tenantId != null) {
            Headers headers = (Headers) ReflectUtil.getFieldValue(record, "headers"); // private Properties，No get Method，Intelligent Reflection
            headers.add(WebFrameworkUtils.HEADER_TENANT_ID, tenantId.toString().getBytes());
        }
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
    }

    @Override
    public void close() {
    }

    @Override
    public void configure(Map<String, ?> configs) {
    }

}
