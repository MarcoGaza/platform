package cn.econets.blossom.framework.quartz.config;

import com.alibaba.ttl.TtlRunnable;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Asynchronous task Configuration
 */
@AutoConfiguration
@EnableAsync
public class AsyncAutoConfiguration {

    @Bean
    public BeanPostProcessor threadPoolTaskExecutorBeanPostProcessor() {
        return new BeanPostProcessor() {

            @Override
            public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
                if (!(bean instanceof ThreadPoolTaskExecutor)) {
                    return bean;
                }
                // Modify submitted tasks，Access TransmittableThreadLocal
                ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) bean;
                executor.setTaskDecorator(TtlRunnable::get);
                return executor;
            }

        };
    }

}
