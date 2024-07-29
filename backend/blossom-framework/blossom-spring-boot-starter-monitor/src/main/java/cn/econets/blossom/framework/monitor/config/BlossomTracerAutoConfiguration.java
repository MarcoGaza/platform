package cn.econets.blossom.framework.monitor.config;

import cn.econets.blossom.framework.common.enums.WebFilterOrderEnum;
import cn.econets.blossom.framework.monitor.core.aop.BizTraceAspect;
import cn.econets.blossom.framework.monitor.core.filter.TraceFilter;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Tracer Configuration Class
 *
 *
 */
@AutoConfiguration
@ConditionalOnClass({BizTraceAspect.class})
@EnableConfigurationProperties(TracerProperties.class)
@ConditionalOnProperty(prefix = "application.tracer", value = "enable", matchIfMissing = true)
public class BlossomTracerAutoConfiguration {

    // TODO Important。Currently opentracing Version conflicts exist，Either guarantee skywalking，Either guarantee Alibaba Cloud SMS sdk
//    @Bean
//    public TracerProperties bizTracerProperties() {
//        return new TracerProperties();
//    }
//
//    @Bean
//    public BizTraceAspect bizTracingAop() {
//        return new BizTraceAspect(tracer());
//    }
//
//    @Bean
//    public Tracer tracer() {
//        // Create SkywalkingTracer Object
//        SkywalkingTracer tracer = new SkywalkingTracer();
//        // Set to GlobalTracer Tracker
//        GlobalTracer.register(tracer);
//        return tracer;
//    }

    /**
     * Create TraceFilter Filter，Response header Settings traceId
     */
    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilter() {
        FilterRegistrationBean<TraceFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new TraceFilter());
        registrationBean.setOrder(WebFilterOrderEnum.TRACE_FILTER);
        return registrationBean;
    }

}
