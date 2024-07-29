package cn.econets.blossom.module.promotion.job.coupon;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.quartz.core.handler.JobHandler;
import cn.econets.blossom.framework.tenant.core.job.TenantJob;
import cn.econets.blossom.module.promotion.service.coupon.CouponService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

// TODO Configure one Job
/**
 * Coupon expired Job
 *
 */
@Component
public class CouponExpireJob implements JobHandler {

    @Resource
    private CouponService couponService;

    @Override
    @TenantJob
    public String execute(String param) {
        int count = couponService.expireCoupon();
        return StrUtil.format("Expired coupon {} pcs", count);
    }

}
