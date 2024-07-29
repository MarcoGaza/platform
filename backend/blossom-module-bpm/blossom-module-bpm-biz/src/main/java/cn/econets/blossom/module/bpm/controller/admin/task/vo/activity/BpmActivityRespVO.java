package cn.econets.blossom.module.bpm.controller.admin.task.vo.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Process activity Response VO")
@Data
public class BpmActivityRespVO {

    @Schema(description = "Process activity identifier", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String key;
    @Schema(description = "Type of process activity", requiredMode = Schema.RequiredMode.REQUIRED, example = "StartEvent")
    private String type;

    @Schema(description = "Start time of process activity", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime startTime;
    @Schema(description = "End time of process activity", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime endTime;

    @Schema(description = "The number of the associated process task-Associated process tasks，Only UserTask Only available in other types", example = "2048")
    private String taskId;

}
