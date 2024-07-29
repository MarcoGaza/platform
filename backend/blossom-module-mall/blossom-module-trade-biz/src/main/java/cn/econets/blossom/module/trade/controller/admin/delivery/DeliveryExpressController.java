package cn.econets.blossom.module.trade.controller.admin.delivery;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.excel.core.util.ExcelUtils;
import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.express.*;
import cn.econets.blossom.module.trade.convert.delivery.DeliveryExpressConvert;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryExpressDO;
import cn.econets.blossom.module.trade.service.delivery.DeliveryExpressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;

@Tag(name = "Management Backend - Express Delivery Company")
@RestController
@RequestMapping("/trade/delivery/express")
@Validated
public class DeliveryExpressController {

    @Resource
    private DeliveryExpressService deliveryExpressService;

    @PostMapping("/create")
    @Operation(summary = "Create a courier company")
    @PreAuthorize("@ss.hasPermission('trade:delivery:express:create')")
    public CommonResult<Long> createDeliveryExpress(@Valid @RequestBody DeliveryExpressCreateReqVO createReqVO) {
        return success(deliveryExpressService.createDeliveryExpress(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update courier company")
    @PreAuthorize("@ss.hasPermission('trade:delivery:express:update')")
    public CommonResult<Boolean> updateDeliveryExpress(@Valid @RequestBody DeliveryExpressUpdateReqVO updateReqVO) {
        deliveryExpressService.updateDeliveryExpress(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete courier company")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('trade:delivery:express:delete')")
    public CommonResult<Boolean> deleteDeliveryExpress(@RequestParam("id") Long id) {
        deliveryExpressService.deleteDeliveryExpress(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get the courier company")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('trade:delivery:express:query')")
    public CommonResult<DeliveryExpressRespVO> getDeliveryExpress(@RequestParam("id") Long id) {
        DeliveryExpressDO deliveryExpress = deliveryExpressService.getDeliveryExpress(id);
        return success(DeliveryExpressConvert.INSTANCE.convert(deliveryExpress));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "Get a simplified list of courier companies", description = "Mainly used for front-end drop-down options")
    public CommonResult<List<DeliveryExpressSimpleRespVO>> getSimpleDeliveryExpressList() {
        List<DeliveryExpressDO> list = deliveryExpressService.getDeliveryExpressListByStatus(CommonStatusEnum.ENABLE.getStatus());
        return success(DeliveryExpressConvert.INSTANCE.convertList1(list));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the courier company page")
    @PreAuthorize("@ss.hasPermission('trade:delivery:express:query')")
    public CommonResult<PageResult<DeliveryExpressRespVO>> getDeliveryExpressPage(@Valid DeliveryExpressPageReqVO pageVO) {
        PageResult<DeliveryExpressDO> pageResult = deliveryExpressService.getDeliveryExpressPage(pageVO);
        return success(DeliveryExpressConvert.INSTANCE.convertPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "Export courier company Excel")
    @PreAuthorize("@ss.hasPermission('trade:delivery:express:export')")
    @OperateLog(type = EXPORT)
    public void exportDeliveryExpressExcel(@Valid DeliveryExpressExportReqVO exportReqVO,
              HttpServletResponse response) throws IOException {
        List<DeliveryExpressDO> list = deliveryExpressService.getDeliveryExpressList(exportReqVO);
        // Export Excel
        List<DeliveryExpressExcelVO> dataList = DeliveryExpressConvert.INSTANCE.convertList02(list);
        ExcelUtils.write(response, "Express Delivery Company.xls", "Data", DeliveryExpressExcelVO.class, dataList);
    }
}
