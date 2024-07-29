package cn.econets.blossom.module.promotion.controller.app.seckill.vo.activity;

import cn.econets.blossom.module.promotion.controller.app.seckill.vo.config.AppSeckillConfigRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(description = "User App - Current flash sale activity Response VO")
@Data
public class AppSeckillActivityNowRespVO {

    @Schema(description = "Second sale time period", requiredMode = Schema.RequiredMode.REQUIRED)
    private AppSeckillConfigRespVO config;

    @Schema(description = "Second-sale activity array", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<AppSeckillActivityRespVO> activities;

}
