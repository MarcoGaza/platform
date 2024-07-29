package cn.econets.blossom.module.promotion.controller.admin.bargain.vo.help;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;

@Schema(description = "Management Backend - Bargaining assistance Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BargainHelpRespVO extends BargainHelpBaseVO {

    @Schema(description = "Bargaining support number", requiredMode = Schema.RequiredMode.REQUIRED, example = "25860")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    // ========== User related ==========

    @Schema(description = "User Nickname", example = "Oldeconets")
    private String nickname;

    @Schema(description = "User avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xxx.jpg")
    private String avatar;

}
