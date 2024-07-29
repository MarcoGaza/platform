package cn.econets.blossom.module.trade.service.price.calculator;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.module.member.api.address.MemberAddressApi;
import cn.econets.blossom.module.member.api.address.dto.MemberAddressRespDTO;
import cn.econets.blossom.module.trade.dal.dataobject.config.TradeConfigDO;
import cn.econets.blossom.module.trade.dal.dataobject.delivery.DeliveryPickUpStoreDO;
import cn.econets.blossom.module.trade.enums.delivery.DeliveryExpressChargeModeEnum;
import cn.econets.blossom.module.trade.enums.delivery.DeliveryTypeEnum;
import cn.econets.blossom.module.trade.service.config.TradeConfigService;
import cn.econets.blossom.module.trade.service.delivery.DeliveryExpressTemplateService;
import cn.econets.blossom.module.trade.service.delivery.DeliveryPickUpStoreService;
import cn.econets.blossom.module.trade.service.delivery.bo.DeliveryExpressTemplateRespBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.*;

/**
 * Shipping fee {@link TradePriceCalculator} Implementation class
 *
 */
@Component
@Order(TradePriceCalculator.ORDER_DELIVERY)
@Slf4j
public class TradeDeliveryPriceCalculator implements TradePriceCalculator {

    @Resource
    private MemberAddressApi addressApi;

    @Resource
    private DeliveryPickUpStoreService deliveryPickUpStoreService;
    @Resource
    private DeliveryExpressTemplateService deliveryExpressTemplateService;
    @Resource
    private TradeConfigService tradeConfigService;

    @Override
    public void calculate(TradePriceCalculateReqBO param, TradePriceCalculateRespBO result) {
        if (param.getDeliveryType() == null) {
            return;
        }
        if (DeliveryTypeEnum.PICK_UP.getType().equals(param.getDeliveryType())) {
            calculateByPickUp(param);
        } else if (DeliveryTypeEnum.EXPRESS.getType().equals(param.getDeliveryType())) {
            calculateExpress(param, result);
        }
    }

    private void calculateByPickUp(TradePriceCalculateReqBO param) {
        if (param.getPickUpStoreId() == null) {
            // When calculating price，If it is empty, it will not count~Final order，This field will be checked and cannot be left blank
            return;
        }
        DeliveryPickUpStoreDO pickUpStore = deliveryPickUpStoreService.getDeliveryPickUpStore(param.getPickUpStoreId());
        if (pickUpStore == null || CommonStatusEnum.DISABLE.getStatus().equals(pickUpStore.getStatus())) {
            throw exception(PICK_UP_STORE_NOT_EXISTS);
        }
    }

    // ========= Express delivery ==========

    private void calculateExpress(TradePriceCalculateReqBO param, TradePriceCalculateRespBO result) {
        // 0. Get the recipient address area
        if (param.getAddressId() == null) {
            // When calculating price，If it is empty, it will not be counted~Final order，This field will be checked and cannot be left blank
            return;
        }
        MemberAddressRespDTO address = addressApi.getAddress(param.getAddressId(), param.getUserId());
        Assert.notNull(address, "Recipient({})Address，Cannot be empty", param.getUserId());

        // Situation 1：Free shipping worldwide
        if (isGlobalExpressFree(result)) {
            return;
        }

        // Case 2：Express delivery template
        // 2.1 Filter selected products SKU
        List<OrderItem> selectedItem = filterList(result.getItems(), OrderItem::getSelected);
        Set<Long> deliveryTemplateIds = convertSet(selectedItem, OrderItem::getDeliveryTemplateId);
        Map<Long, DeliveryExpressTemplateRespBO> expressTemplateMap =
                deliveryExpressTemplateService.getExpressTemplateMapByIdsAndArea(deliveryTemplateIds, address.getAreaId());
        // 2.2 Calculate delivery costs
        if (CollUtil.isEmpty(expressTemplateMap)) {
            log.error("[calculate][Product not found templateIds {} areaId{} Corresponding freight template]", deliveryTemplateIds, address.getAreaId());
            throw exception(PRICE_CALCULATE_DELIVERY_PRICE_TEMPLATE_NOT_FOUND);
        }
        calculateDeliveryPrice(selectedItem, expressTemplateMap, result);
    }

    /**
     * Whether free shipping is available globally
     *
     * @param result Calculation results
     * @return Is shipping free?
     */
    private boolean isGlobalExpressFree(TradePriceCalculateRespBO result) {
        TradeConfigDO config = tradeConfigService.getTradeConfig();
        return config != null
                && Boolean.TRUE.equals(config.getDeliveryExpressFreeEnabled()) // Open free shipping
                && result.getPrice().getPayPrice() >= config.getDeliveryExpressFreePrice(); // Satisfying the price of free shipping
    }

    private void calculateDeliveryPrice(List<OrderItem> selectedSkus,
                                        Map<Long, DeliveryExpressTemplateRespBO> expressTemplateMap,
                                        TradePriceCalculateRespBO result) {
        // Calculate product shipping costs based on product shipping cost template：The same shipping template may correspond to multiple order items SKU
        Map<Long, List<OrderItem>> template2ItemMap = convertMultiMap(selectedSkus, OrderItem::getDeliveryTemplateId);
        // Calculate the express delivery charges in sequence
        for (Map.Entry<Long, List<OrderItem>> entry : template2ItemMap.entrySet()) {
            Long templateId  = entry.getKey();
            List<OrderItem> orderItems = entry.getValue();
            DeliveryExpressTemplateRespBO templateBO = expressTemplateMap.get(templateId);
            if (templateBO == null) {
                log.error("[calculateDeliveryPrice][Courier freight cannot be calculated，Not found templateId({}) Corresponding freight template configuration]", templateId);
                continue;
            }
            // 1. Prioritize whether free shipping is available。If free shipping is not included, express delivery fee is not included
            if (isExpressTemplateFree(orderItems, templateBO.getChargeMode(), templateBO.getFree())) {
                continue;
            }
            // 2. Calculate express delivery charges
            calculateExpressFeeByChargeMode(orderItems, templateBO.getChargeMode(), templateBO.getCharge());
        }
        TradePriceCalculatorHelper.recountAllPrice(result);
    }

    /**
     * Calculate shipping costs based on delivery method
     *
     * @param orderItems SKU Product Item
     * @param chargeMode  Delivery billing method
     * @param templateCharge Express delivery fee configuration
     */
    private void calculateExpressFeeByChargeMode(List<OrderItem> orderItems, Integer chargeMode,
                                                 DeliveryExpressTemplateRespBO.Charge templateCharge) {
        if (templateCharge == null) {
            log.error("[calculateExpressFeeByChargeMode][Calculating express delivery costs，Not found SKU({}) Corresponding shipping template]", orderItems);
            return;
        }
        double totalChargeValue = getTotalChargeValue(orderItems, chargeMode);
        // 1. Calculation SKU Commodity delivery fee
        int deliveryPrice;
        if (totalChargeValue <= templateCharge.getStartCount()) {
            deliveryPrice = templateCharge.getStartPrice();
        } else {
            double remainWeight = totalChargeValue - templateCharge.getStartCount();
            // Remaining weight/ Continuation = Number of renewals. Round up
            int extraNum = (int) Math.ceil(remainWeight / templateCharge.getExtraCount());
            int extraPrice = templateCharge.getExtraPrice() * extraNum;
            deliveryPrice = templateCharge.getStartPrice() + extraPrice;
        }

        // 2. Share the courier costs SKU. When refunding，Maybe according to SKU Considering the refund amount
        int remainPrice = deliveryPrice;
        for (int i = 0; i < orderItems.size(); i++) {
            TradePriceCalculateRespBO.OrderItem item = orderItems.get(i);
            int partPrice;
            double chargeValue = getChargeValue(item, chargeMode);
            if (i < orderItems.size() - 1) { // Reason for reducing one，Because of the split，If according to the proportion，May appear.So the last one，Use anti-subtraction
                partPrice = (int) (deliveryPrice * (chargeValue / totalChargeValue));
                remainPrice -= partPrice;
            } else {
                partPrice = remainPrice;
            }
            Assert.isTrue(partPrice >= 0, "The amount of the allocation must be greater than or equal to 0");
            // Update express delivery rates
            item.setDeliveryPrice(partPrice);
            TradePriceCalculatorHelper.recountPayPrice(item);
        }
    }

    /**
     * Check if shipping is free
     *
     * @param chargeMode   Delivery billing method
     * @param templateFree Free shipping configuration
     */
    private boolean isExpressTemplateFree(List<OrderItem> orderItems, Integer chargeMode,
                                          DeliveryExpressTemplateRespBO.Free templateFree) {
        if (templateFree == null) {
            return false;
        }
        double totalChargeValue = getTotalChargeValue(orderItems, chargeMode);
        double totalPrice = TradePriceCalculatorHelper.calculateTotalPayPrice(orderItems);
        return totalChargeValue >= templateFree.getFreeCount() && totalPrice >= templateFree.getFreePrice();
    }

    private double getTotalChargeValue(List<OrderItem> orderItems, Integer chargeMode) {
        double total = 0;
        for (OrderItem orderItem : orderItems) {
            total += getChargeValue(orderItem, chargeMode);
        }
        return total;
    }

    private double getChargeValue(OrderItem orderItem, Integer chargeMode) {
        DeliveryExpressChargeModeEnum chargeModeEnum = DeliveryExpressChargeModeEnum.valueOf(chargeMode);
        switch (chargeModeEnum) {
            case COUNT:
                return orderItem.getCount();
            case WEIGHT:
                return orderItem.getWeight() != null ? orderItem.getWeight() * orderItem.getCount() : 0;
            case VOLUME:
                return orderItem.getVolume() != null ? orderItem.getVolume() * orderItem.getCount() : 0;
            default:
                throw new IllegalArgumentException(StrUtil.format("Unknown billing mode({})", chargeMode));
        }
    }

}
