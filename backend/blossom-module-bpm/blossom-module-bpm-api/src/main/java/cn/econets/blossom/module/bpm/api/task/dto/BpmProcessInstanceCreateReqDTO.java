package cn.econets.blossom.module.bpm.api.task.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

/**
 * Creation of process instance Request DTO
 *
 */
@Data
public class BpmProcessInstanceCreateReqDTO {

    /**
     * Process definition identifier
     */
    @NotEmpty(message = "The process definition identifier cannot be empty")
    private String processDefinitionKey;
    /**
     * Variable instance
     */
    private Map<String, Object> variables;

    /**
     * Unique identifier of the business
     *
     * For example，Leave application number。Through it，You can query the corresponding instance
     */
    @NotEmpty(message = "Unique identifier of the business")
    private String businessKey;

    // TODO assignees Plural
    /**
     * Pre-assigned approver
     *
     * key：taskKey Task Code
     * value：Array of approvers
     * For example： { taskKey1 :[1, 2] }，It means taskKey1 This task，Set in advance，By userId for 1,2 Users for approval
     */
    private Map<String, List<Long>> assignee;

}
