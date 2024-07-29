package cn.econets.blossom.framework.tenant.core.mq.kafka;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * Multi-tenant Kafka of {@link EnvironmentPostProcessor} Implementation class
 *
 * Kafka Producer When sending a message，Increase {@link TenantKafkaProducerInterceptor} Interceptor
 *
 */
@Slf4j
public class TenantKafkaEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String PROPERTY_KEY_INTERCEPTOR_CLASSES = "spring.kafka.producer.properties.interceptor.classes";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // Add TenantKafkaProducerInterceptor Interceptor
        try {
            String value = environment.getProperty(PROPERTY_KEY_INTERCEPTOR_CLASSES);
            if (StrUtil.isEmpty(value)) {
                value = TenantKafkaProducerInterceptor.class.getName();
            } else {
                value += "," + TenantKafkaProducerInterceptor.class.getName();
            }
            environment.getSystemProperties().put(PROPERTY_KEY_INTERCEPTOR_CLASSES, value);
        } catch (NoClassDefFoundError ignore) {
            // If triggered NoClassDefFoundError Abnormal，Description TenantKafkaProducerInterceptor Class does not exist，Not introduced kafka-spring Dependency
        }
    }

}
