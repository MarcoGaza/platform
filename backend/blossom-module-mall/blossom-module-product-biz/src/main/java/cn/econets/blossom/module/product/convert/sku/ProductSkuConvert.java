package cn.econets.blossom.module.product.convert.sku;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.product.api.sku.dto.ProductSkuUpdateStockReqDTO;
import cn.econets.blossom.module.product.dal.dataobject.sku.ProductSkuDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.*;
import java.util.stream.Collectors;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * Products SKU Convert
 *
 */
@Mapper
public interface ProductSkuConvert {

    ProductSkuConvert INSTANCE = Mappers.getMapper(ProductSkuConvert.class);

    /**
     * Get SPU Inventory changes Map
     *
     * @param items SKU Inventory changes
     * @param skus SKU List
     * @return SPU Inventory changes Map
     */
    default Map<Long, Integer> convertSpuStockMap(List<ProductSkuUpdateStockReqDTO.Item> items,
                                                  List<ProductSkuDO> skus) {
        Map<Long, Long> skuIdAndSpuIdMap = convertMap(skus, ProductSkuDO::getId, ProductSkuDO::getSpuId); // SKU with SKU Numbered Map Relationship
        Map<Long, Integer> spuIdAndStockMap = new HashMap<>(); // SPU Inventory changes Map Relationship
        items.forEach(item -> {
            Long spuId = skuIdAndSpuIdMap.get(item.getId());
            if (spuId == null) {
                return;
            }
            Integer stock = spuIdAndStockMap.getOrDefault(spuId, 0) + item.getIncrCount();
            spuIdAndStockMap.put(spuId, stock);
        });
        return spuIdAndStockMap;
    }

    default String buildPropertyKey(ProductSkuDO bean) {
        if (CollUtil.isEmpty(bean.getProperties())) {
            return StrUtil.EMPTY;
        }
        List<ProductSkuDO.Property> properties = new ArrayList<>(bean.getProperties());
        properties.sort(Comparator.comparing(ProductSkuDO.Property::getValueId));
        return properties.stream().map(m -> String.valueOf(m.getValueId())).collect(Collectors.joining());
    }

}
