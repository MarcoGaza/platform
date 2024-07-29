package cn.econets.blossom.module.promotion.controller.app.coupon;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.pojo.CommonResult;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.framework.security.core.annotations.PreAuthenticated;
import cn.econets.blossom.module.promotion.controller.app.coupon.vo.coupon.*;
import cn.econets.blossom.module.promotion.convert.coupon.CouponConvert;
import cn.econets.blossom.module.promotion.dal.dataobject.coupon.CouponDO;
import cn.econets.blossom.module.promotion.dal.dataobject.coupon.CouponTemplateDO;
import cn.econets.blossom.module.promotion.enums.coupon.CouponTakeTypeEnum;
import cn.econets.blossom.module.promotion.service.coupon.CouponService;
import cn.econets.blossom.module.promotion.service.coupon.CouponTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

import static cn.econets.blossom.framework.common.pojo.CommonResult.success;
import static cn.econets.blossom.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "User App - Coupon")
@RestController
@RequestMapping("/promotion/coupon")
@Validated
public class AppCouponController {

    @Resource
    private CouponService couponService;
    @Resource
    private CouponTemplateService couponTemplateService;

    @PostMapping("/take")
    @Operation(summary = "Get coupons")
    @Parameter(name = "templateId", description = "Coupon template number", required = true, example = "1024")
    @PreAuthenticated
    public CommonResult<Boolean> takeCoupon(@Valid @RequestBody AppCouponTakeReqVO reqVO) {
        // 1. Get coupons
        Long userId = getLoginUserId();
        couponService.takeCoupon(reqVO.getTemplateId(), CollUtil.newHashSet(userId), CouponTakeTypeEnum.USER);

        // 2. Check whether you can continue to receive
        CouponTemplateDO couponTemplate = couponTemplateService.getCouponTemplate(reqVO.getTemplateId());
        boolean canTakeAgain = true;
        if (couponTemplate.getTakeLimitCount() != null && couponTemplate.getTakeLimitCount() > 0) {
            Integer takeCount = couponService.getTakeCount(reqVO.getTemplateId(), userId);
            canTakeAgain = takeCount < couponTemplate.getTakeLimitCount();
        }
        return success(canTakeAgain);
    }

    @GetMapping("/match-list")
    @Operation(summary = "Get a list of coupons matching the specified product", description = "Used for ordering page，Show coupon list")
    public CommonResult<List<AppCouponMatchRespVO>> getMatchCouponList(AppCouponMatchReqVO matchReqVO) {
        // todo: Optimization：Discount amount in reverse order
        List<CouponDO> list = couponService.getMatchCouponList(getLoginUserId(), matchReqVO);
        return success(BeanUtils.toBean(list, AppCouponMatchRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "My coupon list")
    @PreAuthenticated
    public CommonResult<PageResult<AppCouponRespVO>> getCouponPage(AppCouponPageReqVO pageReqVO) {
        PageResult<CouponDO> pageResult = couponService.getCouponPage(
                CouponConvert.INSTANCE.convert(pageReqVO, Collections.singleton(getLoginUserId())));
        return success(BeanUtils.toBean(pageResult, AppCouponRespVO.class));
    }

    @GetMapping("/get")
    @Operation(summary = "Get coupons")
    @Parameter(name = "id", description = "Coupon number", required = true, example = "1024")
    @PreAuthenticated
    public CommonResult<AppCouponRespVO> getCoupon(@RequestParam("id") Long id) {
        CouponDO coupon = couponService.getCoupon(getLoginUserId(), id);
        return success(BeanUtils.toBean(coupon, AppCouponRespVO.class));
    }

    @GetMapping(value = "/get-unused-count")
    @Operation(summary = "Get the number of unused coupons")
    @PreAuthenticated
    public CommonResult<Long> getUnusedCouponCount() {
        return success(couponService.getUnusedCouponCount(getLoginUserId()));
    }

}
