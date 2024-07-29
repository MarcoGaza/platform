package cn.econets.blossom.module.member.controller.app.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Schema(description = "User APP - WeChat Mini Program Mobile Login Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppAuthWeixinMiniAppLoginReqVO {

    @Schema(description = "Mobile phone code，Mini program passed wx.getPhoneNumber Method to obtain", requiredMode = Schema.RequiredMode.REQUIRED, example = "hello")
    @NotEmpty(message = "Mobile phone code Cannot be empty")
    private String phoneCode;

    @Schema(description = "Login code，Mini program passed wx.login Method to obtain", requiredMode = Schema.RequiredMode.REQUIRED, example = "word")
    @NotEmpty(message = "Login code Cannot be empty")
    private String loginCode;

    @Schema(description = "state", requiredMode = Schema.RequiredMode.REQUIRED, example = "9b2ffbc1-7425-4155-9894-9d5c08541d62")
    @NotEmpty(message = "state Cannot be empty")
    private String state;

}
