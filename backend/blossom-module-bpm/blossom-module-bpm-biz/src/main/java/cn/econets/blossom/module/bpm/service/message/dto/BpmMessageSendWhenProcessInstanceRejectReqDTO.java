package cn.econets.blossom.module.bpm.service.message.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * BPM The sending process instance was not accepted Request DTO
 */
@Data
public class BpmMessageSendWhenProcessInstanceRejectReqDTO {

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

    /**
     * Reason for failure
     */
    @NotEmpty(message = "Reason for failure cannot be empty")
    private String reason;

}
