package cn.econets.blossom.module.bpm.controller.admin.task.vo.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Tasks for reducing signature process Response VO")
@Data
public class BpmTaskSubSignRespVO {
    @Schema(description = "Reviewed user information", requiredMode = Schema.RequiredMode.REQUIRED, example = "Xiao Li")
    private BpmTaskRespVO.User assigneeUser;
    @Schema(description = "Mission ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "12312")
    private String id;
    @Schema(description = "Task Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Manager Approval")
    private String name;
}
