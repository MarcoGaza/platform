package cn.econets.blossom.module.bpm.service.message;

import cn.econets.blossom.module.bpm.service.message.dto.BpmMessageSendWhenProcessInstanceApproveReqDTO;
import cn.econets.blossom.module.bpm.service.message.dto.BpmMessageSendWhenProcessInstanceRejectReqDTO;
import cn.econets.blossom.module.bpm.service.message.dto.BpmMessageSendWhenTaskCreatedReqDTO;

import javax.validation.Valid;

/**
 * BPM Message Service Interface
 *
 * TODO Future support for configurable messages；Different processes，In what scenario，What message do you need to send?，What is the content of the message；
 *
 * 
 */
public interface BpmMessageService {

    /**
     * Send a message that the process instance has been passed
     *
     * @param reqDTO Send message
     */
    void sendMessageWhenProcessInstanceApprove(@Valid BpmMessageSendWhenProcessInstanceApproveReqDTO reqDTO);

    /**
     * The message that the process instance was not sent was rejected
     *
     * @param reqDTO Send message
     */
    void sendMessageWhenProcessInstanceReject(@Valid BpmMessageSendWhenProcessInstanceRejectReqDTO reqDTO);

    /**
     * Send a message that the task has been assigned
     *
     * @param reqDTO Send message
     */
    void sendMessageWhenTaskAssigned(@Valid BpmMessageSendWhenTaskCreatedReqDTO reqDTO);

}
