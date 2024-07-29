package cn.econets.blossom.module.product.service.brand;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.controller.admin.brand.vo.*;
import cn.econets.blossom.module.product.dal.dataobject.brand.ProductBrandDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Product Brand Service Interface
 *
 */
public interface ProductBrandService {

    /**
     * Create a brand
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createBrand(@Valid ProductBrandCreateReqVO createReqVO);

    /**
     * Update brand
     *
     * @param updateReqVO Update information
     */
    void updateBrand(@Valid ProductBrandUpdateReqVO updateReqVO);

    /**
     * Delete brand
     *
     * @param id Number
     */
    void deleteBrand(Long id);

    /**
     * Get the brand
     *
     * @param id Number
     * @return Brand
     */
    ProductBrandDO getBrand(Long id);

    /**
     * Get brand list
     *
     * @param ids Number
     * @return Brand List
     */
    List<ProductBrandDO> getBrandList(Collection<Long> ids);

    /**
     * Get brand list
     *
     * @param listReqVO Request parameters
     * @return Brand List
     */
    List<ProductBrandDO> getBrandList(ProductBrandListReqVO listReqVO);

    /**
     * Verify whether the selected product category is legal
     *
     * @param id Classification Number
     */
    void validateProductBrand(Long id);

    /**
     * Get brand paging
     *
     * @param pageReqVO Paged query
     * @return Brand Pagination
     */
    PageResult<ProductBrandDO> getBrandPage(ProductBrandPageReqVO pageReqVO);

    /**
     * Get the brand list of the specified status
     *
     * @param status Status
     * @return  Return to brand list
     */
    List<ProductBrandDO> getBrandListByStatus(Integer status);
}
