package cn.econets.blossom.module.bpm.service.message.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * BPM Sending task is assigned Request DTO
 */
@Data
public class BpmMessageSendWhenTaskCreatedReqDTO {

    /**
     * Process instance number
     */
    @NotEmpty(message = "The process instance number cannot be empty")
    private String processInstanceId;
    /**
     * Name of the process instance
     */
    @NotEmpty(message = "The name of the process instance cannot be empty")
    private String processInstanceName;
    @NotNull(message = "The initiator's user ID")
    private Long startUserId;
    @NotEmpty(message = "Nickname of the initiator")
    private String startUserNickname;

    /**
     * Process task number
     */
    @NotEmpty(message = "The process task number cannot be empty")
    private String taskId;
    /**
     * The name of the process task
     */
    @NotEmpty(message = "The name of the process task cannot be empty")
    private String taskName;

    /**
     * Approver's user ID
     */
    @NotNull(message = "The user ID of the approver cannot be empty")
    private Long assigneeUserId;

}
