package cn.econets.blossom.module.bpm.controller.admin.task;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.bpm.controller.admin.task.vo.instance.*;
import cn.econets.blossom.module.bpm.service.task.BpmProcessInstanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "Management Backend - Process instance") // Process instance，Created once by process definition“Apply”
@RestController
@RequestMapping("/bpm/process-instance")
@Validated
public class BpmProcessInstanceController {

    @Resource
    private BpmProcessInstanceService processInstanceService;

    @GetMapping("/my-page")
    @Operation(summary = "Get my instance pagination list", description = "In【My process】In the menu，Make a call")
    @PreAuthorize("@ss.hasPermission('bpm:process-instance:query')")
    public CommonResult<PageResult<BpmProcessInstancePageItemRespVO>> getMyProcessInstancePage(
            @Valid BpmProcessInstanceMyPageReqVO pageReqVO) {
        return success(processInstanceService.getMyProcessInstancePage(getLoginUserId(), pageReqVO));
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new process instance")
    @PreAuthorize("@ss.hasPermission('bpm:process-instance:query')")
    public CommonResult<String> createProcessInstance(@Valid @RequestBody BpmProcessInstanceCreateReqVO createReqVO) {
        return success(processInstanceService.createProcessInstance(getLoginUserId(), createReqVO));
    }

    @GetMapping("/get")
    @Operation(summary = "Get the specified process instance", description = "In【Process details】Interface，Make a call")
    @Parameter(name = "id", description = "Process instance number", required = true)
    @PreAuthorize("@ss.hasPermission('bpm:process-instance:query')")
    public CommonResult<BpmProcessInstanceRespVO> getProcessInstance(@RequestParam("id") String id) {
        return success(processInstanceService.getProcessInstanceVO(id));
    }

    @DeleteMapping("/cancel")
    @Operation(summary = "Cancel process instance", description = "Withdraw the initiated process")
    @PreAuthorize("@ss.hasPermission('bpm:process-instance:cancel')")
    public CommonResult<Boolean> cancelProcessInstance(@Valid @RequestBody BpmProcessInstanceCancelReqVO cancelReqVO) {
        processInstanceService.cancelProcessInstance(getLoginUserId(), cancelReqVO);
        return success(true);
    }
}
