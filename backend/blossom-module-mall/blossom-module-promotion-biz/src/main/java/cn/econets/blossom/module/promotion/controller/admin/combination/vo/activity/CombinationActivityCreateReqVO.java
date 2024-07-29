package cn.econets.blossom.module.promotion.controller.admin.combination.vo.activity;

import cn.econets.blossom.module.promotion.controller.admin.combination.vo.product.CombinationProductBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import java.util.List;

@Schema(description = "Management Backend - Create a group buying activity Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CombinationActivityCreateReqVO extends CombinationActivityBaseVO {

    @Schema(description = "Group buy product", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    private List<CombinationProductBaseVO> products;

}
