package cn.econets.blossom.framework.monitor.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * BizTracerConfiguration Class
 *
 */
@ConfigurationProperties("application.tracer")
@Data
public class TracerProperties {
}
