package cn.econets.blossom.module.system.controller.admin.dept.vo.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Department Information Response VO")
@Data
public class DeptRespVO {

    @Schema(description = "Department Number", example = "1024")
    private Long id;

    @Schema(description = "Department Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    private String name;

    @Schema(description = "Parent Department ID", example = "1024")
    private Long parentId;

    @Schema(description = "The display order cannot be empty", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer sort;

    @Schema(description = "User ID of the person in charge", example = "2048")
    private Long leaderUserId;

    @Schema(description = "Contact number", example = "15601691000")
    private String phone;

    @Schema(description = "Mailbox", example = "ryximu@qq.com")
    private String email;

    @Schema(description = "Status,See you CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "Timestamp format")
    private LocalDateTime createTime;

}
