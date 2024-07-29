package cn.econets.blossom.module.bpm.controller.admin.task;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.bpm.controller.admin.task.vo.activity.BpmActivityRespVO;
import cn.econets.blossom.module.bpm.service.task.BpmActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Process activity instance")
@RestController
@RequestMapping("/bpm/activity")
@Validated
public class BpmActivityController {

    @Resource
    private BpmActivityService activityService;

    @GetMapping("/list")
    @Operation(summary = "Generate a highlighted flowchart for the specified process instance",
            description = "Only highlight ongoing tasks。But be careful，This interface is temporarily unused，Through the front end ProcessViewer.vue Interface highlightDiagram Method generation")
    @Parameter(name = "processInstanceId", description = "Process instance number", required = true)
    @PreAuthorize("@ss.hasPermission('bpm:task:query')")
    public CommonResult<List<BpmActivityRespVO>> getActivityList(
            @RequestParam("processInstanceId") String processInstanceId) {
        return success(activityService.getActivityListByProcessInstanceId(processInstanceId));
    }
}
