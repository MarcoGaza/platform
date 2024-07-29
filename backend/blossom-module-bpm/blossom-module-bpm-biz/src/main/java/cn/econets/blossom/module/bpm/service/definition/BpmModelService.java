package cn.econets.blossom.module.bpm.service.definition;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.model.*;
import org.flowable.bpmn.model.BpmnModel;

import javax.validation.Valid;

/**
 * FlowableProcess model interface
 *
 */
public interface BpmModelService {

    /**
     * Get process model paging
     *
     * @param pageVO Paged query
     * @return Process model paging
     */
    PageResult<BpmModelPageItemRespVO> getModelPage(BpmModelPageReqVO pageVO);

    /**
     * Create process model
     *
     * @param modelVO Create information
     * @param bpmnXml BPMN XML
     * @return The number of the created process model
     */
    String createModel(@Valid BpmModelCreateReqVO modelVO, String bpmnXml);

    /**
     * Get process module
     *
     * @param id Number
     * @return Process Model
     */
    BpmModelRespVO getModel(String id);

    /**
     * Modify process model
     *
     * @param updateReqVO Update information
     */
    void updateModel(@Valid BpmModelUpdateReqVO updateReqVO);

    /**
     * Process model，Deploy as a process definition
     *
     * @param id Number
     */
    void deployModel(String id);

    /**
     * Delete model
     *
     * @param id Number
     */
    void deleteModel(String id);

    /**
     * Modify the state of the model，The actual updated state of the deployed process definition
     *
     * @param id Number
     * @param state Status
     */
    void updateModelState(String id, Integer state);

    /**
     * Get the corresponding process model number BPMN Model
     *
     * @param id Process model number
     * @return BPMN Model
     */
    BpmnModel getBpmnModel(String id);

    /**
     * Get the corresponding process definition number BPMN Model
     *
     * @param processDefinitionId Process definition number
     * @return BPMN Model
     */
    BpmnModel getBpmnModelByDefinitionId(String processDefinitionId);

}
