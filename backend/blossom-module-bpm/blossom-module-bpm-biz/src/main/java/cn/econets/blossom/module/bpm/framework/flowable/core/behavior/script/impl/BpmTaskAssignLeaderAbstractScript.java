package cn.econets.blossom.module.bpm.framework.flowable.core.behavior.script.impl;

import cn.econets.blossom.framework.common.util.number.NumberUtils;
import cn.econets.blossom.module.bpm.framework.flowable.core.behavior.script.BpmTaskAssignScript;
import cn.econets.blossom.module.bpm.service.task.BpmProcessInstanceService;
import cn.econets.blossom.module.system.api.dept.DeptApi;
import cn.econets.blossom.module.system.api.dept.dto.DeptRespDTO;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.context.annotation.Lazy;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Set;

import static cn.econets.blossom.framework.common.util.collection.SetUtils.asSet;
import static java.util.Collections.emptySet;

/**
 * Assigned to the initiator Leader Approved Script Implementation class
 * Currently Leader The definition of，
 *
 */
public abstract class BpmTaskAssignLeaderAbstractScript implements BpmTaskAssignScript {

    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DeptApi deptApi;
    @Resource
    @Lazy // Solve circular dependencies
    private BpmProcessInstanceService bpmProcessInstanceService;

    protected Set<Long> calculateTaskCandidateUsers(DelegateExecution execution, int level) {
        Assert.isTrue(level > 0, "level Must be greater than 0");
        // Get the initiator
        ProcessInstance processInstance = bpmProcessInstanceService.getProcessInstance(execution.getProcessInstanceId());
        Long startUserId = NumberUtils.parseLong(processInstance.getStartUserId());
        // Get the corresponding leve Department
        DeptRespDTO dept = null;
        for (int i = 0; i < level; i++) {
            // Get level Corresponding department
            if (dept == null) {
                dept = getStartUserDept(startUserId);
                if (dept == null) { // Cannot find the initiator's department，So this rule cannot be used
                    return emptySet();
                }
            } else {
                DeptRespDTO parentDept = deptApi.getDept(dept.getParentId());
                if (parentDept == null) { // Cannot find parent department，So I had to end the search。The reason is：For example，People of higher rank，The department has relatively few levels
                    break;
                }
                dept = parentDept;
            }
        }
        return dept.getLeaderUserId() != null ? asSet(dept.getLeaderUserId()) : emptySet();
    }

    private DeptRespDTO getStartUserDept(Long startUserId) {
        AdminUserRespDTO startUser = adminUserApi.getUser(startUserId);
        if (startUser.getDeptId() == null) { // Cannot find department，So this rule cannot be used
            return null;
        }
        return deptApi.getDept(startUser.getDeptId());
    }

}
