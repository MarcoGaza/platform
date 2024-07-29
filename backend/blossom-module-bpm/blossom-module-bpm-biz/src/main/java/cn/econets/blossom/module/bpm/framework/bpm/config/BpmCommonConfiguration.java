package cn.econets.blossom.module.bpm.framework.bpm.config;

import cn.econets.blossom.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventPublisher;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BPM Universal Configuration Configuration Class，Provided to Activiti Japanese Flowable
 */
@Configuration(proxyBeanMethods = false)
public class BpmCommonConfiguration {

    @Bean
    public BpmProcessInstanceResultEventPublisher processInstanceResultEventPublisher(ApplicationEventPublisher publisher) {
        return new BpmProcessInstanceResultEventPublisher(publisher);
    }

}
