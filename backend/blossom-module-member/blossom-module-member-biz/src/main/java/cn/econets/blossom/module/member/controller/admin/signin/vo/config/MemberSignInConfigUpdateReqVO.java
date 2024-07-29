package cn.econets.blossom.module.member.controller.admin.signin.vo.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

@Schema(description = "Management Backend - Sign-in rules update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberSignInConfigUpdateReqVO extends MemberSignInConfigBaseVO {

    @Schema(description = "Rule auto-increment primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "13653")
    @NotNull(message = "The rule auto-increment primary key cannot be empty")
    private Long id;

}
