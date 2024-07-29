package cn.econets.blossom.module.bpm.service.message.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * BPM The sending process instance was passed Request DTO
 */
@Data
public class BpmMessageSendWhenProcessInstanceApproveReqDTO {

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

}
