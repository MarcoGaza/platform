package cn.econets.blossom.module.member.controller.app.user.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.system.enums.common.SexEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Schema(description = "User App - Member User Update Request VO")
@Data
public class AppMemberUserUpdateReqVO {

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    private String nickname;

    @Schema(description = "Avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/x.png")
    @URL(message = "The avatar must be URL Format")
    private String avatar;

    @Schema(description = "Gender", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sex;

}
