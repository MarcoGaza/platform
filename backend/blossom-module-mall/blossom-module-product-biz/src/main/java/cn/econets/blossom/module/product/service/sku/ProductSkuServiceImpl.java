package cn.econets.blossom.module.product.service.sku;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.product.api.sku.dto.ProductSkuUpdateStockReqDTO;
import cn.econets.blossom.module.product.controller.admin.spu.vo.ProductSkuSaveReqVO;
import cn.econets.blossom.module.product.convert.sku.ProductSkuConvert;
import cn.econets.blossom.module.product.dal.dataobject.property.ProductPropertyDO;
import cn.econets.blossom.module.product.dal.dataobject.property.ProductPropertyValueDO;
import cn.econets.blossom.module.product.dal.dataobject.sku.ProductSkuDO;
import cn.econets.blossom.module.product.dal.mysql.sku.ProductSkuMapper;
import cn.econets.blossom.module.product.service.property.ProductPropertyService;
import cn.econets.blossom.module.product.service.property.ProductPropertyValueService;
import cn.econets.blossom.module.product.service.spu.ProductSpuService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.*;

/**
 * Products SKU Service Implementation class
 *
 */
@Service
@Validated
public class ProductSkuServiceImpl implements ProductSkuService {

    @Resource
    private ProductSkuMapper productSkuMapper;

    @Resource
    @Lazy // Circular dependency，Avoid errors
    private ProductSpuService productSpuService;
    @Resource
    @Lazy // Circular dependency，Avoid errors
    private ProductPropertyService productPropertyService;
    @Resource
    private ProductPropertyValueService productPropertyValueService;

    @Override
    public void deleteSku(Long id) {
        // Verify existence
        validateSkuExists(id);
        // Delete
        productSkuMapper.deleteById(id);
    }

    private void validateSkuExists(Long id) {
        if (productSkuMapper.selectById(id) == null) {
            throw exception(SKU_NOT_EXISTS);
        }
    }

    @Override
    public ProductSkuDO getSku(Long id) {
        return productSkuMapper.selectById(id);
    }

    @Override
    public List<ProductSkuDO> getSkuList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return productSkuMapper.selectBatchIds(ids);
    }

    @Override
    public void validateSkuList(List<ProductSkuSaveReqVO> skus, Boolean specType) {
        // 0、VerificationskusIs it empty?
        if (CollUtil.isEmpty(skus)) {
            throw exception(SKU_NOT_EXISTS);
        }
        // Single specification，Give single specification default attributes
        if (ObjectUtil.equal(specType, false)) {
            ProductSkuSaveReqVO skuVO = skus.get(0);
            List<ProductSkuSaveReqVO.Property> properties = new ArrayList<>();
            ProductSkuSaveReqVO.Property property = new ProductSkuSaveReqVO.Property()
                    .setPropertyId(ProductPropertyDO.ID_DEFAULT).setPropertyName(ProductPropertyDO.NAME_DEFAULT)
                    .setValueId(ProductPropertyValueDO.ID_DEFAULT).setValueName(ProductPropertyValueDO.NAME_DEFAULT);
            properties.add(property);
            skuVO.setProperties(properties);
            return; // Single specification does not require subsequent verification
        }

        // 1、Verify that the attribute exists
        Set<Long> propertyIds = skus.stream().filter(p -> p.getProperties() != null)
                // Traverse multiple Property Properties
                .flatMap(p -> p.getProperties().stream())
                // Each Property Convert to corresponding propertyId，Finally forming a collection
                .map(ProductSkuSaveReqVO.Property::getPropertyId)
                .collect(Collectors.toSet());
        List<ProductPropertyDO> propertyList = productPropertyService.getPropertyList(propertyIds);
        if (propertyList.size() != propertyIds.size()) {
            throw exception(PROPERTY_NOT_EXISTS);
        }

        // 2. Verification，One SKU Next，No duplicate attributes。The verification method is，Traverse each SKU ，Check if there are duplicate attributes propertyId
        Map<Long, ProductPropertyValueDO> propertyValueMap = convertMap(productPropertyValueService.getPropertyValueListByPropertyId(propertyIds), ProductPropertyValueDO::getId);
        skus.forEach(sku -> {
            Set<Long> skuPropertyIds = convertSet(sku.getProperties(), propertyItem -> propertyValueMap.get(propertyItem.getValueId()).getPropertyId());
            if (skuPropertyIds.size() != sku.getProperties().size()) {
                throw exception(SKU_PROPERTIES_DUPLICATED);
            }
        });

        // 3. Recheck，Each Sku The number of attribute values，is consistent。
        int attrValueIdsSize = skus.get(0).getProperties().size();
        for (int i = 1; i < skus.size(); i++) {
            if (attrValueIdsSize != skus.get(i).getProperties().size()) {
                throw exception(SPU_ATTR_NUMBERS_MUST_BE_EQUALS);
            }
        }

        // 4. Final check，Each Sku There are no duplicates
        // Each element，They are all the same Sku of attrValueId Collection。Like this，Through the outermost layer Set ，Judge whether there are duplicates.
        Set<Set<Long>> skuAttrValues = new HashSet<>();
        for (ProductSkuSaveReqVO sku : skus) {
            // Add failed，Description repeated
            if (!skuAttrValues.add(convertSet(sku.getProperties(), ProductSkuSaveReqVO.Property::getValueId))) {
                throw exception(SPU_SKU_NOT_DUPLICATE);
            }
        }
    }

    @Override
    public void createSkuList(Long spuId, List<ProductSkuSaveReqVO> skuCreateReqList) {
        List<ProductSkuDO> skus = BeanUtils.toBean(skuCreateReqList, ProductSkuDO.class, sku -> sku.setSpuId(spuId));
        productSkuMapper.insertBatch(skus);
    }

    @Override
    public List<ProductSkuDO> getSkuListBySpuId(Long spuId) {
        return productSkuMapper.selectListBySpuId(spuId);
    }

    @Override
    public List<ProductSkuDO> getSkuListBySpuId(Collection<Long> spuIds) {
        if (CollUtil.isEmpty(spuIds)) {
            return Collections.emptyList();
        }
        return productSkuMapper.selectListBySpuId(spuIds);
    }

    @Override
    public void deleteSkuBySpuId(Long spuId) {
        productSkuMapper.deleteBySpuId(spuId);
    }

    @Override
    public int updateSkuProperty(Long propertyId, String propertyName) {
        // Get all sku
        List<ProductSkuDO> skuDOList = productSkuMapper.selectList();
        // Need to be updated after processing sku
        List<ProductSkuDO> updateSkus = new ArrayList<>();
        if (CollUtil.isEmpty(skuDOList)) {
            return 0;
        }
        skuDOList.stream().filter(sku -> sku.getProperties() != null)
                .forEach(sku -> sku.getProperties().forEach(property -> {
                    if (property.getPropertyId().equals(propertyId)) {
                        property.setPropertyName(propertyName);
                        updateSkus.add(sku);
                    }
                }));
        if (CollUtil.isEmpty(updateSkus)) {
            return 0;
        }

        productSkuMapper.updateBatch(updateSkus);
        return updateSkus.size();
    }

    @Override
    public int updateSkuPropertyValue(Long propertyValueId, String propertyValueName) {
        // Get all sku
        List<ProductSkuDO> skuDOList = productSkuMapper.selectList();
        // Need to be updated after processing sku
        List<ProductSkuDO> updateSkus = new ArrayList<>();
        if (CollUtil.isEmpty(skuDOList)) {
            return 0;
        }
        skuDOList.stream()
                .filter(sku -> sku.getProperties() != null)
                .forEach(sku -> sku.getProperties().forEach(property -> {
                    if (property.getValueId().equals(propertyValueId)) {
                        property.setValueName(propertyValueName);
                        updateSkus.add(sku);
                    }
                }));
        if (CollUtil.isEmpty(updateSkus)) {
            return 0;
        }

        productSkuMapper.updateBatch(updateSkus);
        return updateSkus.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSkuList(Long spuId, List<ProductSkuSaveReqVO> skus) {
        // Build properties and SKU Mapping relationship;
        Map<String, Long> existsSkuMap = convertMap(productSkuMapper.selectListBySpuId(spuId),
                ProductSkuConvert.INSTANCE::buildPropertyKey, ProductSkuDO::getId);

        // Split three sets，Newly inserted、Needs to be updated、Need to be deleted
        List<ProductSkuDO> insertSkus = new ArrayList<>();
        List<ProductSkuDO> updateSkus = new ArrayList<>();
        List<ProductSkuDO> allUpdateSkus = BeanUtils.toBean(skus, ProductSkuDO.class, sku -> sku.setSpuId(spuId));
        allUpdateSkus.forEach(sku -> {
            String propertiesKey = ProductSkuConvert.INSTANCE.buildPropertyKey(sku);
            // 1、Can be found，Update
            Long existsSkuId = existsSkuMap.remove(propertiesKey);
            if (existsSkuId != null) {
                sku.setId(existsSkuId);
                updateSkus.add(sku);
                return;
            }
            // 2、Not found，Insert
            sku.setSpuId(spuId);
            insertSkus.add(sku);
        });

        // Execute the final batch operation
        if (CollUtil.isNotEmpty(insertSkus)) {
            productSkuMapper.insertBatch(insertSkus);
        }
        if (CollUtil.isNotEmpty(updateSkus)) {
            updateSkus.forEach(sku -> productSkuMapper.updateById(sku));
        }
        if (CollUtil.isNotEmpty(existsSkuMap)) {
            productSkuMapper.deleteBatchIds(existsSkuMap.values());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSkuStock(ProductSkuUpdateStockReqDTO updateStockReqDTO) {
        // Update SKU Inventory
        updateStockReqDTO.getItems().forEach(item -> {
            if (item.getIncrCount() > 0) {
                productSkuMapper.updateStockIncr(item.getId(), item.getIncrCount());
            } else if (item.getIncrCount() < 0) {
                int updateStockIncr = productSkuMapper.updateStockDecr(item.getId(), item.getIncrCount());
                if (updateStockIncr == 0) {
                    throw exception(SKU_STOCK_NOT_ENOUGH);
                }
            }
        });

        // Update SPU Inventory
        List<ProductSkuDO> skus = productSkuMapper.selectBatchIds(
                convertSet(updateStockReqDTO.getItems(), ProductSkuUpdateStockReqDTO.Item::getId));
        Map<Long, Integer> spuStockIncrCounts = ProductSkuConvert.INSTANCE.convertSpuStockMap(
                updateStockReqDTO.getItems(), skus);
        productSpuService.updateSpuStock(spuStockIncrCounts);
    }

}
