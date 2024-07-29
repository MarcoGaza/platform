package cn.econets.blossom.module.promotion.controller.admin.seckill.vo.activity;

import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.product.SeckillProductRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Schema(description = "Management Backend - Details of flash sale Response VO")
@Data
@ToString(callSuper = true)
public class SeckillActivityDetailRespVO extends SeckillActivityBaseVO{

    @Schema(description = "Second-sale eventid", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long id;

    @Schema(description = "Second-selling products", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SeckillProductRespVO> products;

}
