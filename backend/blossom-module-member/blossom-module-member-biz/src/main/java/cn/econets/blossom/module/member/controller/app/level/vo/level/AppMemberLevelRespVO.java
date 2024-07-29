package cn.econets.blossom.module.member.controller.app.level.vo.level;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "User App - Member Level Response VO")
@Data
public class AppMemberLevelRespVO {

    @Schema(description = "Level Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    private String name;

    @Schema(description = "Level", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer level;

    @Schema(description = "Upgrade Experience", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer experience;

    @Schema(description = "Enjoy discount", requiredMode = Schema.RequiredMode.REQUIRED, example = "98")
    private Integer discountPercent;

    @Schema(description = "Level Icon", example = "https://www.econets.cn/blossom.jpg")
    private String icon;

    @Schema(description = "Level background image", example = "https://www.econets.cn/blossom.jpg")
    private String backgroundUrl;

}
