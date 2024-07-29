package cn.econets.blossom.module.bpm.service.definition;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.PageUtils;
import cn.econets.blossom.framework.flowable.core.util.BpmnModelUtils;
import cn.econets.blossom.framework.tenant.core.context.TenantContextHolder;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.process.BpmProcessDefinitionListReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.process.BpmProcessDefinitionPageItemRespVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.process.BpmProcessDefinitionPageReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.process.BpmProcessDefinitionRespVO;
import cn.econets.blossom.module.bpm.convert.definition.BpmProcessDefinitionConvert;
import cn.econets.blossom.module.bpm.dal.dataobject.definition.BpmFormDO;
import cn.econets.blossom.module.bpm.dal.dataobject.definition.BpmProcessDefinitionExtDO;
import cn.econets.blossom.module.bpm.dal.mysql.definition.BpmProcessDefinitionExtMapper;
import cn.econets.blossom.module.bpm.service.definition.dto.BpmProcessDefinitionCreateReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.common.engine.impl.util.io.BytesStreamSource;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.module.bpm.enums.ErrorCodeConstants.PROCESS_DEFINITION_KEY_NOT_MATCH;
import static cn.econets.blossom.module.bpm.enums.ErrorCodeConstants.PROCESS_DEFINITION_NAME_NOT_MATCH;
import static java.util.Collections.emptyList;

/**
 * Process definition implementation
 * Mainly carried out Flowable {@link ProcessDefinition} Japanese {@link Deployment} Maintenance
 *
 * 
 */
@Service
@Validated
@Slf4j
public class BpmProcessDefinitionServiceImpl implements BpmProcessDefinitionService {

    private static final String BPMN_FILE_SUFFIX = ".bpmn";

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private BpmProcessDefinitionExtMapper processDefinitionMapper;

    @Resource
    private BpmFormService formService;

    @Override
    public ProcessDefinition getProcessDefinition(String id) {
        return repositoryService.getProcessDefinition(id);
    }

    @Override
    public ProcessDefinition getProcessDefinition2(String id) {
        return repositoryService.createProcessDefinitionQuery().processDefinitionId(id).singleResult();
    }

    @Override
    public ProcessDefinition getProcessDefinitionByDeploymentId(String deploymentId) {
        if (StrUtil.isEmpty(deploymentId)) {
            return null;
        }
        return repositoryService.createProcessDefinitionQuery().deploymentId(deploymentId).singleResult();
    }

    @Override
    public List<ProcessDefinition> getProcessDefinitionListByDeploymentIds(Set<String> deploymentIds) {
        if (CollUtil.isEmpty(deploymentIds)) {
            return emptyList();
        }
        return repositoryService.createProcessDefinitionQuery().deploymentIds(deploymentIds).list();
    }

    @Override
    public ProcessDefinition getActiveProcessDefinition(String key) {
        return repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).active().singleResult();
    }

    @Override
    public List<Deployment> getDeployments(Set<String> ids) {
        if (CollUtil.isEmpty(ids)) {
            return emptyList();
        }
        List<Deployment> list = new ArrayList<>(ids.size());
        for (String id : ids) {
            addIfNotNull(list, getDeployment(id));
        }
        return list;
    }

    @Override
    public Deployment getDeployment(String id) {
        if (StrUtil.isEmpty(id)) {
            return null;
        }
        return repositoryService.createDeploymentQuery().deploymentId(id).singleResult();
    }

    @Override
    public BpmnModel getBpmnModel(String processDefinitionId) {
        return repositoryService.getBpmnModel(processDefinitionId);
    }

    @Override
    public String createProcessDefinition(@Valid BpmProcessDefinitionCreateReqDTO createReqDTO) {
        // Create Deployment Deployment
        Deployment deploy = repositoryService.createDeployment()
                .key(createReqDTO.getKey()).name(createReqDTO.getName()).category(createReqDTO.getCategory())
                .addBytes(createReqDTO.getKey() + BPMN_FILE_SUFFIX, createReqDTO.getBpmnBytes())
                .tenantId(TenantContextHolder.getTenantIdStr())
                .deploy();

        // Settings ProcessDefinition of category Category
        ProcessDefinition definition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId()).singleResult();
        repositoryService.setProcessDefinitionCategory(definition.getId(), createReqDTO.getCategory());
        // Attention 1，ProcessDefinition of key Japanese name It is through BPMN In <bpmn2:process /> of id Japanese name Decision
        // Attention 2，Currently the design of this project is in progress，Guarantee required Model、Deployment、ProcessDefinition Use the same key，Ensure relevance。
        //          Otherwise，Will lead to ProcessDefinition The page cannot be found。
        if (!Objects.equals(definition.getKey(), createReqDTO.getKey())) {
            throw exception(PROCESS_DEFINITION_KEY_NOT_MATCH, createReqDTO.getKey(), definition.getKey());
        }
        if (!Objects.equals(definition.getName(), createReqDTO.getName())) {
            throw exception(PROCESS_DEFINITION_NAME_NOT_MATCH, createReqDTO.getName(), definition.getName());
        }

        // Insert extended table
        BpmProcessDefinitionExtDO definitionDO = BpmProcessDefinitionConvert.INSTANCE.convert2(createReqDTO)
                .setProcessDefinitionId(definition.getId());
        processDefinitionMapper.insert(definitionDO);
        return definition.getId();
    }

    @Override
    public void updateProcessDefinitionState(String id, Integer state) {
        // Activate
        if (Objects.equals(SuspensionState.ACTIVE.getStateCode(), state)) {
            repositoryService.activateProcessDefinitionById(id, false, null);
            return;
        }
        // Suspended
        if (Objects.equals(SuspensionState.SUSPENDED.getStateCode(), state)) {
            // suspendProcessInstances = false，Tasks in progress，Do not suspend。
            // Reason：As long as new processes are not allowed to be initiated，The old process can continue to be executed。
            repositoryService.suspendProcessDefinitionById(id, false, null);
            return;
        }
        log.error("[updateProcessDefinitionState][Process definition({}) Modify unknown status({})]", id, state);
    }

    @Override
    public String getProcessDefinitionBpmnXML(String id) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(id);
        if (bpmnModel == null) {
            return null;
        }
        BpmnXMLConverter converter = new BpmnXMLConverter();
        return StrUtil.utf8Str(converter.convertToXML(bpmnModel));
    }

    @Override
    public boolean isProcessDefinitionEquals(@Valid BpmProcessDefinitionCreateReqDTO createReqDTO) {
        // Verification name、description Whether to update
        ProcessDefinition oldProcessDefinition = getActiveProcessDefinition(createReqDTO.getKey());
        if (oldProcessDefinition == null) {
            return false;
        }
        BpmProcessDefinitionExtDO oldProcessDefinitionExt = getProcessDefinitionExt(oldProcessDefinition.getId());
        if (!StrUtil.equals(createReqDTO.getName(), oldProcessDefinition.getName())
                || !StrUtil.equals(createReqDTO.getDescription(), oldProcessDefinitionExt.getDescription())
                || !StrUtil.equals(createReqDTO.getCategory(), oldProcessDefinition.getCategory())) {
            return false;
        }
        // Verification form Is the information updated?
        if (!ObjectUtil.equal(createReqDTO.getFormType(), oldProcessDefinitionExt.getFormType())
                || !ObjectUtil.equal(createReqDTO.getFormId(), oldProcessDefinitionExt.getFormId())
                || !ObjectUtil.equal(createReqDTO.getFormConf(), oldProcessDefinitionExt.getFormConf())
                || !ObjectUtil.equal(createReqDTO.getFormFields(), oldProcessDefinitionExt.getFormFields())
                || !ObjectUtil.equal(createReqDTO.getFormCustomCreatePath(), oldProcessDefinitionExt.getFormCustomCreatePath())
                || !ObjectUtil.equal(createReqDTO.getFormCustomViewPath(), oldProcessDefinitionExt.getFormCustomViewPath())) {
            return false;
        }
        // Verification BPMN XML Information
        BpmnModel newModel = buildBpmnModel(createReqDTO.getBpmnBytes());
        BpmnModel oldModel = getBpmnModel(oldProcessDefinition.getId());
        // Compare Byte Changes
        if (!BpmnModelUtils.equals(oldModel, newModel)) {
            return false;
        }
        // Finally found to be consistent，Then return true
        return true;
    }

    /**
     * Build the corresponding BPMN Model
     *
     * @param bpmnBytes Original BPMN XML Byte array
     * @return BPMN Model
     */
    private  BpmnModel buildBpmnModel(byte[] bpmnBytes) {
        // Convert to BpmnModel Object
        BpmnXMLConverter converter = new BpmnXMLConverter();
        return converter.convertToBpmnModel(new BytesStreamSource(bpmnBytes), true, true);
    }

    @Override
    public BpmProcessDefinitionExtDO getProcessDefinitionExt(String id) {
        return processDefinitionMapper.selectByProcessDefinitionId(id);
    }

    @Override
    public List<BpmProcessDefinitionRespVO> getProcessDefinitionList(BpmProcessDefinitionListReqVO listReqVO) {
        // Concatenate query conditions
        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        if (Objects.equals(SuspensionState.SUSPENDED.getStateCode(), listReqVO.getSuspensionState())) {
            definitionQuery.suspended();
        } else if (Objects.equals(SuspensionState.ACTIVE.getStateCode(), listReqVO.getSuspensionState())) {
            definitionQuery.active();
        }
        // Execute query
        definitionQuery.processDefinitionTenantId(TenantContextHolder.getTenantIdStr());
        List<ProcessDefinition> processDefinitions = definitionQuery.list();
        if (CollUtil.isEmpty(processDefinitions)) {
            return Collections.emptyList();
        }

        // Get BpmProcessDefinitionDO Map
        List<BpmProcessDefinitionExtDO> processDefinitionDOs = processDefinitionMapper.selectListByProcessDefinitionIds(
                convertList(processDefinitions, ProcessDefinition::getId));
        Map<String, BpmProcessDefinitionExtDO> processDefinitionDOMap = convertMap(processDefinitionDOs,
                BpmProcessDefinitionExtDO::getProcessDefinitionId);
        // Execute query，and return
        return BpmProcessDefinitionConvert.INSTANCE.convertList3(processDefinitions, processDefinitionDOMap);
    }

    @Override
    public PageResult<BpmProcessDefinitionPageItemRespVO> getProcessDefinitionPage(BpmProcessDefinitionPageReqVO pageVO) {
        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        if (StrUtil.isNotBlank(pageVO.getKey())) {
            definitionQuery.processDefinitionKey(pageVO.getKey());
        }

        // Execute query
        List<ProcessDefinition> processDefinitions = definitionQuery.orderByProcessDefinitionVersion().desc()
                .listPage(PageUtils.getStart(pageVO), pageVO.getPageSize());

        if (CollUtil.isEmpty(processDefinitions)) {
            return new PageResult<>(emptyList(), definitionQuery.count());
        }
        // Get Deployment Map
        Set<String> deploymentIds = new HashSet<>();
        processDefinitions.forEach(definition -> addIfNotNull(deploymentIds, definition.getDeploymentId()));
        Map<String, Deployment> deploymentMap = getDeploymentMap(deploymentIds);

        // Get BpmProcessDefinitionDO Map
        List<BpmProcessDefinitionExtDO> processDefinitionDOs = processDefinitionMapper.selectListByProcessDefinitionIds(
                convertList(processDefinitions, ProcessDefinition::getId));
        Map<String, BpmProcessDefinitionExtDO> processDefinitionDOMap = convertMap(processDefinitionDOs,
                BpmProcessDefinitionExtDO::getProcessDefinitionId);

        // Get Form Map
        Set<Long> formIds = convertSet(processDefinitionDOs, BpmProcessDefinitionExtDO::getFormId);
        Map<Long, BpmFormDO> formMap = formService.getFormMap(formIds);

        // Splicing results
        long definitionCount = definitionQuery.count();
        return new PageResult<>(BpmProcessDefinitionConvert.INSTANCE.convertList(processDefinitions, deploymentMap,
                processDefinitionDOMap, formMap), definitionCount);
    }

}
