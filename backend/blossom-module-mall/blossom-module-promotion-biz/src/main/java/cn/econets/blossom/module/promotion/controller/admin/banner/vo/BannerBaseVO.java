package cn.econets.blossom.module.promotion.controller.admin.banner.vo;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.promotion.enums.banner.BannerPositionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Banner Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 *
 */
@Data
public class BannerBaseVO {

    @Schema(description = "Title", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Title cannot be empty")
    private String title;

    @Schema(description = "Jump link", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The jump link cannot be empty")
    private String url;

    @Schema(description = "Image address", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The image address cannot be empty")
    private String picUrl;

    @Schema(description = "position", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "position Cannot be empty")
    @InEnum(BannerPositionEnum.class)
    private Integer position;

    @Schema(description = "Sort", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The sort order cannot be empty")
    private Integer sort;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Status cannot be empty")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

    @Schema(description = "Remarks")
    private String memo;

}
