package cn.econets.blossom.module.bpm.controller.admin.task.vo.instance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Process instance paging Item Response VO")
@Data
public class BpmProcessInstancePageItemRespVO {

    @Schema(description = "Process instance number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String id;

    @Schema(description = "Process Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
    private String name;

    @Schema(description = "Process definition number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private String processDefinitionId;

    @Schema(description = "Process Classification-See bpm_model_category Data dictionary", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String category;

    @Schema(description = "The status of the process instance-See bpm_process_instance_status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Results of process instance-See bpm_process_instance_result", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Integer result;

    @Schema(description = "Submission time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "End time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime endTime;

    /**
     * Current Task
     */
    private List<Task> tasks;

    @Schema(description = "Process Task")
    @Data
    public static class Task {

        @Schema(description = "Process task number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
        private String id;

        @Schema(description = "Task Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
        private String name;

    }

}
