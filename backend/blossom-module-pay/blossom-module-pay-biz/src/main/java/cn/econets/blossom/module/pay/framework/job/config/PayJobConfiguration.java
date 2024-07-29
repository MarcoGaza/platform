package cn.econets.blossom.module.pay.framework.job.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration(proxyBeanMethods = false)
public class PayJobConfiguration {

    public static final String NOTIFY_THREAD_POOL_TASK_EXECUTOR = "NOTIFY_THREAD_POOL_TASK_EXECUTOR";

    @Bean(NOTIFY_THREAD_POOL_TASK_EXECUTOR)
    public ThreadPoolTaskExecutor notifyThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8); // Set the number of core threads
        executor.setMaxPoolSize(16); // Set the maximum number of threads
        executor.setKeepAliveSeconds(60); // Set idle time
        executor.setQueueCapacity(100); // Set the queue size
        executor.setThreadNamePrefix("notify-task-"); // Configure thread pool prefix
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // Loading
        executor.initialize();
        return executor;
    }

}
