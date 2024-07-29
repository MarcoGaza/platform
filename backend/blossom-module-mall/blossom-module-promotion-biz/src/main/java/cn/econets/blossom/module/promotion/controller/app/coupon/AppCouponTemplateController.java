package cn.econets.blossom.module.promotion.controller.app.coupon;

import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.common.util.object.ObjectUtils;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.promotion.controller.app.coupon.vo.template.AppCouponTemplatePageReqVO;
import cn.econets.blossom.module.promotion.controller.app.coupon.vo.template.AppCouponTemplateRespVO;
import cn.econets.blossom.module.promotion.convert.coupon.CouponTemplateConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.coupon.CouponTemplateDO;
import cn.econets.blossom.module.promotion.enums.common.PromotionProductScopeEnum;
import cn.econets.blossom.module.promotion.enums.coupon.CouponTakeTypeEnum;
import cn.econets.blossom.module.promotion.service.coupon.CouponService;
import cn.econets.blossom.module.promotion.service.coupon.CouponTemplateService;
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
import java.util.*;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static java.util.Collections.singletonList;

@Tag(name = "User App - Coupon Template")
@RestController
@RequestMapping("/promotion/coupon-template")
@Validated
public class AppCouponTemplateController {

    @Resource
    private CouponTemplateService couponTemplateService;
    @Resource
    private CouponService couponService;

    @Resource
    private ProductSpuApi productSpuApi;

    @GetMapping("/get")
    @Operation(summary = "Get coupon template")
    @Parameter(name = "id", description = "Coupon template number", required = true, example = "1024")
    public CommonResult<AppCouponTemplateRespVO> getCouponTemplate(Long id) {
        CouponTemplateDO template = couponTemplateService.getCouponTemplate(id);
        if (template == null) {
            return success(null);
        }
        // Whether the processing is available for collection
        Map<Long, Boolean> canCanTakeMap = couponService.getUserCanCanTakeMap(getLoginUserId(), singletonList(template));
        return success(BeanUtils.toBean(template, AppCouponTemplateRespVO.class)
                .setCanTake(canCanTakeMap.get(template.getId())));
    }

    @GetMapping("/list")
    @Operation(summary = "Get the coupon template list")
    @Parameters({
            @Parameter(name = "spuId", description = "Products SPU Number"), // Currently mainly used for product details
            @Parameter(name = "productScope", description = "Use type"),
            @Parameter(name = "count", description = "Quantity", required = true)
    })
    public CommonResult<List<AppCouponTemplateRespVO>> getCouponTemplateList(
            @RequestParam(value = "spuId", required = false) Long spuId,
            @RequestParam(value = "productScope", required = false) Integer productScope,
            @RequestParam(value = "count", required = false, defaultValue = "10") Integer count) {
        // 1.1 Processing query conditions：Product range number
        Long productScopeValue = getProductScopeValue(productScope, spuId);
        // 1.2 Processing query conditions：How to receive = Receive directly
        List<Integer> canTakeTypes = singletonList(CouponTakeTypeEnum.USER.getValue());

        // 2. Query
        List<CouponTemplateDO> list = couponTemplateService.getCouponTemplateList(canTakeTypes, productScope,
                productScopeValue, count);

        // 3.1 Number of items to be collected
        Map<Long, Boolean> canCanTakeMap = couponService.getUserCanCanTakeMap(getLoginUserId(), list);
        // 3.2 Splicing returns
        return success(CouponTemplateConvert.INSTANCE.convertAppList(list, canCanTakeMap));
    }

    @GetMapping("/list-by-ids")
    @Operation(summary = "Get the coupon template list")
    @Parameter(name = "ids", description = "Coupon template number list")
    public CommonResult<List<AppCouponTemplateRespVO>> getCouponTemplateList(
            @RequestParam(value = "ids", required = false) Set<Long> ids) {
        // 1. Query
        List<CouponTemplateDO> list = couponTemplateService.getCouponTemplateList(ids);

        // 2.1 Number of items to be collected
        Map<Long, Boolean> canCanTakeMap = couponService.getUserCanCanTakeMap(getLoginUserId(), list);
        // 2.2 Splicing returns
        return success(CouponTemplateConvert.INSTANCE.convertAppList(list, canCanTakeMap));
    }

    @GetMapping("/page")
    @Operation(summary = "Get coupon template page")
    public CommonResult<PageResult<AppCouponTemplateRespVO>> getCouponTemplatePage(AppCouponTemplatePageReqVO pageReqVO) {
        // 1.1 Processing query conditions：Product range number
        Long productScopeValue = getProductScopeValue(pageReqVO.getProductScope(), pageReqVO.getSpuId());
        // 1.2 Processing query conditions：How to receive = Receive directly
        List<Integer> canTakeTypes = singletonList(CouponTakeTypeEnum.USER.getValue());

        // 2. Paged query
        PageResult<CouponTemplateDO> pageResult = couponTemplateService.getCouponTemplatePage(
                CouponTemplateConvert.INSTANCE.convert(pageReqVO, canTakeTypes, pageReqVO.getProductScope(), productScopeValue));

        // 3.1 Number of items to be collected
        Map<Long, Boolean> canCanTakeMap = couponService.getUserCanCanTakeMap(getLoginUserId(), pageResult.getList());
        // 3.2 Splicing returns
        return success(CouponTemplateConvert.INSTANCE.convertAppPage(pageResult, canCanTakeMap));
    }

    /**
     * Get the product usage range number
     *
     * @param productScope Product Range
     * @param spuId        Products SPU Number
     * @return Product range number
     */
    private Long getProductScopeValue(Integer productScope, Long spuId) {
        // General Coupon：No product range
        if (ObjectUtils.equalsAny(productScope, PromotionProductScopeEnum.ALL.getScope(), null)) {
            return null;
        }
        // Category coupons：Query the product category number
        if (Objects.equals(productScope, PromotionProductScopeEnum.CATEGORY.getScope()) && spuId != null) {
            ProductSpuRespDTO spu = productSpuApi.getSpu(spuId);
            return spu != null ? spu.getCategoryId() : null;
        }
        // Commodity coupon：Return directly
        return spuId;
    }

}
