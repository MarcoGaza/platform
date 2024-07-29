package cn.econets.blossom.module.member.controller.app.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "User APP - User personal information Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppMemberUserInfoRespVO {

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    private String nickname;

    @Schema(description = "User avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xxx.png")
    private String avatar;

    @Schema(description = "User's mobile phone number", requiredMode = Schema.RequiredMode.REQUIRED, example = "15601691300")
    private String mobile;

    @Schema(description = "User gender", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sex;

    @Schema(description = "Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    private Integer point;

    @Schema(description = "Experience Value", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer experience;

    @Schema(description = "User Level")
    private Level level;

    @Schema(description = "Do you want to become a promoter?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    private Boolean brokerageEnabled;

    @Schema(description = "User App - Member Level")
    @Data
    public static class Level {

        @Schema(description = "Level Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Long id;

        @Schema(description = "Level Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
        private String name;

        @Schema(description = "Level", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Integer level;

        @Schema(description = "Level Icon", example = "https://www.econets.cn/blossom.jpg")
        private String icon;

    }

}
