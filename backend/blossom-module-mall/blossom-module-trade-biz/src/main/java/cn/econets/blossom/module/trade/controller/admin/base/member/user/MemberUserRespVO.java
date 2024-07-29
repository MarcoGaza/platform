package cn.econets.blossom.module.trade.controller.admin.base.member.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Member User Response VO")
@Data
public class MemberUserRespVO {

    @Schema(description = "User ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "Yudao source code")
    private String nickname;

    @Schema(description = "User avatar", example = "https://www.econets.cn/xxx.png")
    private String avatar;

}
