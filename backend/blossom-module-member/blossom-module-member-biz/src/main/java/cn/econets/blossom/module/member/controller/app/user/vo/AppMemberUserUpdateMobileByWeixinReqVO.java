package cn.econets.blossom.module.member.controller.app.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Schema(description = "User APP - Authorization code based on WeChat applet，Modify mobile phone Request VO")
@Data
public class AppMemberUserUpdateMobileByWeixinReqVO {

    @Schema(description = "Mobile phone code，Mini program passed wx.getPhoneNumber Method to obtain",
            requiredMode = Schema.RequiredMode.REQUIRED, example = "hello")
    @NotEmpty(message = "Mobile phone code Cannot be empty")
    private String code;

}
