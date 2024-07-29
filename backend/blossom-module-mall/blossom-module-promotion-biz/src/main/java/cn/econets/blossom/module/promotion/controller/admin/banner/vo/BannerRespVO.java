package cn.econets.blossom.module.promotion.controller.admin.banner.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Banner Response VO")
@Data
@ToString(callSuper = true)
public class BannerRespVO  extends BannerBaseVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "2022-07-01 23:59:59")
    private LocalDateTime createTime;

}
