package cn.econets.blossom.module.promotion.controller.admin.seckill.vo.activity;


import cn.econets.blossom.module.promotion.controller.admin.seckill.vo.product.SeckillProductBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "Management Backend - Create a flash sale event Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SeckillActivityCreateReqVO extends SeckillActivityBaseVO {

    @Schema(description = "Second-selling products", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<SeckillProductBaseVO> products;

}
