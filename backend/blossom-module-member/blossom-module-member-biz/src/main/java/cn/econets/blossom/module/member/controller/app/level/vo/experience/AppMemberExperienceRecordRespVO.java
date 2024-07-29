package cn.econets.blossom.module.member.controller.app.level.vo.experience;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "User App - Member experience record Response VO")
@Data
public class AppMemberExperienceRecordRespVO {

    @Schema(description = "Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Increase experience")
    private String title;

    @Schema(description = "Experience", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer experience;

    @Schema(description = "Description", requiredMode = Schema.RequiredMode.REQUIRED, example = "Orders increase 100 Experience")
    private String description;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
