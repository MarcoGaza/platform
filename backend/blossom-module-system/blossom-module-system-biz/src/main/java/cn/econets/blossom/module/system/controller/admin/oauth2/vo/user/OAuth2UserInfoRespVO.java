package cn.econets.blossom.module.system.controller.admin.oauth2.vo.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Management Backend - OAuth2 Get basic user information Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OAuth2UserInfoRespVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "User Account", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    private String username;

    @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    private String nickname;

    @Schema(description = "User mailbox", example = "ryximu@qq.com")
    private String email;
    @Schema(description = "Mobile phone number", example = "15601691300")
    private String mobile;

    @Schema(description = "User gender，See SexEnum Enumeration class", example = "1")
    private Integer sex;

    @Schema(description = "User avatar", example = "https://www.econets.cn/xxx.png")
    private String avatar;

    /**
     * Department
     */
    private Dept dept;

    /**
     * Position array
     */
    private List<Post> posts;

    @Schema(description = "Department")
    @Data
    public static class Dept {

        @Schema(description = "Department Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Long id;

        @Schema(description = "Department Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "R&D Department")
        private String name;

    }

    @Schema(description = "Position")
    @Data
    public static class Post {

        @Schema(description = "Position number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        private Long id;

        @Schema(description = "Position Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Development")
        private String name;

    }

}
