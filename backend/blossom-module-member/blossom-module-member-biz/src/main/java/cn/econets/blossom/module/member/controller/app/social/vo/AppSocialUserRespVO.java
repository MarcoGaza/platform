package cn.econets.blossom.module.member.controller.app.social.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User APP - Social user Response VO")
@Data
public class AppSocialUserRespVO {

    @Schema(description = "Social user openid", requiredMode = Schema.RequiredMode.REQUIRED, example = "IPRmJ0wvBptiPIlGEZiPewGwiEiE")
    private String openid;

    @Schema(description = "Nickname of social user", requiredMode = Schema.RequiredMode.REQUIRED, example = "Source code")
    private String nickname;

    @Schema(description = "Social user's avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/1.png")
    private String avatar;

}
