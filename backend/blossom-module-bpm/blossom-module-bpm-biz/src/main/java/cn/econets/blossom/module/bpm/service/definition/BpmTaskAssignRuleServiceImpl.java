package cn.econets.blossom.module.bpm.service.definition;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.object.ObjectUtils;
import cn.econets.blossom.framework.permission.core.annotation.DataPermission;
import cn.econets.blossom.framework.flowable.core.util.BpmnModelUtils;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.rule.BpmTaskAssignRuleCreateReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.rule.BpmTaskAssignRuleRespVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.rule.BpmTaskAssignRuleUpdateReqVO;
import cn.econets.blossom.module.bpm.convert.definition.BpmTaskAssignRuleConvert;
import cn.econets.blossom.module.bpm.dal.dataobject.definition.BpmTaskAssignRuleDO;
import cn.econets.blossom.module.bpm.dal.dataobject.definition.BpmUserGroupDO;
import cn.econets.blossom.module.bpm.dal.mysql.definition.BpmTaskAssignRuleMapper;
import cn.econets.blossom.module.bpm.enums.DictTypeConstants;
import cn.econets.blossom.module.bpm.enums.definition.BpmTaskAssignRuleTypeEnum;
import cn.econets.blossom.module.bpm.framework.flowable.core.behavior.script.BpmTaskAssignScript;
import cn.econets.blossom.module.bpm.service.task.BpmProcessInstanceService;
import cn.econets.blossom.module.system.api.dept.DeptApi;
import cn.econets.blossom.module.system.api.dept.PostApi;
import cn.econets.blossom.module.system.api.dept.dto.DeptRespDTO;
import cn.econets.blossom.module.system.api.dict.DictDataApi;
import cn.econets.blossom.module.system.api.permission.PermissionApi;
import cn.econets.blossom.module.system.api.permission.RoleApi;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.engine.delegate.DelegateExecution;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;
import java.util.function.Function;

import static cn.hutool.core.text.CharSequenceUtil.format;
import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.framework.common.util.json.JsonUtils.toJsonString;
import static cn.econets.blossom.module.bpm.enums.ErrorCodeConstants.*;

/**
 * BPM Task allocation rules Service Implementation class
 */
@Service
@Validated
@Slf4j
public class BpmTaskAssignRuleServiceImpl implements BpmTaskAssignRuleService {

    @Resource
    private BpmTaskAssignRuleMapper taskRuleMapper;
    @Resource
    @Lazy // Solve circular dependencies
    private BpmModelService modelService;
    @Resource
    @Lazy // Solve circular dependencies
    private BpmProcessDefinitionService processDefinitionService;
    @Resource
    private BpmUserGroupService userGroupService;
    @Resource
    private RoleApi roleApi;
    @Resource
    private DeptApi deptApi;
    @Resource
    private PostApi postApi;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DictDataApi dictDataApi;
    @Resource
    private PermissionApi permissionApi;
    @Resource
    @Lazy // Solve circular dependencies
    private BpmProcessInstanceService processInstanceService;
    /**
     * Task assignment script
     */
    private Map<Long, BpmTaskAssignScript> scriptMap = Collections.emptyMap();

    @Resource
    public void setScripts(List<BpmTaskAssignScript> scripts) {
        this.scriptMap = convertMap(scripts, script -> script.getEnum().getId());
    }

    @Override
    public List<BpmTaskAssignRuleDO> getTaskAssignRuleListByProcessDefinitionId(String processDefinitionId,
                                                                                String taskDefinitionKey) {
        return taskRuleMapper.selectListByProcessDefinitionId(processDefinitionId, taskDefinitionKey);
    }

    @Override
    public List<BpmTaskAssignRuleDO> getTaskAssignRuleListByModelId(String modelId) {
        return taskRuleMapper.selectListByModelId(modelId);
    }

    @Override
    public List<BpmTaskAssignRuleRespVO> getTaskAssignRuleList(String modelId, String processDefinitionId) {
        // Get rules
        List<BpmTaskAssignRuleDO> rules = Collections.emptyList();
        BpmnModel model = null;
        if (StrUtil.isNotEmpty(modelId)) {
            rules = getTaskAssignRuleListByModelId(modelId);
            model = modelService.getBpmnModel(modelId);
        } else if (StrUtil.isNotEmpty(processDefinitionId)) {
            rules = getTaskAssignRuleListByProcessDefinitionId(processDefinitionId, null);
            model = processDefinitionService.getBpmnModel(processDefinitionId);
        }
        if (model == null) {
            return Collections.emptyList();
        }
        // Get user tasks，Only user tasks can set allocation rules
        List<UserTask> userTasks = BpmnModelUtils.getBpmnModelElements(model, UserTask.class);
        if (CollUtil.isEmpty(userTasks)) {
            return Collections.emptyList();
        }
        // Convert data
        return BpmTaskAssignRuleConvert.INSTANCE.convertList(userTasks, rules);
    }

    @Override
    public Long createTaskAssignRule(@Valid BpmTaskAssignRuleCreateReqVO reqVO) {
        // Verification parameters
        validTaskAssignRuleOptions(reqVO.getType(), reqVO.getOptions());
        // Check whether it has been configured
        BpmTaskAssignRuleDO existRule =
                taskRuleMapper.selectListByModelIdAndTaskDefinitionKey(reqVO.getModelId(), reqVO.getTaskDefinitionKey());
        if (existRule != null) {
            throw exception(TASK_ASSIGN_RULE_EXISTS, reqVO.getModelId(), reqVO.getTaskDefinitionKey());
        }

        // Storage
        BpmTaskAssignRuleDO rule = BpmTaskAssignRuleConvert.INSTANCE.convert(reqVO)
                .setProcessDefinitionId(BpmTaskAssignRuleDO.PROCESS_DEFINITION_ID_NULL); // Only process model，Only new creation is allowed
        taskRuleMapper.insert(rule);
        return rule.getId();
    }

    @Override
    public void updateTaskAssignRule(@Valid BpmTaskAssignRuleUpdateReqVO reqVO) {
        // Verify parameters
        validTaskAssignRuleOptions(reqVO.getType(), reqVO.getOptions());
        // Check if it exists
        BpmTaskAssignRuleDO existRule = taskRuleMapper.selectById(reqVO.getId());
        if (existRule == null) {
            throw exception(TASK_ASSIGN_RULE_NOT_EXISTS);
        }
        // Only allow modification of process model rules
        if (!Objects.equals(BpmTaskAssignRuleDO.PROCESS_DEFINITION_ID_NULL, existRule.getProcessDefinitionId())) {
            throw exception(TASK_UPDATE_FAIL_NOT_MODEL);
        }

        // Perform update
        taskRuleMapper.updateById(BpmTaskAssignRuleConvert.INSTANCE.convert(reqVO));
    }

    @Override
    public boolean isTaskAssignRulesEquals(String modelId, String processDefinitionId) {
        // Call VO The reason for the interface is，Filter out rules not needed by the process model，Keep and copyTaskAssignRules Consistency of methods
        List<BpmTaskAssignRuleRespVO> modelRules = getTaskAssignRuleList(modelId, null);
        List<BpmTaskAssignRuleRespVO> processInstanceRules = getTaskAssignRuleList(null, processDefinitionId);
        if (modelRules.size() != processInstanceRules.size()) {
            return false;
        }

        // Traverse，Match the corresponding rules
        Map<String, BpmTaskAssignRuleRespVO> processInstanceRuleMap =
                CollectionUtils.convertMap(processInstanceRules, BpmTaskAssignRuleRespVO::getTaskDefinitionKey);
        for (BpmTaskAssignRuleRespVO modelRule : modelRules) {
            BpmTaskAssignRuleRespVO processInstanceRule = processInstanceRuleMap.get(modelRule.getTaskDefinitionKey());
            if (processInstanceRule == null) {
                return false;
            }
            if (!ObjectUtil.equals(modelRule.getType(), processInstanceRule.getType()) || !ObjectUtil.equal(
                    modelRule.getOptions(), processInstanceRule.getOptions())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void copyTaskAssignRules(String fromModelId, String toProcessDefinitionId) {
        List<BpmTaskAssignRuleRespVO> rules = getTaskAssignRuleList(fromModelId, null);
        if (CollUtil.isEmpty(rules)) {
            return;
        }
        // Start copying
        List<BpmTaskAssignRuleDO> newRules = BpmTaskAssignRuleConvert.INSTANCE.convertList2(rules);
        newRules.forEach(rule -> rule.setProcessDefinitionId(toProcessDefinitionId).setId(null).setCreateTime(null)
                .setUpdateTime(null));
        taskRuleMapper.insertBatch(newRules);
    }

    @Override
    public void checkTaskAssignRuleAllConfig(String id) {
        // No user tasks are configured，So no need to configure rules
        List<BpmTaskAssignRuleRespVO> taskAssignRules = getTaskAssignRuleList(id, null);
        if (CollUtil.isEmpty(taskAssignRules)) {
            return;
        }
        // Check for unconfigured rules
        taskAssignRules.forEach(rule -> {
            if (CollUtil.isEmpty(rule.getOptions())) {
                throw exception(MODEL_DEPLOY_FAIL_TASK_ASSIGN_RULE_NOT_CONFIG, rule.getTaskDefinitionName());
            }
        });
    }

    private void validTaskAssignRuleOptions(Integer type, Set<Long> options) {
        if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.ROLE.getType())) {
            roleApi.validRoleList(options);
        } else if (ObjectUtils.equalsAny(type, BpmTaskAssignRuleTypeEnum.DEPT_MEMBER.getType(),
                BpmTaskAssignRuleTypeEnum.DEPT_LEADER.getType())) {
            deptApi.validateDeptList(options);
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.POST.getType())) {
            postApi.validPostList(options);
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.USER.getType())) {
            adminUserApi.validateUserList(options);
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.USER_GROUP.getType())) {
            userGroupService.validUserGroups(options);
        } else if (Objects.equals(type, BpmTaskAssignRuleTypeEnum.SCRIPT.getType())) {
            dictDataApi.validateDictDataList(DictTypeConstants.TASK_ASSIGN_SCRIPT,
                    CollectionUtils.convertSet(options, String::valueOf));
        } else {
            throw new IllegalArgumentException(format("Unknown rule type({})", type));
        }
    }

    @Override
    @DataPermission(enable = false) // Ignore data permissions，Otherwise there will be problems with allocation
    public Set<Long> calculateTaskCandidateUsers(DelegateExecution execution) {
        // 1. First obtain from the pre-selected approvers
        List<Long> assignee = processInstanceService.getAssigneeByProcessInstanceIdAndTaskDefinitionKey(
                execution.getProcessInstanceId(), execution.getCurrentActivityId());
        if (CollUtil.isNotEmpty(assignee)) {
            // TODO new HashSet That's it
            return convertSet(assignee, Function.identity());
        }
        // 2. By assigning rules，Calculate approvers
        BpmTaskAssignRuleDO rule = getTaskRule(execution);
        return calculateTaskCandidateUsers(execution, rule);
    }

    @VisibleForTesting
    BpmTaskAssignRuleDO getTaskRule(DelegateExecution execution) {
        List<BpmTaskAssignRuleDO> taskRules = getTaskAssignRuleListByProcessDefinitionId(
                execution.getProcessDefinitionId(), execution.getCurrentActivityId());
        if (CollUtil.isEmpty(taskRules)) {
            throw new FlowableException(format("Process Task({}/{}/{}) No matching task rules found",
                    execution.getId(), execution.getProcessDefinitionId(), execution.getCurrentActivityId()));
        }
        if (taskRules.size() > 1) {
            throw new FlowableException(format("Process Task({}/{}/{}) Too many task rules found({})",
                    execution.getId(), execution.getProcessDefinitionId(), execution.getCurrentActivityId()));
        }
        return taskRules.get(0);
    }

    @VisibleForTesting
    Set<Long> calculateTaskCandidateUsers(DelegateExecution execution, BpmTaskAssignRuleDO rule) {
        Set<Long> assigneeUserIds = null;
        if (Objects.equals(BpmTaskAssignRuleTypeEnum.ROLE.getType(), rule.getType())) {
            assigneeUserIds = calculateTaskCandidateUsersByRole(rule);
        } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.DEPT_MEMBER.getType(), rule.getType())) {
            assigneeUserIds = calculateTaskCandidateUsersByDeptMember(rule);
        } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.DEPT_LEADER.getType(), rule.getType())) {
            assigneeUserIds = calculateTaskCandidateUsersByDeptLeader(rule);
        } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.POST.getType(), rule.getType())) {
            assigneeUserIds = calculateTaskCandidateUsersByPost(rule);
        } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.USER.getType(), rule.getType())) {
            assigneeUserIds = calculateTaskCandidateUsersByUser(rule);
        } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.USER_GROUP.getType(), rule.getType())) {
            assigneeUserIds = calculateTaskCandidateUsersByUserGroup(rule);
        } else if (Objects.equals(BpmTaskAssignRuleTypeEnum.SCRIPT.getType(), rule.getType())) {
            assigneeUserIds = calculateTaskCandidateUsersByScript(execution, rule);
        }

        // Remove disabled users
        removeDisableUsers(assigneeUserIds);
        // If candidates are empty，Throws an exception
        if (CollUtil.isEmpty(assigneeUserIds)) {
            log.error("[calculateTaskCandidateUsers][Process Task({}/{}/{}) Task Rules({}) No candidates found]", execution.getId(),
                    execution.getProcessDefinitionId(), execution.getCurrentActivityId(), toJsonString(rule));
            throw exception(TASK_CREATE_FAIL_NO_CANDIDATE_USER);
        }
        return assigneeUserIds;
    }

    private Set<Long> calculateTaskCandidateUsersByRole(BpmTaskAssignRuleDO rule) {
        return permissionApi.getUserRoleIdListByRoleIds(rule.getOptions());
    }

    private Set<Long> calculateTaskCandidateUsersByDeptMember(BpmTaskAssignRuleDO rule) {
        List<AdminUserRespDTO> users = adminUserApi.getUserListByDeptIds(rule.getOptions());
        return convertSet(users, AdminUserRespDTO::getId);
    }

    private Set<Long> calculateTaskCandidateUsersByDeptLeader(BpmTaskAssignRuleDO rule) {
        List<DeptRespDTO> depts = deptApi.getDeptList(rule.getOptions());
        return convertSet(depts, DeptRespDTO::getLeaderUserId);
    }

    private Set<Long> calculateTaskCandidateUsersByPost(BpmTaskAssignRuleDO rule) {
        List<AdminUserRespDTO> users = adminUserApi.getUserListByPostIds(rule.getOptions());
        return convertSet(users, AdminUserRespDTO::getId);
    }

    private Set<Long> calculateTaskCandidateUsersByUser(BpmTaskAssignRuleDO rule) {
        return rule.getOptions();
    }

    private Set<Long> calculateTaskCandidateUsersByUserGroup(BpmTaskAssignRuleDO rule) {
        List<BpmUserGroupDO> userGroups = userGroupService.getUserGroupList(rule.getOptions());
        Set<Long> userIds = new HashSet<>();
        userGroups.forEach(group -> userIds.addAll(group.getMemberUserIds()));
        return userIds;
    }

    private Set<Long> calculateTaskCandidateUsersByScript(DelegateExecution execution, BpmTaskAssignRuleDO rule) {
        // Get the corresponding script
        List<BpmTaskAssignScript> scripts = new ArrayList<>(rule.getOptions().size());
        rule.getOptions().forEach(id -> {
            BpmTaskAssignScript script = scriptMap.get(id);
            if (script == null) {
                throw exception(TASK_ASSIGN_SCRIPT_NOT_EXISTS, id);
            }
            scripts.add(script);
        });
        // Calculate tasks one by one
        Set<Long> userIds = new HashSet<>();
        scripts.forEach(script -> CollUtil.addAll(userIds, script.calculateTaskCandidateUsers(execution)));
        return userIds;
    }

    @VisibleForTesting
    void removeDisableUsers(Set<Long> assigneeUserIds) {
        if (CollUtil.isEmpty(assigneeUserIds)) {
            return;
        }
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(assigneeUserIds);
        assigneeUserIds.removeIf(id -> {
            AdminUserRespDTO user = userMap.get(id);
            return user == null || !CommonStatusEnum.ENABLE.getStatus().equals(user.getStatus());
        });
    }

}
