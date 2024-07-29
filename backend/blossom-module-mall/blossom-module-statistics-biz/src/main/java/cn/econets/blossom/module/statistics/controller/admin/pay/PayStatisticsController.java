package cn.econets.blossom.module.statistics.controller.admin.pay;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.statistics.controller.admin.pay.vo.PaySummaryRespVO;
import cn.econets.blossom.module.statistics.convert.pay.PayStatisticsConvert;
import cn.econets.blossom.module.statistics.service.pay.PayWalletStatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Payment Statistics")
@RestController
@RequestMapping("/statistics/pay")
@Validated
@Slf4j
public class PayStatisticsController {

    @Resource
    private PayWalletStatisticsService payWalletStatisticsService;

    @GetMapping("/summary")
    @Operation(summary = "Get the recharge amount")
    public CommonResult<PaySummaryRespVO> getWalletRechargePrice() {
        Integer rechargePrice = payWalletStatisticsService.getRechargePriceSummary();
        return success(PayStatisticsConvert.INSTANCE.convert(rechargePrice));
    }

}
