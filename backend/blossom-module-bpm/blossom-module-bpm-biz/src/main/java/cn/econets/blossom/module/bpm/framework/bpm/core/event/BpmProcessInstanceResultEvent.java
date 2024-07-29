package cn.econets.blossom.module.bpm.framework.bpm.core.event;

import cn.econets.blossom.module.bpm.dal.dataobject.task.BpmProcessInstanceExtDO;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import javax.validation.constraints.NotNull;

/**
 * The result of the process instance has changed Event
 * Positioning：Due to the additional increase {@link BpmProcessInstanceExtDO#getResult()} Results，So add this event
 *
 */
@SuppressWarnings("ALL")
@Data
public class BpmProcessInstanceResultEvent extends ApplicationEvent {

    /**
     * Process instance number
     */
    @NotNull(message = "The process instance number cannot be empty")
    private String id;
    /**
     * Process instance key
     */
    @NotNull(message = "Process instance key Cannot be empty")
    private String processDefinitionKey;
    /**
     * Results of process instance
     */
    @NotNull(message = "The result of a process instance cannot be empty")
    private Integer result;
    /**
     * Business ID corresponding to the process instance
     * For example，Requesting leave
     */
    private String businessKey;

    public BpmProcessInstanceResultEvent(Object source) {
        super(source);
    }

}
