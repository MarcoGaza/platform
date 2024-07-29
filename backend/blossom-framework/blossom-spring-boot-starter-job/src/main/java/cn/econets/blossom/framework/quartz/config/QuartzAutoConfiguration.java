package cn.econets.blossom.framework.quartz.config;

import cn.econets.blossom.framework.quartz.core.scheduler.SchedulerManager;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Optional;

/**
 * Scheduled tasks Configuration
 */
@AutoConfiguration
@EnableScheduling // Open Spring Built-in scheduled tasks
@Slf4j
public class QuartzAutoConfiguration {

    @Bean
    public SchedulerManager schedulerManager(Optional<Scheduler> scheduler) {
        if (!scheduler.isPresent()) {
            log.info("[Scheduled tasks - Disabled]");
            return new SchedulerManager(null);
        }
        return new SchedulerManager(scheduler.get());
    }

}
