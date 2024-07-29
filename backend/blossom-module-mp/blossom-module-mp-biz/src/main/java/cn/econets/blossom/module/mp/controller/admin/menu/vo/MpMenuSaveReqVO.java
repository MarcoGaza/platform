package cn.econets.blossom.module.mp.controller.admin.menu.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - Save from the public account menu Request VO")
@Data
public class MpMenuSaveReqVO {

    @Schema(description = "The public account number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    @NotNull(message = "The public account number cannot be empty")
    private Long accountId;

    @NotEmpty(message = "Menu cannot be empty")
    @Valid
    private List<Menu> menus;

    @Schema(description = "Management Backend - Each menu when the public account menu is saved")
    @Data
    public static class Menu extends MpMenuBaseVO {

        /**
         * Submenu array
         */
        private List<Menu> children;

    }

}
