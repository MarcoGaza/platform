package cn.econets.blossom.module.promotion.controller.admin.bargain.vo.activity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "Management Backend - Pagination items for bargaining activities Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BargainActivityPageItemRespVO extends BargainActivityBaseVO {

    @Schema(description = "Activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "22901")
    private Long id;

    @Schema(description = "Product Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "618Big Sale")
    private String spuName;
    @Schema(description = "Product Main Picture", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.econets.cn/xx.png")
    private String picUrl;

    @Schema(description = "Activity Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "Activity status cannot be empty")
    private Integer status;

    @Schema(description = "Total inventory of activities", requiredMode = Schema.RequiredMode.REQUIRED, example = "23")
    private Integer totalStock;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED, example = "2022-07-01 23:59:59")
    private LocalDateTime createTime;

    // ========== Statistical fields ==========

    @Schema(description = "Total number of bargaining users", requiredMode = Schema.RequiredMode.REQUIRED, example = "999")
    private Integer recordUserCount;

    @Schema(description = "Number of users who successfully bargained", requiredMode = Schema.RequiredMode.REQUIRED, example = "500")
    private Integer recordSuccessUserCount;

    @Schema(description = "Number of users who helped bargain", requiredMode = Schema.RequiredMode.REQUIRED, example = "888")
    private Integer helpUserCount;

}
