package cn.econets.blossom.module.crm.service.product;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.crm.controller.admin.product.vo.product.CrmProductPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.product.vo.product.CrmProductSaveReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.product.CrmProductDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * CRM Products Service Interface
 *
 */
public interface CrmProductService {

    /**
     * Create product
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createProduct(@Valid CrmProductSaveReqVO createReqVO);

    /**
     * Update product
     *
     * @param updateReqVO Update information
     */
    void updateProduct(@Valid CrmProductSaveReqVO updateReqVO);

    /**
     * Delete product
     *
     * @param id Number
     */
    void deleteProduct(Long id);

    /**
     * Get products
     *
     * @param id Number
     * @return Products
     */
    CrmProductDO getProduct(Long id);

    /**
     * Get product list
     *
     * @param ids Number
     * @return Product List
     */
    List<CrmProductDO> getProductList(Collection<Long> ids);

    /**
     * Get product paging
     *
     * @param pageReqVO Paged query
     * @return Product Paging
     */
    PageResult<CrmProductDO> getProductPage(CrmProductPageReqVO pageReqVO, Long userId);

    /**
     * Get products
     *
     * @param categoryId Classification number
     * @return Products
     */
    CrmProductDO getProductByCategoryId(Long categoryId);

    /**
     * Get product list
     *
     * @param ids Product Number
     * @return Product List
     */
    List<CrmProductDO> getProductListByIds(Collection<Long> ids);

}
