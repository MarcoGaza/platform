package cn.econets.blossom.module.bpm.controller.admin.task.vo.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Process Task Done Completed pagination items Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmTaskDonePageItemRespVO extends BpmTaskTodoPageItemRespVO {

    @Schema(description = "End time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime endTime;
    @Schema(description = "Duration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1000")
    private Long durationInMillis;

    @Schema(description = "Task results-See also bpm_process_instance_result", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer result;
    @Schema(description = "Approval Suggestions", requiredMode = Schema.RequiredMode.REQUIRED, example = "No more leave！")
    private String reason;

}
