package cn.econets.blossom.module.system.controller.admin.notice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Notice information Response VO")
@Data
public class NoticeRespVO {

    @Schema(description = "Notice Serial Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "Announcement Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Little blogger")
    private String title;

    @Schema(description = "Announcement Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "Little blogger")
    private Integer type;

    @Schema(description = "Announcement content", requiredMode = Schema.RequiredMode.REQUIRED, example = "Half Life Coding")
    private String content;

    @Schema(description = "Status，See CommonStatusEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer status;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "Timestamp format")
    private LocalDateTime createTime;

}
