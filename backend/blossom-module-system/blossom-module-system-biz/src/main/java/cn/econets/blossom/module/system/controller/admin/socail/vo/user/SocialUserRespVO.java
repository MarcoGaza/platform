package cn.econets.blossom.module.system.controller.admin.socail.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Social user Response VO")
@Data
public class SocialUserRespVO {

    @Schema(description = "Primary key(Self-increase strategy)", requiredMode = Schema.RequiredMode.REQUIRED, example = "14569")
    private Long id;

    @Schema(description = "Type of social platform", requiredMode = Schema.RequiredMode.REQUIRED, example = "30")
    private Integer type;

    @Schema(description = "Social openid", requiredMode = Schema.RequiredMode.REQUIRED, example = "666")
    private String openid;

    @Schema(description = "Social token", requiredMode = Schema.RequiredMode.REQUIRED, example = "666")
    private String token;

    @Schema(description = "Original Token Data，Generally JSON Format", requiredMode = Schema.RequiredMode.REQUIRED, example = "{}")
    private String rawTokenInfo;

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    private String nickname;

    @Schema(description = "User avatar", example = "https://www.econets.cn/xxx.png")
    private String avatar;

    @Schema(description = "Original user data，Generally JSON Format", requiredMode = Schema.RequiredMode.REQUIRED, example = "{}")
    private String rawUserInfo;

    @Schema(description = "The last authentication code", requiredMode = Schema.RequiredMode.REQUIRED, example = "666666")
    private String code;

    @Schema(description = "The last authentication state", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    private String state;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Update time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime updateTime;

}
