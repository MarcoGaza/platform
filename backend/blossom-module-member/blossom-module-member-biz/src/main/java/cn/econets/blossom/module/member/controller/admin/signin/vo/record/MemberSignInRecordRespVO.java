package cn.econets.blossom.module.member.controller.admin.signin.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Sign-in record Response VO")
@Data
public class MemberSignInRecordRespVO {

    @Schema(description = "Sign in and increase automatically id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11903")
    private Long id;

    @Schema(description = "Sign in user", requiredMode = Schema.RequiredMode.REQUIRED, example = "6507")
    private Long userId;

    @Schema(description = "Nickname", example = "Zhang San")
    private String nickname;

    @Schema(description = "Sign in on which day", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer day;

    @Schema(description = "Sign-in points", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer point;

    @Schema(description = "Sign-in time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
