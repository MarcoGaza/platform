package cn.econets.blossom.module.bpm.controller.admin.task.vo.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

// TODO Class name，Should be delete Ha
@Schema(description = "Management Backend - Tasks for reducing signature process Request VO")
@Data
public class BpmTaskSubSignReqVO {

    @Schema(description = "The tasks that were reduced ID")
    @NotEmpty(message = "Task number cannot be empty")
    private String id;

    @Schema(description = "Reason for signing additional visa")
    @NotEmpty(message = "The reason for signing cannot be empty")
    private String reason;
}
