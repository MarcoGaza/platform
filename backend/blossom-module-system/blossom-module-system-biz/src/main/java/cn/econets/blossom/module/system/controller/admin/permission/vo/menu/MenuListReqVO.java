package cn.econets.blossom.module.system.controller.admin.permission.vo.menu;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Management Backend - Menu List Request VO")
@Data
public class MenuListReqVO {

    @Schema(description = "Menu Name，Fuzzy matching", example = "blossom")
    private String name;

    @Schema(description = "Display status，See CommonStatusEnum Enumeration class", example = "1")
    private Integer status;

}
