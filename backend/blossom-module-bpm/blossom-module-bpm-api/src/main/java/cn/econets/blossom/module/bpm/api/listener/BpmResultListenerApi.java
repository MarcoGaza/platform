package cn.econets.blossom.module.bpm.api.listener;

import cn.econets.blossom.module.bpm.api.listener.dto.BpmResultListenerRespDTO;

// TODO Changed to support later RPC
/**
 * Listener for changes in the results of a business process instance Api
 *
 */
public interface BpmResultListenerApi {

    /**
     * Monitoring process definition Key
     *
     * @return Return the monitored process definition Key
     */
    String getProcessDefinitionKey();

    /**
     * Handling events
     *
     * @param event Event
     */
    void onEvent(BpmResultListenerRespDTO event);

}
