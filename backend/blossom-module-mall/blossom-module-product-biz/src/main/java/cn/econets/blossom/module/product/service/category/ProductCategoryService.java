package cn.econets.blossom.module.product.service.category;

import cn.econets.blossom.module.product.controller.admin.category.vo.ProductCategoryListReqVO;
import cn.econets.blossom.module.product.controller.admin.category.vo.ProductCategorySaveReqVO;
import cn.econets.blossom.module.product.dal.dataobject.category.ProductCategoryDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Product Categories Service Interface
 *
 */
public interface ProductCategoryService {

    /**
     * Create product categories
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createCategory(@Valid ProductCategorySaveReqVO createReqVO);

    /**
     * Update product categories
     *
     * @param updateReqVO Update information
     */
    void updateCategory(@Valid ProductCategorySaveReqVO updateReqVO);

    /**
     * Delete product category
     *
     * @param id Number
     */
    void deleteCategory(Long id);

    /**
     * Get product classification
     *
     * @param id Number
     * @return Product Categories
     */
    ProductCategoryDO getCategory(Long id);

    /**
     * Check product category
     *
     * @param id Classification number
     */
    void validateCategory(Long id);

    /**
     * Get the level of product classification
     *
     * @param id Number
     * @return Product classification level
     */
    Integer getCategoryLevel(Long id);

    /**
     * Get product category list
     *
     * @param listReqVO Query conditions
     * @return Product Category List
     */
    List<ProductCategoryDO> getCategoryList(ProductCategoryListReqVO listReqVO);

    /**
     * Get the list of product categories that are enabled
     *
     * @return Product Category List
     */
    List<ProductCategoryDO> getEnableCategoryList();

    /**
     * Get the list of product categories that are enabled，Specify number
     *
     * @param ids Product category number array
     * @return Product Category List
     */
    List<ProductCategoryDO> getEnableCategoryList(List<Long> ids);

    /**
     * Check whether the product category is valid。As follows，Deemed invalid：
     * 1. Product category number does not exist
     * 2. Product category is disabled
     *
     * @param ids Product category number array
     */
    void validateCategoryList(Collection<Long> ids);

}
