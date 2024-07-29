package cn.econets.blossom.module.bpm.framework.bpm.core.event;

import cn.hutool.core.util.StrUtil;
import org.springframework.context.ApplicationListener;

/**
 * {@link BpmProcessInstanceResultEvent} Listener
 *
 */
public abstract class BpmProcessInstanceResultEventListener
        implements ApplicationListener<BpmProcessInstanceResultEvent> {

    @Override
    public final void onApplicationEvent(BpmProcessInstanceResultEvent event) {
        if (!StrUtil.equals(event.getProcessDefinitionKey(), getProcessDefinitionKey())) {
            return;
        }
        onEvent(event);
    }

    /**
     * @return Return the monitored process definition Key
     */
    protected abstract String getProcessDefinitionKey();

    /**
     * Handling events
     *
     * @param event Event
     */
    protected abstract void onEvent(BpmProcessInstanceResultEvent event);

}
