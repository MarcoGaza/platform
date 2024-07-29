package cn.econets.blossom.module.bpm.service.task;

import cn.econets.blossom.module.bpm.controller.admin.task.vo.activity.BpmActivityRespVO;
import org.flowable.engine.history.HistoricActivityInstance;

import java.util.List;

/**
 * BPM Activity Instance Service Interface
 *
 * 
 */
public interface BpmActivityService {

    /**
     * Get the list of active instances of the specified process instance
     *
     * @param processInstanceId Process instance number
     * @return Activity instance list
     */
    List<BpmActivityRespVO> getActivityListByProcessInstanceId(String processInstanceId);

    /**
     * Get the activity instance corresponding to the execution number
     *
     * @param executionId Execution number
     * @return Activity Instance
     */
    List<HistoricActivityInstance> getHistoricActivityListByExecutionId(String executionId);

}
