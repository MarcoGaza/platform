package cn.econets.blossom.module.bpm.framework.flowable.config;

import cn.hutool.core.collection.ListUtil;
import cn.econets.blossom.module.bpm.framework.flowable.core.behavior.BpmActivityBehaviorFactory;
import cn.econets.blossom.module.bpm.service.definition.BpmTaskAssignRuleService;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * BPM Module Flowable Configuration Class
 *
 */
@Configuration(proxyBeanMethods = false)
public class BpmFlowableConfiguration {

    /**
     * BPM Module ProcessEngineConfigurationConfigurer Implementation class：
     *
     * 1. Set various listeners
     * 2. Set custom ActivityBehaviorFactory Realization
     */
    @Bean
    public EngineConfigurationConfigurer<SpringProcessEngineConfiguration> bpmProcessEngineConfigurationConfigurer(
            ObjectProvider<FlowableEventListener> listeners,
            BpmActivityBehaviorFactory bpmActivityBehaviorFactory) {
        return configuration -> {
            // Register listener，For example BpmActivityEventListener
            configuration.setEventListeners(ListUtil.toList(listeners.iterator()));
            // Settings ActivityBehaviorFactory Implementation class，Customization of reviewers for process tasks
            configuration.setActivityBehaviorFactory(bpmActivityBehaviorFactory);
        };
    }

    @Bean
    public BpmActivityBehaviorFactory bpmActivityBehaviorFactory(BpmTaskAssignRuleService taskRuleService) {
        BpmActivityBehaviorFactory bpmActivityBehaviorFactory = new BpmActivityBehaviorFactory();
        bpmActivityBehaviorFactory.setBpmTaskRuleService(taskRuleService);
        return bpmActivityBehaviorFactory;
    }

}
