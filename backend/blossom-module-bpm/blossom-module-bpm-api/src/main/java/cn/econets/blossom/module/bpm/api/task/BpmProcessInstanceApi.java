package cn.econets.blossom.module.bpm.api.task;

import cn.econets.blossom.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;

import javax.validation.Valid;

/**
 * Process instance Api Interface
 *
 */
public interface BpmProcessInstanceApi {

    /**
     * Create a process instance（Provided to internal）
     *
     * @param userId User Number
     * @param reqDTO Create information
     * @return Instance number
     */
    String createProcessInstance(Long userId, @Valid BpmProcessInstanceCreateReqDTO reqDTO);

}
