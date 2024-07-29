package cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity;

import cn.econets.blossom.module.promotion.controller.admin.combination.vo.product.CombinationProductRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Management Backend - Group buying activity Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CombinationActivityRespVO extends CombinationActivityBaseVO {

    @Schema(description = "Activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "22901")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "Number of people in the group", requiredMode = Schema.RequiredMode.REQUIRED, example = "666")
    private Integer userSize;

    @Schema(description = "Group buy product", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<CombinationProductRespVO> products;

}
