package cn.econets.blossom.module.trade.controller.admin.delivery;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.pickup.*;
import cn.econets.blossom.module.trade.convert.delivery.DeliveryPickUpStoreConvert;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryPickUpStoreDO;
import cn.econets.blossom.module.trade.service.delivery.DeliveryPickUpStoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;

@Tag(name = "Management Backend - Self-pickup store")
@RestController
@RequestMapping("/trade/delivery/pick-up-store")
@Validated
public class DeliveryPickUpStoreController {

    @Resource
    private DeliveryPickUpStoreService deliveryPickUpStoreService;

    @PostMapping("/create")
    @Operation(summary = "Create a self-pickup store")
    @PreAuthorize("@ss.hasPermission('trade:delivery:pick-up-store:create')")
    public CommonResult<Long> createDeliveryPickUpStore(@Valid @RequestBody DeliveryPickUpStoreCreateReqVO createReqVO) {
        return success(deliveryPickUpStoreService.createDeliveryPickUpStore(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update self-pickup stores")
    @PreAuthorize("@ss.hasPermission('trade:delivery:pick-up-store:update')")
    public CommonResult<Boolean> updateDeliveryPickUpStore(@Valid @RequestBody DeliveryPickUpStoreUpdateReqVO updateReqVO) {
        deliveryPickUpStoreService.updateDeliveryPickUpStore(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete the self-pickup store")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('trade:delivery:pick-up-store:delete')")
    public CommonResult<Boolean> deleteDeliveryPickUpStore(@RequestParam("id") Long id) {
        deliveryPickUpStoreService.deleteDeliveryPickUpStore(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get the self-pickup store")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('trade:delivery:pick-up-store:query')")
    public CommonResult<DeliveryPickUpStoreRespVO> getDeliveryPickUpStore(@RequestParam("id") Long id) {
        DeliveryPickUpStoreDO deliveryPickUpStore = deliveryPickUpStoreService.getDeliveryPickUpStore(id);
        return success(DeliveryPickUpStoreConvert.INSTANCE.convert(deliveryPickUpStore));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "Get a simplified list of self-pickup stores")
    public CommonResult<List<DeliveryPickUpStoreSimpleRespVO>> getSimpleDeliveryPickUpStoreList() {
        List<DeliveryPickUpStoreDO> list = deliveryPickUpStoreService.getDeliveryPickUpStoreListByStatus(
                CommonStatusEnum.ENABLE.getStatus());
        return success(DeliveryPickUpStoreConvert.INSTANCE.convertList1(list));
    }

    @GetMapping("/list")
    @Operation(summary = "Get the list of self-pickup stores")
    @Parameter(name = "ids", description = "Numbered list", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('trade:delivery:pick-up-store:query')")
    public CommonResult<List<DeliveryPickUpStoreRespVO>> getDeliveryPickUpStoreList(@RequestParam("ids") Collection<Long> ids) {
        List<DeliveryPickUpStoreDO> list = deliveryPickUpStoreService.getDeliveryPickUpStoreList(ids);
        return success(DeliveryPickUpStoreConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the self-pickup store page")
    @PreAuthorize("@ss.hasPermission('trade:delivery:pick-up-store:query')")
    public CommonResult<PageResult<DeliveryPickUpStoreRespVO>> getDeliveryPickUpStorePage(@Valid DeliveryPickUpStorePageReqVO pageVO) {
        PageResult<DeliveryPickUpStoreDO> pageResult = deliveryPickUpStoreService.getDeliveryPickUpStorePage(pageVO);
        return success(DeliveryPickUpStoreConvert.INSTANCE.convertPage(pageResult));
    }

}
