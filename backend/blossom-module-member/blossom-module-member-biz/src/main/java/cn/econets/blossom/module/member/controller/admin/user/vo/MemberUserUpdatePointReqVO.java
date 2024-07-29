package cn.econets.blossom.module.member.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - User modifies points Request VO")
@Data
@ToString(callSuper = true)
public class MemberUserUpdatePointReqVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "23788")
    @NotNull(message = "User ID cannot be empty")
    private Long id;

    @Schema(description = "Change Points，Positive numbers mean increase，Negative numbers mean decrease", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "Change integral cannot be empty")
    private Integer point;

}
