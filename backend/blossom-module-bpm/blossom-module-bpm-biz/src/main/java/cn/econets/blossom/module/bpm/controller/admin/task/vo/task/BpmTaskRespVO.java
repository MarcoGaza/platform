package cn.econets.blossom.module.bpm.controller.admin.task.vo.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "Management Backend - Process Task Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmTaskRespVO extends BpmTaskDonePageItemRespVO {

    @Schema(description = "Task definition identifier", requiredMode = Schema.RequiredMode.REQUIRED, example = "user-001")
    private String definitionKey;

    /**
     * Reviewed user information
     */
    private User assigneeUser;

    /**
     * Parent taskID
     */
    private String parentTaskId;

    @Schema(description = "Subtask（Generated by signature）", requiredMode = Schema.RequiredMode.REQUIRED, example = "childrenTask")
    private List<BpmTaskRespVO> children;

    @Schema(description = "User Information")
    @Data
    public static class User {

        @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Long id;
        @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
        private String nickname;

        @Schema(description = "Department Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Long deptId;
        @Schema(description = "Department Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "R&D Department")
        private String deptName;

    }
}
