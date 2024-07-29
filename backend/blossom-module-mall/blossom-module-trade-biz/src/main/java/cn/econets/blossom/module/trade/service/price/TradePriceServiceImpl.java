package cn.econets.blossom.module.trade.service.price;

import cn.econets.blossom.module.product.api.sku.ProductSkuApi;
import cn.econets.blossom.module.product.api.sku.dto.ProductSkuRespDTO;
import cn.econets.blossom.module.product.api.spu.ProductSpuApi;
import cn.econets.blossom.module.product.api.spu.dto.ProductSpuRespDTO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateReqBO;
import cn.econets.blossom.module.trade.service.price.bo.TradePriceCalculateRespBO;
import cn.econets.blossom.module.trade.service.price.calculator.TradePriceCalculator;
import cn.econets.blossom.module.trade.service.price.calculator.TradePriceCalculatorHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.SKU_NOT_EXISTS;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.SKU_STOCK_NOT_ENOUGH;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.PRICE_CALCULATE_PAY_PRICE_ILLEGAL;

/**
 * Price calculation Service Implementation class
 *
 */
@Service
@Validated
@Slf4j
public class TradePriceServiceImpl implements TradePriceService {

    @Resource
    private ProductSkuApi productSkuApi;
    @Resource
    private ProductSpuApi productSpuApi;

    @Resource
    private List<TradePriceCalculator> priceCalculators;

    @Override
    public TradePriceCalculateRespBO calculatePrice(TradePriceCalculateReqBO calculateReqBO) {
        // 1.1 Get goods SKU Array
        List<ProductSkuRespDTO> skuList = checkSkuList(calculateReqBO);
        // 1.2 Get goods SPU Array
        List<ProductSpuRespDTO> spuList = checkSpuList(skuList);

        // 2.1 Calculate price
        TradePriceCalculateRespBO calculateRespBO = TradePriceCalculatorHelper
                .buildCalculateResp(calculateReqBO, spuList, skuList);
        priceCalculators.forEach(calculator -> calculator.calculate(calculateReqBO, calculateRespBO));
        // 2.2  If the final payment amount is less than or equal to 0，Throws a business exception
        if (calculateRespBO.getPrice().getPayPrice() <= 0) {
            log.error("[calculatePrice][Price calculation is incorrect，Request calculateReqDTO({})，Results priceCalculate({})]",
                    calculateReqBO, calculateRespBO);
            throw exception(PRICE_CALCULATE_PAY_PRICE_ILLEGAL);
        }
        return calculateRespBO;
    }

    private List<ProductSkuRespDTO> checkSkuList(TradePriceCalculateReqBO reqBO) {
        // Get goods SKU Array
        Map<Long, Integer> skuIdCountMap = convertMap(reqBO.getItems(),
                TradePriceCalculateReqBO.Item::getSkuId, TradePriceCalculateReqBO.Item::getCount);
        List<ProductSkuRespDTO> skus = productSkuApi.getSkuList(skuIdCountMap.keySet());

        // Check product SKU
        skus.forEach(sku -> {
            Integer count = skuIdCountMap.get(sku.getId());
            if (count == null) {
                throw exception(SKU_NOT_EXISTS);
            }
            if (count > sku.getStock()) {
                throw exception(SKU_STOCK_NOT_ENOUGH);
            }
        });
        return skus;
    }

    private List<ProductSpuRespDTO> checkSpuList(List<ProductSkuRespDTO> skuList) {
        // Get goods SPU Array
        return productSpuApi.validateSpuList(convertSet(skuList, ProductSkuRespDTO::getSpuId));
    }

}
