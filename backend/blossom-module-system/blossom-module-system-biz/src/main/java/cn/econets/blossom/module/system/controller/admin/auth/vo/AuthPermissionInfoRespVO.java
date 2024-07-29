package cn.econets.blossom.module.system.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Schema(description = "Management Backend - Login user's permission information Response VO，Additionally includes user information and role list")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthPermissionInfoRespVO {

    @Schema(description = "User Information", requiredMode = Schema.RequiredMode.REQUIRED)
    private UserVO user;

    @Schema(description = "Role ID array", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<String> roles;

    @Schema(description = "Operation permission array", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<String> permissions;

    @Schema(description = "Menu Tree", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<MenuVO> menus;

    @Schema(description = "User Information VO")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class UserVO {

        @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
        private Long id;

        @Schema(description = "User Nickname", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
        private String nickname;

        @Schema(description = "User avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xx.jpg")
        private String avatar;

    }

    @Schema(description = "Management Backend - Menu information for logged-in users Response VO")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MenuVO {

        @Schema(description = "Menu Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
        private Long id;

        @Schema(description = "Parent Menu ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
        private Long parentId;

        @Schema(description = "Menu Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
        private String name;

        @Schema(description = "Routing address,Only when the menu type is menu or directory，Need to upload", example = "post")
        private String path;

        @Schema(description = "Component path,Only when the menu type is menu，Need to upload", example = "system/post/index")
        private String component;

        @Schema(description = "Component name", example = "SystemUser")
        private String componentName;

        @Schema(description = "Menu Icon,Only when the menu type is menu or directory，Need to upload", example = "/menu/list")
        private String icon;

        @Schema(description = "Is it visible?", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
        private Boolean visible;

        @Schema(description = "Whether to cache", requiredMode = Schema.RequiredMode.REQUIRED, example = "false")
        private Boolean keepAlive;

        @Schema(description = "Whether to always display", example = "false")
        private Boolean alwaysShow;

        /**
         * Sub-route
         */
        private List<MenuVO> children;

    }

}
