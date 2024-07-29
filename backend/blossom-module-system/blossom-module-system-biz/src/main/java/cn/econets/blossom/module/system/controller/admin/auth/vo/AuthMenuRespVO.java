package cn.econets.blossom.module.system.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Management Backend - Menu information for logged-in users Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthMenuRespVO {

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
    private List<AuthMenuRespVO> children;

}
