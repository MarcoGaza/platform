package cn.econets.blossom.module.promotion.controller.app.bargain.vo.help;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "User App - Bargaining assistance Response VO")
@Data
public class AppBargainHelpRespVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long userId;

    @Schema(description = "Nickname of the support user", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String nickname;

    @Schema(description = "Helping user's avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private String avatar;

    @Schema(description = "Help users bargain a certain amount", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer reducePrice;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private LocalDateTime createTime;

}
