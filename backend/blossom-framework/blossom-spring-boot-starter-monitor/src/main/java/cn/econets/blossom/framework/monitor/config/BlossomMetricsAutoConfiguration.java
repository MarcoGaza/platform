package cn.econets.blossom.framework.monitor.config;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * Metrics Configuration Class
 *
 */
@AutoConfiguration
@ConditionalOnClass({MeterRegistryCustomizer.class})
@ConditionalOnProperty(prefix = "application.metrics", value = "enable", matchIfMissing = true) // Allow use application.metrics.enable=false Disable Metrics
public class BlossomMetricsAutoConfiguration {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(
            @Value("${spring.application.name}") String applicationName) {
        return registry -> registry.config().commonTags("application", applicationName);
    }

}
