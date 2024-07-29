package cn.econets.blossom.module.bpm.service.definition;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.common.util.object.PageUtils;
import cn.econets.blossom.framework.common.util.validation.ValidationUtils;
import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.model.*;
import cn.econets.blossom.module.bpm.convert.definition.BpmModelConvert;
import cn.econets.blossom.module.bpm.dal.dataobject.definition.BpmFormDO;
import cn.econets.blossom.module.bpm.enums.definition.BpmModelFormTypeEnum;
import cn.econets.blossom.module.bpm.service.definition.dto.BpmModelMetaInfoRespDTO;
import cn.econets.blossom.module.bpm.service.definition.dto.BpmProcessDefinitionCreateReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.common.engine.impl.util.io.BytesStreamSource;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.Model;
import org.flowable.engine.repository.ModelQuery;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.econets.blossom.module.bpm.enums.ErrorCodeConstants.*;

/**
 * FlowableProcess model implementation
 * Mainly carried out Flowable {@link Model} Maintenance
 *
 */
@Service
@Validated
@Slf4j
public class BpmModelServiceImpl implements BpmModelService {

    @Resource
    private RepositoryService repositoryService;
    @Resource
    private BpmProcessDefinitionService processDefinitionService;
    @Resource
    private BpmFormService bpmFormService;
    @Resource
    private BpmTaskAssignRuleService taskAssignRuleService;

    @Override
    public PageResult<BpmModelPageItemRespVO> getModelPage(BpmModelPageReqVO pageVO) {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        if (StrUtil.isNotBlank(pageVO.getKey())) {
            modelQuery.modelKey(pageVO.getKey());
        }
        if (StrUtil.isNotBlank(pageVO.getName())) {
            modelQuery.modelNameLike("%" + pageVO.getName() + "%"); // Fuzzy matching
        }
        if (StrUtil.isNotBlank(pageVO.getCategory())) {
            modelQuery.modelCategory(pageVO.getCategory());
        }
        // Execute query
        List<Model> models = modelQuery.modelTenantId(TenantContextHolder.getTenantIdStr())
                .orderByCreateTime().desc()
                .listPage(PageUtils.getStart(pageVO), pageVO.getPageSize());

        // Get Form Map
        Set<Long> formIds = CollectionUtils.convertSet(models, model -> {
            BpmModelMetaInfoRespDTO metaInfo = JsonUtils.parseObject(model.getMetaInfo(), BpmModelMetaInfoRespDTO.class);
            return metaInfo != null ? metaInfo.getFormId() : null;
        });
        Map<Long, BpmFormDO> formMap = bpmFormService.getFormMap(formIds);

        // Get Deployment Map
        Set<String> deploymentIds = new HashSet<>();
        models.forEach(model -> CollectionUtils.addIfNotNull(deploymentIds, model.getDeploymentId()));
        Map<String, Deployment> deploymentMap = processDefinitionService.getDeploymentMap(deploymentIds);
        // Get ProcessDefinition Map
        List<ProcessDefinition> processDefinitions = processDefinitionService.getProcessDefinitionListByDeploymentIds(deploymentIds);
        Map<String, ProcessDefinition> processDefinitionMap = convertMap(processDefinitions, ProcessDefinition::getDeploymentId);

        // Joining results
        long modelCount = modelQuery.count();
        return new PageResult<>(BpmModelConvert.INSTANCE.convertList(models, formMap, deploymentMap, processDefinitionMap), modelCount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String createModel(@Valid BpmModelCreateReqVO createReqVO, String bpmnXml) {
        checkKeyNCName(createReqVO.getKey());
        // The verification process identifier already exists
        Model keyModel = getModelByKey(createReqVO.getKey());
        if (keyModel != null) {
            throw exception(MODEL_KEY_EXISTS, createReqVO.getKey());
        }

        // Create process definition
        Model model = repositoryService.newModel();
        BpmModelConvert.INSTANCE.copy(model, createReqVO);
        model.setTenantId(TenantContextHolder.getTenantIdStr());
        // Save process definition
        repositoryService.saveModel(model);
        // Save BPMN XML
        saveModelBpmnXml(model, bpmnXml);
        return model.getId();
    }

    private Model getModelByKey(String key) {
        return repositoryService.createModelQuery().modelKey(key).singleResult();
    }

    @Override
    public BpmModelRespVO getModel(String id) {
        Model model = repositoryService.getModel(id);
        if (model == null) {
            return null;
        }
        BpmModelRespVO modelRespVO = BpmModelConvert.INSTANCE.convert(model);
        // Splicing bpmn XML
        byte[] bpmnBytes = repositoryService.getModelEditorSource(id);
        modelRespVO.setBpmnXml(StrUtil.utf8Str(bpmnBytes));
        return modelRespVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // Because multiple operations are performed，So start the transaction
    public void updateModel(@Valid BpmModelUpdateReqVO updateReqVO) {
        // Verify that the process model exists
        Model model = repositoryService.getModel(updateReqVO.getId());
        if (model == null) {
            throw exception(MODEL_NOT_EXISTS);
        }

        // Modify process definition
        BpmModelConvert.INSTANCE.copy(model, updateReqVO);
        // Update model
        repositoryService.saveModel(model);
        // Update BPMN XML
        saveModelBpmnXml(model, updateReqVO.getBpmnXml());
    }

    @Override
    @Transactional(rollbackFor = Exception.class) // Because multiple operations are performed，So start the transaction
    public void deployModel(String id) {
        // 1.1 Verify that the process model exists
        Model model = repositoryService.getModel(id);
        if (ObjectUtils.isEmpty(model)) {
            throw exception(MODEL_NOT_EXISTS);
        }
        // 1.2 Verification flow chart
        // TODO Verify the validity of the flowchart；For example，Is there a starting element?，Is there an ending element?；
        byte[] bpmnBytes = repositoryService.getModelEditorSource(model.getId());
        if (bpmnBytes == null) {
            throw exception(MODEL_NOT_EXISTS);
        }
        // 1.3 Verification form has been configured
        BpmFormDO form = checkFormConfig(model.getMetaInfo());
        // 1.4 Verify that task allocation rules have been configured
        taskAssignRuleService.checkTaskAssignRuleAllConfig(id);

        // 1.5 Check whether the model has been modified。If not modified，Creation is not allowed
        BpmProcessDefinitionCreateReqDTO definitionCreateReqDTO = BpmModelConvert.INSTANCE.convert2(model, form).setBpmnBytes(bpmnBytes);
        if (processDefinitionService.isProcessDefinitionEquals(definitionCreateReqDTO)) { // The process definition information is equal
            ProcessDefinition oldProcessDefinition = processDefinitionService.getProcessDefinitionByDeploymentId(model.getDeploymentId());
            if (oldProcessDefinition != null && taskAssignRuleService.isTaskAssignRulesEquals(model.getId(), oldProcessDefinition.getId())) {
                throw exception(MODEL_DEPLOY_FAIL_TASK_INFO_EQUALS);
            }
        }

        // 2.1 Create process definition
        String definitionId = processDefinitionService.createProcessDefinition(definitionCreateReqDTO);

        // 2.2 Suspend the old process definition。That is to say，Only the latest deployed process definition，Only then can you initiate a task。
        updateProcessDefinitionSuspended(model.getDeploymentId());

        // 2.3 Update model of deploymentId，Associate
        ProcessDefinition definition = processDefinitionService.getProcessDefinition(definitionId);
        model.setDeploymentId(definition.getDeploymentId());
        repositoryService.saveModel(model);

        // 2.4 Copy task assignment rules
        taskAssignRuleService.copyTaskAssignRules(id, definition.getId());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteModel(String id) {
        // Verify that the process model exists
        Model model = repositoryService.getModel(id);
        if (model == null) {
            throw exception(MODEL_NOT_EXISTS);
        }
        // Execute deletion
        repositoryService.deleteModel(id);
        // Disable process definition
        updateProcessDefinitionSuspended(model.getDeploymentId());
    }

    @Override
    public void updateModelState(String id, Integer state) {
        // Verify that the process model exists
        Model model = repositoryService.getModel(id);
        if (model == null) {
            throw exception(MODEL_NOT_EXISTS);
        }
        // Verify that the process definition exists
        ProcessDefinition definition = processDefinitionService.getProcessDefinitionByDeploymentId(model.getDeploymentId());
        if (definition == null) {
            throw exception(PROCESS_DEFINITION_NOT_EXISTS);
        }

        // Update status
        processDefinitionService.updateProcessDefinitionState(definition.getId(), state);
    }

    @Override
    public BpmnModel getBpmnModel(String id) {
        byte[] bpmnBytes = repositoryService.getModelEditorSource(id);
        if (ArrayUtil.isEmpty(bpmnBytes)) {
            return null;
        }
        BpmnXMLConverter converter = new BpmnXMLConverter();
        return converter.convertToBpmnModel(new BytesStreamSource(bpmnBytes), true, true);
    }

    @Override
    public BpmnModel getBpmnModelByDefinitionId(String processDefinitionId) {
        return repositoryService.getBpmnModel(processDefinitionId);
    }

    private void checkKeyNCName(String key) {
        if (!ValidationUtils.isXmlNCName(key)) {
            throw exception(MODEL_KEY_VALID);
        }
    }

    /**
     * Verification process form has been configured
     *
     * @param metaInfoStr Process Model metaInfo Field
     * @return Process Form
     */
    private BpmFormDO checkFormConfig(String  metaInfoStr) {
        BpmModelMetaInfoRespDTO metaInfo = JsonUtils.parseObject(metaInfoStr, BpmModelMetaInfoRespDTO.class);
        if (metaInfo == null || metaInfo.getFormType() == null) {
            throw exception(MODEL_DEPLOY_FAIL_FORM_NOT_CONFIG);
        }
        // Verify form exists
        if (Objects.equals(metaInfo.getFormType(), BpmModelFormTypeEnum.NORMAL.getType())) {
            BpmFormDO form = bpmFormService.getForm(metaInfo.getFormId());
            if (form == null) {
                throw exception(FORM_NOT_EXISTS);
            }
            return form;
        }
        return null;
    }

    private void saveModelBpmnXml(Model model, String bpmnXml) {
        if (StrUtil.isEmpty(bpmnXml)) {
            return;
        }
        repositoryService.addModelEditorSource(model.getId(), StrUtil.utf8Bytes(bpmnXml));
    }

    /**
     * Hang deploymentId Corresponding process definition。 Here is onedeploymentId Only associated with one process definition
     * @param deploymentId Process ReleaseId.
     */
    private void updateProcessDefinitionSuspended(String deploymentId) {
        if (StrUtil.isEmpty(deploymentId)) {
            return;
        }
        ProcessDefinition oldDefinition = processDefinitionService.getProcessDefinitionByDeploymentId(deploymentId);
        if (oldDefinition == null) {
            return;
        }
        processDefinitionService.updateProcessDefinitionState(oldDefinition.getId(), SuspensionState.SUSPENDED.getStateCode());
    }

}
