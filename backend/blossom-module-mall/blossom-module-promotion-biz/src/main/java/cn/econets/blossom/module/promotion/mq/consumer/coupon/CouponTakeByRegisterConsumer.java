package cn.econets.blossom.module.promotion.mq.consumer.coupon;

import cn.econets.blossom.module.member.message.user.MemberUserCreateMessage;
import cn.econets.blossom.module.promotion.service.coupon.CouponService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * When the user registers，Consumers who send coupons，Base {@link MemberUserCreateMessage} Message
 *
 */
@Component
@Slf4j
public class CouponTakeByRegisterConsumer {

    @Resource
    private CouponService couponService;

    @EventListener
    @Async // Spring Event Default is Producer Sent thread，Passed @Async Implementing Asynchronous Mode
    public void onMessage(MemberUserCreateMessage message) {
        log.info("[onMessage][Message content({})]", message);
        couponService.takeCouponByRegister(message.getUserId());
    }

}
