package cn.econets.blossom.module.bpm.controller.admin.task.vo.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Process Task Running Pagination items in progress Response VO")
@Data
public class BpmTaskTodoPageItemRespVO {

    @Schema(description = "Task number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String id;

    @Schema(description = "Task Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
    private String name;

    @Schema(description = "Receive time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime claimTime;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Activation Status-See SuspensionState Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer suspensionState;

    /**
     * Process instance
     */
    private ProcessInstance processInstance;

    @Data
    @Schema(description = "Process instance")
    public static class ProcessInstance {

        @Schema(description = "Process instance number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
        private String id;

        @Schema(description = "Process instance name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Taro Road")
        private String name;

        @Schema(description = "The initiator's user ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
        private Long startUserId;

        @Schema(description = "User nickname of the initiator", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
        private String startUserNickname;

        @Schema(description = "Process definition number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
        private String processDefinitionId;

    }

}
