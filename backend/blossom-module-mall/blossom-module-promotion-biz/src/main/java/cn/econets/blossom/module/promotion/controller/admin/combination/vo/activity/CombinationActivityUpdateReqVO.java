package cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity;

import cn.econets.blossom.module.promotion.controller.admin.combination.vo.product.CombinationProductBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - Group buying activity update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CombinationActivityUpdateReqVO extends CombinationActivityBaseVO {

    @Schema(description = "Activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "22901")
    @NotNull(message = "Activity number cannot be empty")
    private Long id;

    @Schema(description = "Group buy product", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    private List<CombinationProductBaseVO> products;

}
