package cn.econets.blossom.module.product.service.category;

import cn.hutool.core.collection.CollUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.product.controller.admin.category.vo.ProductCategoryListReqVO;
import cn.econets.blossom.module.product.controller.admin.category.vo.ProductCategorySaveReqVO;
import cn.econets.blossom.module.product.dal.dataobject.category.ProductCategoryDO;
import cn.econets.blossom.module.product.dal.mysql.category.ProductCategoryMapper;
import cn.econets.blossom.module.product.service.spu.ProductSpuService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.product.dal.dataobject.category.ProductCategoryDO.PARENT_ID_NULL;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.*;

/**
 * Product Categories Service Implementation class
 *
 */
@Service
@Validated
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Resource
    private ProductCategoryMapper productCategoryMapper;
    @Resource
    @Lazy // Circular dependency，Avoid errors
    private ProductSpuService productSpuService;

    @Override
    public Long createCategory(ProductCategorySaveReqVO createReqVO) {
        // Check if the parent category exists
        validateParentProductCategory(createReqVO.getParentId());

        // Insert
        ProductCategoryDO category = BeanUtils.toBean(createReqVO, ProductCategoryDO.class);
        productCategoryMapper.insert(category);
        // Return
        return category.getId();
    }

    @Override
    public void updateCategory(ProductCategorySaveReqVO updateReqVO) {
        // Check if the category exists
        validateProductCategoryExists(updateReqVO.getId());
        // Check if the parent category exists
        validateParentProductCategory(updateReqVO.getParentId());

        // Update
        ProductCategoryDO updateObj = BeanUtils.toBean(updateReqVO, ProductCategoryDO.class);
        productCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteCategory(Long id) {
        // Check if the category exists
        validateProductCategoryExists(id);
        // Check if there are subcategories
        if (productCategoryMapper.selectCountByParentId(id) > 0) {
            throw exception(CATEGORY_EXISTS_CHILDREN);
        }
        // Check whether the category is bound SPU
        Long spuCount = productSpuService.getSpuCountByCategoryId(id);
        if (spuCount > 0) {
            throw exception(CATEGORY_HAVE_BIND_SPU);
        }
        // Delete
        productCategoryMapper.deleteById(id);
    }

    private void validateParentProductCategory(Long id) {
        // If it is a root category，No verification required
        if (Objects.equals(id, PARENT_ID_NULL)) {
            return;
        }
        // The parent category does not exist
        ProductCategoryDO category = productCategoryMapper.selectById(id);
        if (category == null) {
            throw exception(CATEGORY_PARENT_NOT_EXISTS);
        }
        // The parent category cannot be a secondary category
        if (!Objects.equals(category.getParentId(), PARENT_ID_NULL)) {
            throw exception(CATEGORY_PARENT_NOT_FIRST_LEVEL);
        }
    }

    private void validateProductCategoryExists(Long id) {
        ProductCategoryDO category = productCategoryMapper.selectById(id);
        if (category == null) {
            throw exception(CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public void validateCategoryList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // Get product classification information
        List<ProductCategoryDO> list = productCategoryMapper.selectBatchIds(ids);
        Map<Long, ProductCategoryDO> categoryMap = CollectionUtils.convertMap(list, ProductCategoryDO::getId);
        // Verification
        ids.forEach(id -> {
            ProductCategoryDO category = categoryMap.get(id);
            if (category == null) {
                throw exception(CATEGORY_NOT_EXISTS);
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(category.getStatus())) {
                throw exception(CATEGORY_DISABLED, category.getName());
            }
        });
    }

    @Override
    public ProductCategoryDO getCategory(Long id) {
        return productCategoryMapper.selectById(id);
    }

    @Override
    public void validateCategory(Long id) {
        ProductCategoryDO category = productCategoryMapper.selectById(id);
        if (category == null) {
            throw exception(CATEGORY_NOT_EXISTS);
        }
        if (Objects.equals(category.getStatus(), CommonStatusEnum.DISABLE.getStatus())) {
            throw exception(CATEGORY_DISABLED, category.getName());
        }
    }

    @Override
    public Integer getCategoryLevel(Long id) {
        if (Objects.equals(id, PARENT_ID_NULL)) {
            return 0;
        }
        int level = 1;
        // for Reason，It is to avoid dirty data，Leading to a possible infinite loop。Generally not more than 100 Layer Ha
        for (int i = 0; i < Byte.MAX_VALUE; i++) {
            // If there is no parent node，break End
            ProductCategoryDO category = productCategoryMapper.selectById(id);
            if (category == null
                    || Objects.equals(category.getParentId(), PARENT_ID_NULL)) {
                break;
            }
            // Continue recursing the parent node
            level++;
            id = category.getParentId();
        }
        return level;
    }

    @Override
    public List<ProductCategoryDO> getCategoryList(ProductCategoryListReqVO listReqVO) {
        return productCategoryMapper.selectList(listReqVO);
    }

    @Override
    public List<ProductCategoryDO> getEnableCategoryList() {
        return productCategoryMapper.selectListByStatus(CommonStatusEnum.ENABLE.getStatus());
    }

    @Override
    public List<ProductCategoryDO> getEnableCategoryList(List<Long> ids) {
        return productCategoryMapper.selectListByIdAndStatus(ids, CommonStatusEnum.ENABLE.getStatus());
    }

}
