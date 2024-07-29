package cn.econets.blossom.module.crm.service.product;

import cn.econets.blossom.module.crm.controller.admin.product.vo.category.CrmProductCategoryCreateReqVO;
import cn.econets.blossom.module.crm.controller.admin.product.vo.category.CrmProductCategoryListReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.product.CrmProductCategoryDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * CRM Product Category Service Interface
 *
 */
public interface CrmProductCategoryService {

    /**
     * Create product categories
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createProductCategory(@Valid CrmProductCategoryCreateReqVO createReqVO);

    /**
     * Update product categories
     *
     * @param updateReqVO Update information
     */
    void updateProductCategory(@Valid CrmProductCategoryCreateReqVO updateReqVO);

    /**
     * Delete product category
     *
     * @param id Number
     */
    void deleteProductCategory(Long id);

    /**
     * Get product categories
     *
     * @param id Number
     * @return Product Category
     */
    CrmProductCategoryDO getProductCategory(Long id);

    /**
     * Get product category list
     *
     * @param listReqVO List Request
     * @return Product Category List
     */
    List<CrmProductCategoryDO> getProductCategoryList(CrmProductCategoryListReqVO listReqVO);

    /**
     * Get product category list
     *
     * @param ids Number array
     * @return Product Category List
     */
    List<CrmProductCategoryDO> getProductCategoryList(Collection<Long> ids);

}
