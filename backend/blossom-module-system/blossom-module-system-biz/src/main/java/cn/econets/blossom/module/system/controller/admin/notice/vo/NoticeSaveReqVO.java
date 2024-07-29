package cn.econets.blossom.module.system.controller.admin.notice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(description = "Management Backend - Notification announcement creation/Modify Request VO")
@Data
public class NoticeSaveReqVO {

    @Schema(description = "Job announcement number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
//    @NotNull(message = "Job announcement number cannot be empty")
    private Long id;

    @Schema(description = "Announcement Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Little blogger")
    @NotBlank(message = "Announcement title cannot be empty")
    @Size(max = 50, message = "Announcement title cannot exceed50Characters")
    private String title;

    @Schema(description = "Announcement Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "Little blogger")
    @NotNull(message = "Announcement type cannot be empty")
    private Integer type;

    @Schema(description = "Announcement content", requiredMode = Schema.RequiredMode.REQUIRED, example = "Half Life Coding")
    private String content;

    @Schema(description = "Status，See CommonStatusEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

}
