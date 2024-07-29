package cn.econets.blossom.module.trade.service.price.calculator;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.member.api.config.MemberConfigApi;
import cn.econets.blossom.module.member.api.config.dto.MemberConfigRespDTO;
import cn.econets.blossom.module.member.api.user.MemberUserApi;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.promotion.enums.common.PromotionTypeEnum;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.filterList;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.PRICE_CALCULATE_PAY_PRICE_ILLEGAL;

/**
 * Use points {@link TradePriceCalculator} Implementation class
 *
 */
@Component
@Order(TradePriceCalculator.ORDER_POINT_USE)
@Slf4j
public class TradePointUsePriceCalculator implements TradePriceCalculator {

    @Resource
    private MemberConfigApi memberConfigApi;
    @Resource
    private MemberUserApi memberUserApi;

    @Override
    public void calculate(TradePriceCalculateReqBO param, TradePriceCalculateRespBO result) {
        // Default points used are 0
        result.setUsePoint(0);
        // 1.1 Check whether to use points
        if (!BooleanUtil.isTrue(param.getPointStatus())) {
            result.setUsePoint(0);
            return;
        }
        // 1.2 Check whether the points deduction is enabled
        MemberConfigRespDTO config = memberConfigApi.getConfig();
        if (!isDeductPointEnable(config)) {
            return;
        }
        // 1.3 Check user points balance
        MemberUserRespDTO user = memberUserApi.getUser(param.getUserId());
        if (user.getPoint() == null || user.getPoint() <= 0) {
            return;
        }

        // 2.1 Calculate the points discount amount
        int pointPrice = calculatePointPrice(config, user.getPoint(), result);
        // 2.2 Calculate the apportioned points、Deduction amount
        List<TradePriceCalculateRespBO.OrderItem> orderItems = filterList(result.getItems(), TradePriceCalculateRespBO.OrderItem::getSelected);
        List<Integer> dividePointPrices = TradePriceCalculatorHelper.dividePrice(orderItems, pointPrice);
        List<Integer> divideUsePoints = TradePriceCalculatorHelper.dividePrice(orderItems, result.getUsePoint());

        // 3.1 Record discount details
        TradePriceCalculatorHelper.addPromotion(result, orderItems,
                param.getUserId(), "Points deduction", PromotionTypeEnum.POINT.getType(),
                StrUtil.format("Points deduction：Province {} Yuan", TradePriceCalculatorHelper.formatPrice(pointPrice)),
                dividePointPrices);
        // 3.2 Update SKU Discount amount
        for (int i = 0; i < orderItems.size(); i++) {
            TradePriceCalculateRespBO.OrderItem orderItem = orderItems.get(i);
            orderItem.setPointPrice(dividePointPrices.get(i));
            orderItem.setUsePoint(divideUsePoints.get(i));
            TradePriceCalculatorHelper.recountPayPrice(orderItem);
        }
        TradePriceCalculatorHelper.recountAllPrice(result);
    }

    private boolean isDeductPointEnable(MemberConfigRespDTO config) {
        return config != null &&
                BooleanUtil.isTrue(config.getPointTradeDeductEnable()) &&  // Is the points function enabled?
                config.getPointTradeDeductUnitPrice() != null && config.getPointTradeDeductUnitPrice() > 0; // Is there any configuration?：1 How many points can be deducted from the points
    }

    private Integer calculatePointPrice(MemberConfigRespDTO config, Integer usePoint, TradePriceCalculateRespBO result) {
        // The maximum number of points that can be used for each order
        if (config.getPointTradeDeductMaxPrice() != null && config.getPointTradeDeductMaxPrice() > 0) {
            usePoint = Math.min(usePoint, config.getPointTradeDeductMaxPrice());
        }
        // TODO This should be，Deduction until only left 0.01；
        // Points discount amount（Points）
        int pointPrice = usePoint * config.getPointTradeDeductUnitPrice();
        if (result.getPrice().getPayPrice() <= pointPrice) {
            // Prohibited 0 Yuangou
            throw exception(PRICE_CALCULATE_PAY_PRICE_ILLEGAL);
        }
//        // Allow0 Yuangou!!!：When the user has a lot of points，The amount that can be deducted by points must be greater than the payment amount，At this time, you need to reverse calculate how many points to use based on the payment amount
//        if (result.getPrice().getPayPrice() < pointPrice) {
//            pointPrice = result.getPrice().getPayPrice();
//            // The points that need to be deducted for reverse calculation
//            usePoint = NumberUtil.toBigDecimal(pointPrice)
//                    .divide(NumberUtil.toBigDecimal(config.getPointTradeDeductUnitPrice()), 0, RoundingMode.HALF_UP)
//                    .intValue();
//        }
        // Record the points used
        result.setUsePoint(usePoint);
        return pointPrice;
    }

}
