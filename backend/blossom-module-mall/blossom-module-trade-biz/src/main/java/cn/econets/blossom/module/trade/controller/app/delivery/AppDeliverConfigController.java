package cn.econets.blossom.module.trade.controller.app.delivery;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.trade.controller.app.delivery.vo.config.AppDeliveryConfigRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "User App - Delivery Configuration")
@RestController
@RequestMapping("/trade/delivery/config")
@Validated
public class AppDeliverConfigController {

    // TODO ：Kill them later here，Merge to AppTradeConfigController Medium
    @GetMapping("/get")
    @Operation(summary = "Get delivery configuration")
    public CommonResult<AppDeliveryConfigRespVO> getDeliveryConfig() {
        return success(new AppDeliveryConfigRespVO().setPickUpEnable(true).setTencentLbsKey("123456"));
    }

}
