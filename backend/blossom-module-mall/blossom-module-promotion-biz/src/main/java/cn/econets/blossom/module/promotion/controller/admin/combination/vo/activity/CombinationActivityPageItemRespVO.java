package cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity;

import cn.econets.blossom.module.promotion.controller.admin.combination.vo.product.CombinationProductRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Pagination items for group buying activities Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CombinationActivityPageItemRespVO extends CombinationActivityBaseVO {

    @Schema(description = "Activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "22901")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Activity Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer status;

    @Schema(description = "Group buy product", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CombinationProductRespVO> products;

    // ========== Product Field ==========

    @Schema(description = "Product Name", requiredMode = Schema.RequiredMode.REQUIRED, // From SPU of name Read
            example = "618Big Sale")
    private String spuName;
    @Schema(description = "Product Main Picture", requiredMode = Schema.RequiredMode.REQUIRED, // From SPU of picUrl Read
            example = "https://www.econets.cn/xx.png")
    private String picUrl;
    @Schema(description = "Commodity market price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, // From SPU of marketPrice Read
            example = "50")
    private Integer marketPrice;

    // ========== Statistical fields ==========

    @Schema(description = "Number of groups to be opened", requiredMode = Schema.RequiredMode.REQUIRED, example = "33")
    private Integer groupCount;

    @Schema(description = "Number of groups", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
    private Integer groupSuccessCount;

    @Schema(description = "Number of purchases", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    private Integer recordCount;

}
