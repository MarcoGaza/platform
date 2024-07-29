package cn.econets.blossom.module.member.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - User modification level Request VO")
@Data
@ToString(callSuper = true)
public class MemberUserUpdateLevelReqVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "23788")
    @NotNull(message = "User ID cannot be empty")
    private Long id;

    /**
     * When canceling the user level，The value is empty
     */
    @Schema(description = "User level number", example = "1")
    private Long levelId;

    @Schema(description = "Reason for modification", requiredMode = Schema.RequiredMode.REQUIRED, example = "Promotion needs")
    @NotBlank(message = "Reason for modification cannot be empty")
    private String reason;

}
