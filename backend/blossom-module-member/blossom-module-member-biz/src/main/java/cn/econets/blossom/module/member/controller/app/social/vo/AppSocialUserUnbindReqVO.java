package cn.econets.blossom.module.member.controller.app.social.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.system.enums.social.SocialTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "User APP - Cancel social binding Request VO")
@Data
public class AppSocialUserUnbindReqVO {

    @Schema(description = "Type of social platform，See SocialTypeEnum Enumeration value", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @InEnum(SocialTypeEnum.class)
    @NotNull(message = "The type of social platform cannot be empty")
    private Integer type;

    @Schema(description = "Social user openid", requiredMode = Schema.RequiredMode.REQUIRED, example = "IPRmJ0wvBptiPIlGEZiPewGwiEiE")
    @NotEmpty(message = "Social user openid Cannot be empty")
    private String openid;

}
