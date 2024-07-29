package cn.econets.blossom.module.bpm.controller.admin.task.vo.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

// TODO Class name，Should be create Ha
@Schema(description = "Management Backend - Adding signature process tasks Request VO")
@Data
public class BpmTaskAddSignReqVO {

    @Schema(description = "Tasks that require signatures ID")
    @NotEmpty(message = "Task number cannot be empty")
    private String id;

    @Schema(description = "Users who added signatures ID")
    @NotEmpty(message = "Add Signature User ID Cannot be empty")
    private Set<Long> userIdList;

    @Schema(description = "Additional signature type，before Add signature forward，after Add signature to the back")
    @NotEmpty(message = "The signature type cannot be empty")
    private String type;

    @Schema(description = "Reason for signing additional documents")
    @NotEmpty(message = "The reason for signing cannot be empty")
    private String reason;

}
