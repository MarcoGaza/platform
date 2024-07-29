package cn.econets.blossom.module.product.service.property;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.controller.admin.property.vo.property.*;
import cn.econets.blossom.module.product.dal.dataobject.property.ProductPropertyDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Product attribute item Service Interface
 *
 */
public interface ProductPropertyService {

    /**
     * Create property item
     * Attention，If the property item already exists，Just return its number directly
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createProperty(@Valid ProductPropertySaveReqVO createReqVO);

    /**
     * Update property items
     *
     * @param updateReqVO Update information
     */
    void updateProperty(@Valid ProductPropertySaveReqVO updateReqVO);

    /**
     * Delete attribute item
     *
     * @param id Number
     */
    void deleteProperty(Long id);

    /**
     * Get property name page
     *
     * @param pageReqVO Pagination Conditions
     * @return Property item paging
     */
    PageResult<ProductPropertyDO> getPropertyPage(ProductPropertyPageReqVO pageReqVO);

    /**
     * Get the property item of the specified number
     *
     * @param id Number
     * @return Property item
     */
    ProductPropertyDO getProperty(Long id);

    /**
     * A collection of attribute item numbers，Get the corresponding attribute item array
     *
     * @param ids A collection of attribute item numbers
     * @return Attribute item array
     */
    List<ProductPropertyDO> getPropertyList(Collection<Long> ids);

}
