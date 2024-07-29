package cn.econets.blossom.module.member.controller.admin.point.vo.recrod;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - User points record Response VO")
@Data
public class MemberPointRecordRespVO {

    @Schema(description = "Auto-increment primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "31457")
    private Long id;

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long userId;

    @Schema(description = "Nickname", example = "Zhang San")
    private String nickname;

    @Schema(description = "Business Code", requiredMode = Schema.RequiredMode.REQUIRED, example = "22706")
    private String bizId;

    @Schema(description = "Business Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer bizType;

    @Schema(description = "Points Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Guess")
    private String title;

    @Schema(description = "Points description", example = "Guess")
    private String description;

    @Schema(description = "Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer point;

    @Schema(description = "Changed points", requiredMode = Schema.RequiredMode.REQUIRED, example = "200")
    private Integer totalPoint;

    @Schema(description = "Occurrence time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
