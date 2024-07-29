package cn.econets.blossom.module.system.controller.admin.socail.vo.user;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.system.enums.social.SocialTypeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Cancel social binding Request VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SocialUserUnbindReqVO {

    @Schema(description = "Type of social platform，See UserSocialTypeEnum Enumeration value", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @InEnum(SocialTypeEnum.class)
    @NotNull(message = "The type of social platform cannot be empty")
    private Integer type;

    @Schema(description = "Social user's openid", requiredMode = Schema.RequiredMode.REQUIRED, example = "IPRmJ0wvBptiPIlGEZiPewGwiEiE")
    @NotEmpty(message = "Social user openid Cannot be empty")
    private String openid;

}
