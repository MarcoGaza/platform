package cn.econets.blossom.module.bpm.api.listener.dto;

import lombok.Data;

// TODOChanged to support later RPC
/**
 * Results of business process instances Response DTO
 *
 */
@Data
public class BpmResultListenerRespDTO {

    /**
     * Process instance number
     */
    private String id;
    /**
     * Process instance key
     */
    private String processDefinitionKey;
    /**
     * Results of process instance
     */
    private Integer result;
    /**
     * Business ID corresponding to the process instance
     * For example，Requesting leave
     */
    private String businessKey;

}
