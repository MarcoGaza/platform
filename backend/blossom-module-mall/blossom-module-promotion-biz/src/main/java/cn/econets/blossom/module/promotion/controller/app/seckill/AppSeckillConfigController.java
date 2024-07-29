package cn.econets.blossom.module.promotion.controller.app.seckill;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.promotion.controller.app.seckill.vo.config.AppSeckillConfigRespVO;
import cn.econets.blossom.module.promotion.convert.seckill.seckillconfig.SeckillConfigConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.seckill.SeckillConfigDO;
import cn.econets.blossom.module.promotion.service.seckill.SeckillConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "User App - Second sale time period")
@RestController
@RequestMapping("/promotion/seckill-config")
@Validated
public class AppSeckillConfigController {
    @Resource
    private SeckillConfigService configService;

    @GetMapping("/list")
    @Operation(summary = "Get the list of flash sale time periods")
    public CommonResult<List<AppSeckillConfigRespVO>> getSeckillConfigList() {
        List<SeckillConfigDO> list = configService.getSeckillConfigListByStatus(CommonStatusEnum.ENABLE.getStatus());
        return success(SeckillConfigConvert.INSTANCE.convertList2(list));
    }

}
