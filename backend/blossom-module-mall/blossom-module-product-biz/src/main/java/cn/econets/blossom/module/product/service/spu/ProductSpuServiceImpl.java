package cn.econets.blossom.module.product.service.spu;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.product.controller.admin.category.vo.ProductCategoryListReqVO;
import cn.econets.blossom.module.product.controller.admin.spu.vo.ProductSkuSaveReqVO;
import cn.econets.blossom.module.product.controller.admin.spu.vo.ProductSpuPageReqVO;
import cn.econets.blossom.module.product.controller.admin.spu.vo.ProductSpuSaveReqVO;
import cn.econets.blossom.module.product.controller.admin.spu.vo.ProductSpuUpdateStatusReqVO;
import cn.econets.blossom.module.product.controller.app.spu.vo.AppProductSpuPageReqVO;
import cn.econets.blossom.module.product.dal.dataobject.category.ProductCategoryDO;
import cn.econets.blossom.module.product.dal.dataobject.spu.ProductSpuDO;
import cn.econets.blossom.module.product.dal.mysql.spu.ProductSpuMapper;
import cn.econets.blossom.module.product.enums.spu.ProductSpuStatusEnum;
import cn.econets.blossom.module.product.service.brand.ProductBrandService;
import cn.econets.blossom.module.product.service.category.ProductCategoryService;
import cn.econets.blossom.module.product.service.sku.ProductSkuService;
import com.google.common.collect.Maps;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.module.product.dal.dataobject.category.ProductCategoryDO.CATEGORY_LEVEL;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.*;

/**
 * Products SPU Service Implementation class
 *
 */
@Service
@Validated
public class ProductSpuServiceImpl implements ProductSpuService {

    @Resource
    private ProductSpuMapper productSpuMapper;

    @Resource
    @Lazy // Circular dependency，Avoid errors
    private ProductSkuService productSkuService;
    @Resource
    private ProductBrandService brandService;
    @Resource
    private ProductCategoryService categoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSpu(ProductSpuSaveReqVO createReqVO) {
        // Verification Category、Brand
        validateCategory(createReqVO.getCategoryId());
        brandService.validateProductBrand(createReqVO.getBrandId());
        // Verification SKU
        List<ProductSkuSaveReqVO> skuSaveReqList = createReqVO.getSkus();
        productSkuService.validateSkuList(skuSaveReqList, createReqVO.getSpecType());

        ProductSpuDO spu = BeanUtils.toBean(createReqVO, ProductSpuDO.class);
        // Initialization SPU Medium SKU Related properties
        initSpuFromSkus(spu, skuSaveReqList);
        // Insert SPU
        productSpuMapper.insert(spu);
        // Insert SKU
        productSkuService.createSkuList(spu.getId(), skuSaveReqList);
        // Return
        return spu.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSpu(ProductSpuSaveReqVO updateReqVO) {
        // Verification SPU Does it exist?
        validateSpuExists(updateReqVO.getId());
        // Verification Category、Brand
        validateCategory(updateReqVO.getCategoryId());
        brandService.validateProductBrand(updateReqVO.getBrandId());
        // VerificationSKU
        List<ProductSkuSaveReqVO> skuSaveReqList = updateReqVO.getSkus();
        productSkuService.validateSkuList(skuSaveReqList, updateReqVO.getSpecType());

        // Update SPU
        ProductSpuDO updateObj = BeanUtils.toBean(updateReqVO, ProductSpuDO.class);
        initSpuFromSkus(updateObj, skuSaveReqList);
        productSpuMapper.updateById(updateObj);
        // Batch update SKU
        productSkuService.updateSkuList(updateObj.getId(), updateReqVO.getSkus());
    }

    /**
     * Based on SKU Information，Initialization SPU Information
     * Mainly count-related fields，For example, market price、Maximum and minimum price、Inventory, etc.
     *
     * @param spu  Products SPU
     * @param skus Products SKU Array
     */
    private void initSpuFromSkus(ProductSpuDO spu, List<ProductSkuSaveReqVO> skus) {
        // sku The price of the product with the lowest unit price
        spu.setPrice(getMinValue(skus, ProductSkuSaveReqVO::getPrice));
        // sku The market price of the product with the lowest unit price
        spu.setMarketPrice(getMinValue(skus, ProductSkuSaveReqVO::getMarketPrice));
        // sku The cost price of the product with the lowest unit price
        spu.setCostPrice(getMinValue(skus, ProductSkuSaveReqVO::getCostPrice));
        // skus Total inventory
        spu.setStock(getSumValue(skus, ProductSkuSaveReqVO::getStock, Integer::sum));
        // If spu Do not process if the status already exists
        if (spu.getStatus() == null) {
            spu.setStatus(ProductSpuStatusEnum.ENABLE.getStatus()); // The default status is listed
            spu.setSalesCount(0); // Default product sales
            spu.setBrowseCount(0); // Default product views
        }
    }

    /**
     * Check whether the product classification is legal
     *
     * @param id Product Category Number
     */
    private void validateCategory(Long id) {
        categoryService.validateCategory(id);
        // Verification level
        if (categoryService.getCategoryLevel(id) < CATEGORY_LEVEL) {
            throw exception(SPU_SAVE_FAIL_CATEGORY_LEVEL_ERROR);
        }
    }

    @Override
    public List<ProductSpuDO> validateSpuList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        // Get product information
        List<ProductSpuDO> list = productSpuMapper.selectBatchIds(ids);
        Map<Long, ProductSpuDO> spuMap = CollectionUtils.convertMap(list, ProductSpuDO::getId);
        // Verification
        ids.forEach(id -> {
            ProductSpuDO spu = spuMap.get(id);
            if (spu == null) {
                throw exception(SPU_NOT_EXISTS);
            }
            if (!ProductSpuStatusEnum.isEnable(spu.getStatus())) {
                throw exception(SPU_NOT_ENABLE, spu.getName());
            }
        });
        return list;
    }

    @Override
    public void updateBrowseCount(Long id, int incrCount) {
        productSpuMapper.updateBrowseCount(id , incrCount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSpu(Long id) {
        // Check existence
        validateSpuExists(id);
        // Check that the product status is not in the recycle bin and cannot be deleted
        ProductSpuDO spuDO = productSpuMapper.selectById(id);
        // Judgement SPU Is the status the recycle bin?
        if (ObjectUtil.notEqual(spuDO.getStatus(), ProductSpuStatusEnum.RECYCLE.getStatus())) {
            throw exception(SPU_NOT_RECYCLE);
        }
        // TODO 【Optional】Products participating in the event，Deletion is not allowed？？？

        // Delete SPU
        productSpuMapper.deleteById(id);
        // Delete associated SKU
        productSkuService.deleteSkuBySpuId(id);
    }

    private void validateSpuExists(Long id) {
        if (productSpuMapper.selectById(id) == null) {
            throw exception(SPU_NOT_EXISTS);
        }
    }

    @Override
    public ProductSpuDO getSpu(Long id) {
        return productSpuMapper.selectById(id);
    }

    @Override
    public List<ProductSpuDO> getSpuList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return productSpuMapper.selectBatchIds(ids);
    }

    @Override
    public List<ProductSpuDO> getSpuListByStatus(Integer status) {
        return productSpuMapper.selectList(ProductSpuDO::getStatus, status);
    }

    @Override
    public PageResult<ProductSpuDO> getSpuPage(ProductSpuPageReqVO pageReqVO) {
        return productSpuMapper.selectPage(pageReqVO);
    }

    @Override
    public PageResult<ProductSpuDO> getSpuPage(AppProductSpuPageReqVO pageReqVO) {
        // When searching，If you want to find a category number，Its subcategories are included。Because the top-level category does not contain products
        Set<Long> categoryIds = new HashSet<>();
        if (pageReqVO.getCategoryId() != null && pageReqVO.getCategoryId() > 0) {
            categoryIds.add(pageReqVO.getCategoryId());
            List<ProductCategoryDO> categoryChildren = categoryService.getCategoryList(new ProductCategoryListReqVO()
                    .setStatus(CommonStatusEnum.ENABLE.getStatus()).setParentId(pageReqVO.getCategoryId()));
            categoryIds.addAll(convertList(categoryChildren, ProductCategoryDO::getId));
        }
        if (CollUtil.isNotEmpty(pageReqVO.getCategoryIds())) {
            categoryIds.addAll(pageReqVO.getCategoryIds());
            List<ProductCategoryDO> categoryChildren = categoryService.getCategoryList(new ProductCategoryListReqVO()
                    .setStatus(CommonStatusEnum.ENABLE.getStatus()).setParentIds(pageReqVO.getCategoryIds()));
            categoryIds.addAll(convertList(categoryChildren, ProductCategoryDO::getId));
        }
        // Paged query
        return productSpuMapper.selectPage(pageReqVO, categoryIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSpuStock(Map<Long, Integer> stockIncrCounts) {
        stockIncrCounts.forEach((id, incCount) -> productSpuMapper.updateStock(id, incCount));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSpuStatus(ProductSpuUpdateStatusReqVO updateReqVO) {
        // Verify existence
        validateSpuExists(updateReqVO.getId());
        // TODO 【Optional】Products participating in the event，Not allowed to be removed from shelves？？？

        // Update status
        ProductSpuDO productSpuDO = productSpuMapper.selectById(updateReqVO.getId()).setStatus(updateReqVO.getStatus());
        productSpuMapper.updateById(productSpuDO);
    }

    @Override
    public Map<Integer, Long> getTabsCount() {
        Map<Integer, Long> counts = Maps.newLinkedHashMapWithExpectedSize(5);
        // Query the number of goods on sale
        counts.put(ProductSpuPageReqVO.FOR_SALE,
                productSpuMapper.selectCount(ProductSpuDO::getStatus, ProductSpuStatusEnum.ENABLE.getStatus()));
        // Query the quantity of goods in the warehouse
        counts.put(ProductSpuPageReqVO.IN_WAREHOUSE,
                productSpuMapper.selectCount(ProductSpuDO::getStatus, ProductSpuStatusEnum.DISABLE.getStatus()));
        // Query the number of sold-out products
        counts.put(ProductSpuPageReqVO.SOLD_OUT,
                productSpuMapper.selectCount(ProductSpuDO::getStock, 0));
        // Query the quantity of goods that trigger the alert inventory
        counts.put(ProductSpuPageReqVO.ALERT_STOCK,
                productSpuMapper.selectCount());
        // Query the number of items in the recycling bin
        counts.put(ProductSpuPageReqVO.RECYCLE_BIN,
                productSpuMapper.selectCount(ProductSpuDO::getStatus, ProductSpuStatusEnum.RECYCLE.getStatus()));
        return counts;
    }

    @Override
    public Long getSpuCountByCategoryId(Long categoryId) {
        return productSpuMapper.selectCount(ProductSpuDO::getCategoryId, categoryId);
    }

}
