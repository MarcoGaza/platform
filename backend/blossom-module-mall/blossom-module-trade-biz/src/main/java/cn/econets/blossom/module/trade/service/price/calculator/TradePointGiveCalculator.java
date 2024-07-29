package cn.econets.blossom.module.trade.service.price.calculator;

import cn.hutool.core.util.BooleanUtil;
import cn.econets.blossom.framework.common.util.number.MoneyUtils;
import cn.econets.blossom.module.member.api.config.MemberConfigApi;
import cn.econets.blossom.module.member.api.config.dto.MemberConfigRespDTO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.filterList;

/**
 * Give points {@link TradePriceCalculator} Implementation class
 *
 */
@Component
@Order(TradePriceCalculator.ORDER_POINT_GIVE)
@Slf4j
public class TradePointGiveCalculator implements TradePriceCalculator {

    @Resource
    private MemberConfigApi memberConfigApi;

    @Override
    public void calculate(TradePriceCalculateReqBO param, TradePriceCalculateRespBO result) {
        // 1.1 Check whether the points function is enabled
        int givePointPerYuan = Optional.ofNullable(memberConfigApi.getConfig())
                .filter(config -> BooleanUtil.isTrue(config.getPointTradeDeductEnable()))
                .map(MemberConfigRespDTO::getPointTradeGivePoint)
                .orElse(0);
        if (givePointPerYuan <= 0) {
            return;
        }
        // 1.2 Verify payment amount
        if (result.getPrice().getPayPrice() <= 0) {
            return;
        }

        // 2.1 Calculate free points
        int givePoint = MoneyUtils.calculateRatePriceFloor(result.getPrice().getPayPrice(), (double) givePointPerYuan);
        // 2.2 Calculate the allocated bonus points
        List<TradePriceCalculateRespBO.OrderItem> orderItems = filterList(result.getItems(), TradePriceCalculateRespBO.OrderItem::getSelected);
        List<Integer> dividePoints = TradePriceCalculatorHelper.dividePrice(orderItems, givePoint);

        // 3.2 Update SKU Send points
        for (int i = 0; i < orderItems.size(); i++) {
            TradePriceCalculateRespBO.OrderItem orderItem = orderItems.get(i);
            // The product may have given away points，So add it here
            orderItem.setGivePoint(orderItem.getGivePoint() + dividePoints.get(i));
        }
        // 3.3 Update order to give points
        TradePriceCalculatorHelper.recountAllGivePoint(result);
    }

}
