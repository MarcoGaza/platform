package cn.econets.blossom.module.bpm.service.definition;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.process.BpmProcessDefinitionListReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.process.BpmProcessDefinitionPageItemRespVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.process.BpmProcessDefinitionPageReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.process.BpmProcessDefinitionRespVO;
import cn.econets.blossom.module.bpm.dal.dataobject.definition.BpmProcessDefinitionExtDO;
import cn.econets.blossom.module.bpm.service.definition.dto.BpmProcessDefinitionCreateReqDTO;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * FlowableProcess definition interface
 *
 * 
 */
public interface BpmProcessDefinitionService {

    /**
     * Get process definition page
     *
     * @param pageReqVO Paged parameter entry
     * @return Process definition Page
     */
    PageResult<BpmProcessDefinitionPageItemRespVO> getProcessDefinitionPage(BpmProcessDefinitionPageReqVO pageReqVO);

    /**
     * Get the process definition list
     *
     * @param listReqVO List parameter
     * @return Process definition list
     */
    List<BpmProcessDefinitionRespVO> getProcessDefinitionList(BpmProcessDefinitionListReqVO listReqVO);

    /**
     * Create process definition
     *
     * @param createReqDTO Create information
     * @return Process number
     */
    String createProcessDefinition(@Valid BpmProcessDefinitionCreateReqDTO createReqDTO);

    /**
     * Update process definition status
     *
     * @param id Process definition number
     * @param state Status
     */
    void updateProcessDefinitionState(String id, Integer state);

    /**
     * Get the corresponding process definition BPMN XML
     *
     * @param id Process definition number
     * @return BPMN XML
     */
    String getProcessDefinitionBpmnXML(String id);

    /**
     * Get the process definition that needs to be created，Is it equal to the currently activated process definition?
     *
     * @param createReqDTO Create information
     * @return Are they equal?
     */
    boolean isProcessDefinitionEquals(@Valid BpmProcessDefinitionCreateReqDTO createReqDTO);

    /**
     * Get the corresponding number BpmProcessDefinitionExtDO
     *
     * @param id Number
     * @return Process definition extension
     */
    BpmProcessDefinitionExtDO getProcessDefinitionExt(String id);

    /**
     * Get the corresponding number ProcessDefinition
     *
     * @param id Number
     * @return Process definition
     */
    ProcessDefinition getProcessDefinition(String id);

    /**
     * Get the corresponding number ProcessDefinition
     *
     * Compared to {@link #getProcessDefinition(String)} Method，category The value is correct
     *
     * @param id Number
     * @return Process definition
     */
    ProcessDefinition getProcessDefinition2(String id);

    /**
     * Get deploymentId Corresponding ProcessDefinition
     *
     * @param deploymentId Deployment number
     * @return Process definition
     */
    ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId);

    /**
     * Get deploymentIds Corresponding ProcessDefinition Array
     *
     * @param deploymentIds Array of deployment numbers
     * @return Array of process definitions
     */
    List<ProcessDefinition> getProcessDefinitionListByDeploymentIds(Set<String> deploymentIds);

    /**
     * Get the activated process definition corresponding to the process definition identifier
     *
     * @param key Process definition identifier
     * @return Process definition
     */
    ProcessDefinition getActiveProcessDefinition(String key);

    /**
     * Get ids Corresponding Deployment Map
     *
     * @param ids Array of deployment numbers
     * @return Process deployment Map
     */
    default Map<String, Deployment> getDeploymentMap(Set<String> ids) {
        return CollectionUtils.convertMap(getDeployments(ids), Deployment::getId);
    }

    /**
     * Get ids Corresponding Deployment Array
     *
     * @param ids Array of deployment numbers
     * @return Array of process deployments
     */
    List<Deployment> getDeployments(Set<String> ids);

    /**
     * Get id Corresponding Deployment
     *
     * @param id Deployment number
     * @return Process deployment
     */
    Deployment getDeployment(String id);

    /**
     * Get Bpmn Model
     *
     * @param processDefinitionId Process definition number
     * @return Bpmn Model
     */
    BpmnModel getBpmnModel(String processDefinitionId);
}
