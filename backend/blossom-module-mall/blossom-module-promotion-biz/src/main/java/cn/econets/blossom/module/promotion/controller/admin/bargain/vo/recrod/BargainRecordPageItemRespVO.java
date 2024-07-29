package cn.econets.blossom.module.promotion.controller.admin.bargain.vo.recrod;

import cn.econets.blossom.module.promotion.controller.admin.bargain.vo.activity.BargainActivityRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Pagination items of bargaining records Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BargainRecordPageItemRespVO extends BargainRecordBaseVO {

    @Schema(description = "Record number", requiredMode = Schema.RequiredMode.REQUIRED, example = "22901")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "2022-07-01 23:59:59")
    private LocalDateTime createTime;

    @Schema(description = "Number of times of helping to cut", requiredMode = Schema.RequiredMode.REQUIRED, example = "5")
    private Integer helpCount;

    // ========== User related ==========

    @Schema(description = "User Nickname", example = "Oldeconets")
    private String nickname;

    @Schema(description = "User avatar", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xxx.jpg")
    private String avatar;

    // ========== Activity related ==========

    private BargainActivityRespVO activity;

}
