package cn.econets.blossom.module.member.controller.app.point.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "User App - User points record Response VO")
@Data
public class AppMemberPointRecordRespVO {

    @Schema(description = "Auto-increment primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "31457")
    private Long id;

    @Schema(description = "Points Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Guess")
    private String title;

    @Schema(description = "Points description", example = "Guess")
    private String description;

    @Schema(description = "Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer point;

    @Schema(description = "Occurrence time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
