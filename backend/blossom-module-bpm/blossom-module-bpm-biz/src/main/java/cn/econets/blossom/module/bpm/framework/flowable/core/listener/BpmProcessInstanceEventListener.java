package cn.econets.blossom.module.bpm.framework.flowable.core.listener;

import cn.econets.blossom.module.bpm.dal.dataobject.task.BpmProcessInstanceExtDO;
import cn.econets.blossom.module.bpm.service.task.BpmProcessInstanceService;
import com.google.common.collect.ImmutableSet;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEntityEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.engine.delegate.event.AbstractFlowableEngineEventListener;
import org.flowable.engine.delegate.event.FlowableCancelledEvent;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Monitor {@link ProcessInstance} The beginning and end of，Create and update corresponding {@link BpmProcessInstanceExtDO} Record
 *
 */
@Component
public class BpmProcessInstanceEventListener extends AbstractFlowableEngineEventListener {

    @Resource
    @Lazy
    private BpmProcessInstanceService processInstanceService;

    public static final Set<FlowableEngineEventType> PROCESS_INSTANCE_EVENTS = ImmutableSet.<FlowableEngineEventType>builder()
                     .add(FlowableEngineEventType.PROCESS_CREATED)
                     .add(FlowableEngineEventType.PROCESS_CANCELLED)
                     .add(FlowableEngineEventType.PROCESS_COMPLETED)
                     .build();

    public BpmProcessInstanceEventListener(){
        super(PROCESS_INSTANCE_EVENTS);
    }

    @Override
    protected void processCreated(FlowableEngineEntityEvent event) {
        processInstanceService.createProcessInstanceExt((ProcessInstance)event.getEntity());
    }

    @Override
    protected void processCancelled(FlowableCancelledEvent event) {
        processInstanceService.updateProcessInstanceExtCancel(event);
    }

    @Override
    protected void processCompleted(FlowableEngineEntityEvent event) {
        processInstanceService.updateProcessInstanceExtComplete((ProcessInstance)event.getEntity());
    }
}
