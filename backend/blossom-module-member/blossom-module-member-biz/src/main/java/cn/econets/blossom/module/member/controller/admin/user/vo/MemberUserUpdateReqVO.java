package cn.econets.blossom.module.member.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Member User Update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberUserUpdateReqVO extends MemberUserBaseVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "23788")
    @NotNull(message = "The number cannot be empty")
    private Long id;

}
