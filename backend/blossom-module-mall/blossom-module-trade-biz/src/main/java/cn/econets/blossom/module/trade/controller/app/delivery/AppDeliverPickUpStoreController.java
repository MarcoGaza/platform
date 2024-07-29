package cn.econets.blossom.module.trade.controller.app.delivery;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.module.trade.controller.app.delivery.vo.pickup.AppDeliveryPickUpStoreRespVO;
import cn.econets.blossom.module.trade.convert.delivery.DeliveryPickUpStoreConvert;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryPickUpStoreDO;
import cn.econets.blossom.module.trade.service.delivery.DeliveryPickUpStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "User App - Self-pickup store")
@RestController
@RequestMapping("/trade/delivery/pick-up-store")
@Validated
public class AppDeliverPickUpStoreController {

    @Resource
    private DeliveryPickUpStoreService deliveryPickUpStoreService;

    @GetMapping("/list")
    @Operation(summary = "Get the list of self-pickup stores")
    @Parameters({
            @Parameter(name = "latitude", description = "Accuracy", example = "110"),
            @Parameter(name = "longitude", description = "Latitude", example = "120")
    })
    public CommonResult<List<AppDeliveryPickUpStoreRespVO>> getDeliveryPickUpStoreList(
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "longitude", required = false) Double longitude) {
        List<DeliveryPickUpStoreDO> list = deliveryPickUpStoreService.getDeliveryPickUpStoreListByStatus(
                CommonStatusEnum.ENABLE.getStatus());
        return success(DeliveryPickUpStoreConvert.INSTANCE.convertList(list, latitude, longitude));
    }

    @GetMapping("/get")
    @Operation(summary = "Get the self-pickup store")
    @Parameter(name = "id", description = "Store Number")
    public CommonResult<AppDeliveryPickUpStoreRespVO> getOrder(@RequestParam("id") Long id) {
        DeliveryPickUpStoreDO store = deliveryPickUpStoreService.getDeliveryPickUpStore(id);
        return success(DeliveryPickUpStoreConvert.INSTANCE.convert03(store));
    }

}
