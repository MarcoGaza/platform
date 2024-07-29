package cn.econets.blossom.module.promotion.controller.admin.seckill.vo.product;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Second-sale participating products Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SeckillProductRespVO extends SeckillProductBaseVO {

    @Schema(description = "Second sale product number", requiredMode = Schema.RequiredMode.REQUIRED, example = "256")
    private Long id;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
