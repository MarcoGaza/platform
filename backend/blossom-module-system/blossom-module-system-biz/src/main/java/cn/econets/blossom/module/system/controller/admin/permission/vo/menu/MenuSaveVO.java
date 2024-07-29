package cn.econets.blossom.module.system.controller.admin.permission.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(description = "Management Backend - Menu creation/Modify Request VO")
@Data
public class MenuSaveVO {

    @Schema(description = "Menu number", example = "1024")
    private Long id;

    @Schema(description = "Menu name", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    @NotBlank(message = "Menu name cannot be empty")
    @Size(max = 50, message = "Menu name length cannot exceed50Characters")
    private String name;

    @Schema(description = "Permission flag,Only when the menu type is button，Need to be passed", example = "sys:menu:add")
    @Size(max = 100)
    private String permission;

    @Schema(description = "Type，See MenuTypeEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Menu type cannot be empty")
    private Integer type;

    @Schema(description = "The display order cannot be empty", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The display order cannot be empty")
    private Integer sort;

    @Schema(description = "Parent Menu ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Parent Menu ID Cannot be empty")
    private Long parentId;

    @Schema(description = "Routing address,Only when the menu type is menu or directory，Need to upload", example = "post")
    @Size(max = 200, message = "The routing address cannot exceed200Characters")
    private String path;

    @Schema(description = "Menu icon,Only when the menu type is menu or directory，Need to upload", example = "/menu/list")
    private String icon;

    @Schema(description = "Component path,Only when the menu type is menu，Need to upload", example = "system/post/index")
    @Size(max = 200, message = "Component path cannot exceed255Characters")
    private String component;

    @Schema(description = "Component name", example = "SystemUser")
    private String componentName;

    @Schema(description = "Status,See you CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    private Integer status;

    @Schema(description = "Is it visible?", example = "false")
    private Boolean visible;

    @Schema(description = "Whether to cache", example = "false")
    private Boolean keepAlive;

    @Schema(description = "Whether to always display", example = "false")
    private Boolean alwaysShow;

}
