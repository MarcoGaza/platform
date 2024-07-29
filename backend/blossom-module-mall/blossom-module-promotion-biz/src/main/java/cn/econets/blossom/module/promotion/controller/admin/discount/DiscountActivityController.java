package cn.econets.blossom.module.promotion.controller.admin.discount;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.promotion.controller.admin.discount.vo.*;
import cn.econets.blossom.module.promotion.convert.discount.DiscountActivityConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.discount.DiscountActivityDO;
import cn.econets.blossom.module.promotion.dal.dataobject.discount.DiscountProductDO;
import cn.econets.blossom.module.promotion.service.discount.DiscountActivityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;

@Tag(name = "Management Backend - Limited time discount event")
@RestController
@RequestMapping("/promotion/discount-activity")
@Validated
public class DiscountActivityController {

    @Resource
    private DiscountActivityService discountActivityService;

    @Resource
    private ProductSpuApi productSpuApi;

    @PostMapping("/create")
    @Operation(summary = "Create a limited-time discount event")
    @PreAuthorize("@ss.hasPermission('promotion:discount-activity:create')")
    public CommonResult<Long> createDiscountActivity(@Valid @RequestBody DiscountActivityCreateReqVO createReqVO) {
        return success(discountActivityService.createDiscountActivity(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "Update limited time discount event")
    @PreAuthorize("@ss.hasPermission('promotion:discount-activity:update')")
    public CommonResult<Boolean> updateDiscountActivity(@Valid @RequestBody DiscountActivityUpdateReqVO updateReqVO) {
        discountActivityService.updateDiscountActivity(updateReqVO);
        return success(true);
    }

    @PutMapping("/close")
    @Operation(summary = "Close the limited time discount event")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:discount-activity:close')")
    public CommonResult<Boolean> closeRewardActivity(@RequestParam("id") Long id) {
        discountActivityService.closeDiscountActivity(id);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "Delete limited-time discount event")
    @Parameter(name = "id", description = "Number", required = true)
    @PreAuthorize("@ss.hasPermission('promotion:discount-activity:delete')")
    public CommonResult<Boolean> deleteDiscountActivity(@RequestParam("id") Long id) {
        discountActivityService.deleteDiscountActivity(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "Get a limited time discount")
    @Parameter(name = "id", description = "Number", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('promotion:discount-activity:query')")
    public CommonResult<DiscountActivityDetailRespVO> getDiscountActivity(@RequestParam("id") Long id) {
        DiscountActivityDO discountActivity = discountActivityService.getDiscountActivity(id);
        if (discountActivity == null) {
            return success(null);
        }
        // Joining results
        List<DiscountProductDO> discountProducts = discountActivityService.getDiscountProductsByActivityId(id);
        return success(DiscountActivityConvert.INSTANCE.convert(discountActivity, discountProducts));
    }

    @GetMapping("/page")
    @Operation(summary = "Get the limited-time discount event page")
    @PreAuthorize("@ss.hasPermission('promotion:discount-activity:query')")
    public CommonResult<PageResult<DiscountActivityRespVO>> getDiscountActivityPage(@Valid DiscountActivityPageReqVO pageVO) {
        PageResult<DiscountActivityDO> pageResult = discountActivityService.getDiscountActivityPage(pageVO);

        if (CollUtil.isEmpty(pageResult.getList())) { // TODO Blank lines in methods，The purpose is to divide the code into blocks，It can be clearer；So the space above can be omitted，After the following judgment，Space，It's better to add it；Similar ones spuList、And the following convert
            return success(PageResult.empty(pageResult.getTotal()));
        }
        // Splicing data
        List<DiscountProductDO> products = discountActivityService.getDiscountProductsByActivityId(
                convertSet(pageResult.getList(), DiscountActivityDO::getId));

        List<ProductSpuRespDTO> spuList = productSpuApi.getSpuList(
                convertSet(products, DiscountProductDO::getSpuId));

        return success(DiscountActivityConvert.INSTANCE.convertPage(pageResult, products, spuList));
    }

}
