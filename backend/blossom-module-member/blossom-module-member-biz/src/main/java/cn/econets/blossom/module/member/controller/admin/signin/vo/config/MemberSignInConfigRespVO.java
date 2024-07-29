package cn.econets.blossom.module.member.controller.admin.signin.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "Management Backend - Sign-in Rules Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberSignInConfigRespVO extends MemberSignInConfigBaseVO {

    @Schema(description = "Auto-increment primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "20937")
    private Integer id;

    @Schema(description = "Creation time")
    private LocalDateTime createTime;

}
