package cn.econets.blossom.module.trade.controller.admin.delivery;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.trade.controller.admin.delivery.vo.expresstemplate.*;
import cn.econets.blossom.module.trade.convert.delivery.DeliveryExpressTemplateConvert;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryExpressTemplateDO;
import cn.econets.blossom.module.trade.service.delivery.DeliveryExpressTemplateService;
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

@Tag(name = "Management Backend - Express delivery fee template")
@RestController
@RequestMapping("/trade/delivery/express-template")
@Validated
public class DeliveryExpressTemplateController {

    @Resource
    private DeliveryExpressTemplateService deliveryExpressTemplateService;

    @PostMapping("/create")
    @Operation(summary = "Create a courier freight template")
    @PreAuthorize("@ss.hasPermission('trade:delivery:express-template:create')")
    public CommonResult<Long> createDeliveryExpressTemplate(@Valid @RequestBody DeliveryExpressTemplateCreateReqVO createReqVO) {
        return success(deliveryExpressTemplateService.createDeliveryExpressTemplate(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update express delivery fee template")
    @PreAuthorize("@ss.hasPermission('trade:delivery:express-template:update')")
    public CommonResult<Boolean> updateDeliveryExpressTemplate(@Valid @RequestBody DeliveryExpressTemplateUpdateReqVO updateReqVO) {
        deliveryExpressTemplateService.updateDeliveryExpressTemplate(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete express delivery template")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('trade:delivery:express-template:delete')")
    public CommonResult<Boolean> deleteDeliveryExpressTemplate(@RequestParam("id") Long id) {
        deliveryExpressTemplateService.deleteDeliveryExpressTemplate(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get the express delivery fee template")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('trade:delivery:express-template:query')")
    public CommonResult<DeliveryExpressTemplateDetailRespVO> getDeliveryExpressTemplate(@RequestParam("id") Long id) {
        return success(deliveryExpressTemplateService.getDeliveryExpressTemplate(id));
    }

    @GetMapping("/list")
    @Operation(summary = "Get the express delivery fee template list")
    @Parameter(name = "ids", description = "Numbered list", required = true, example = "1024,2048")
    @PreAuthorize("@ss.hasPermission('trade:delivery:express-template:query')")
    public CommonResult<List<DeliveryExpressTemplateRespVO>> getDeliveryExpressTemplateList(@RequestParam("ids") Collection<Long> ids) {
        List<DeliveryExpressTemplateDO> list = deliveryExpressTemplateService.getDeliveryExpressTemplateList(ids);
        return success(DeliveryExpressTemplateConvert.INSTANCE.convertList(list));
    }

    @GetMapping("/list-all-simple")
    @Operation(summary = "Get the simplified information list of the express template", description = "Mainly used for front-end drop-down options")
    public CommonResult<List<DeliveryExpressTemplateSimpleRespVO>> getSimpleTemplateList() {
        // Get the shipping template list，As long as it is turned on
        List<DeliveryExpressTemplateDO> list = deliveryExpressTemplateService.getDeliveryExpressTemplateList();
        // After sorting，Return to the front end
        return success(DeliveryExpressTemplateConvert.INSTANCE.convertList1(list));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the express delivery template page")
    @PreAuthorize("@ss.hasPermission('trade:delivery:express-template:query')")
    public CommonResult<PageResult<DeliveryExpressTemplateRespVO>> getDeliveryExpressTemplatePage(@Valid DeliveryExpressTemplatePageReqVO pageVO) {
        PageResult<DeliveryExpressTemplateDO> pageResult = deliveryExpressTemplateService.getDeliveryExpressTemplatePage(pageVO);
        return success(DeliveryExpressTemplateConvert.INSTANCE.convertPage(pageResult));
    }

}
