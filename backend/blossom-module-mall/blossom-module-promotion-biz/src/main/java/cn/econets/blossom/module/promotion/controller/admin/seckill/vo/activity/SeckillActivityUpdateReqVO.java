package cn.econets.blossom.module.promotion.controller.admin.seckill.vo.activity;

import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.product.SeckillProductBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "Management Backend - Flash sale update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SeckillActivityUpdateReqVO extends SeckillActivityBaseVO {

    @Schema(description = "Second-sale eventid", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Second-selling products", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SeckillProductBaseVO> products;

}
