package cn.econets.blossom.module.promotion.controller.app.seckill.vo.activity;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "User App - Product Reviews Pagination Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppSeckillActivityPageReqVO extends PageParam {

    @Schema(description = "Second kill configuration number", example = "1024")
    private Long configId;

}
