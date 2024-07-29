package cn.econets.blossom.module.bpm.controller.admin.definition;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.rule.BpmTaskAssignRuleCreateReqVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.rule.BpmTaskAssignRuleRespVO;
import cn.econets.blossom.module.bpm.controller.admin.definition.vo.rule.BpmTaskAssignRuleUpdateReqVO;
import cn.econets.blossom.module.bpm.service.definition.BpmTaskAssignRuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Task allocation rules")
@RestController
@RequestMapping("/bpm/task-assign-rule")
@Validated
public class BpmTaskAssignRuleController {

    @Resource
    private BpmTaskAssignRuleService taskAssignRuleService;

    @GetMapping("/list")
    @Operation(summary = "Get the task assignment rule list")
    @Parameters({
            @Parameter(name = "modelId", description = "Model number", example = "1024"),
            @Parameter(name = "processDefinitionId", description = "Process definition number", example = "2048")
    })
    @PreAuthorize("@ss.hasPermission('bpm:task-assign-rule:query')")
    public CommonResult<List<BpmTaskAssignRuleRespVO>> getTaskAssignRuleList(
            @RequestParam(value = "modelId", required = false) String modelId,
            @RequestParam(value = "processDefinitionId", required = false) String processDefinitionId) {
        return success(taskAssignRuleService.getTaskAssignRuleList(modelId, processDefinitionId));
    }

    @PostMapping("/create")
    @Operation(summary = "Create task assignment rules")
    @PreAuthorize("@ss.hasPermission('bpm:task-assign-rule:create')")
    public CommonResult<Long> createTaskAssignRule(@Valid @RequestBody BpmTaskAssignRuleCreateReqVO reqVO) {
        return success(taskAssignRuleService.createTaskAssignRule(reqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update task assignment rules")
    @PreAuthorize("@ss.hasPermission('bpm:task-assign-rule:update')")
    public CommonResult<Boolean> updateTaskAssignRule(@Valid @RequestBody BpmTaskAssignRuleUpdateReqVO reqVO) {
        taskAssignRuleService.updateTaskAssignRule(reqVO);
        return success(true);
    }
}
