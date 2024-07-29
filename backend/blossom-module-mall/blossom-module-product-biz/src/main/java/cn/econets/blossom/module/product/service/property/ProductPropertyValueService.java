package cn.econets.blossom.module.product.service.property;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.product.controller.admin.property.vo.value.ProductPropertyValuePageReqVO;
import cn.econets.blossom.module.product.controller.admin.property.vo.value.ProductPropertyValueSaveReqVO;
import cn.econets.blossom.module.product.dal.dataobject.property.ProductPropertyValueDO;

import java.util.Collection;
import java.util.List;

/**
 * Product attribute value Service Interface
 *
 */
public interface ProductPropertyValueService {

    /**
     * Create attribute value
     * Attention，If the attribute value already exists，Just return its number directly
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createPropertyValue(ProductPropertyValueSaveReqVO createReqVO);

    /**
     * Update property value
     *
     * @param updateReqVO Update information
     */
    void updatePropertyValue(ProductPropertyValueSaveReqVO updateReqVO);

    /**
     * Delete attribute value
     *
     * @param id Number
     */
    void deletePropertyValue(Long id);

    /**
     * Get property value
     *
     * @param id Number
     * @return Property value
     */
    ProductPropertyValueDO getPropertyValue(Long id);

    /**
     * Array according to attribute item number，Get a list of attribute values
     *
     * @param propertyIds Attribute item number array
     * @return Property value list
     */
    List<ProductPropertyValueDO> getPropertyValueListByPropertyId(Collection<Long> propertyIds);

    /**
     * According to the attribute item number，Number of active attribute values
     *
     * @param propertyId Number of attribute items
     * @return Number of attribute values
     */
    Integer getPropertyValueCountByPropertyId(Long propertyId);

    /**
     * Get the paging of attribute values
     *
     * @param pageReqVO Query conditions
     * @return Pagination of property values
     */
    PageResult<ProductPropertyValueDO> getPropertyValuePage(ProductPropertyValuePageReqVO pageReqVO);

    /**
     * Delete the attribute values ​​under the specified attribute item number
     *
     * @param propertyId The number of the property item
     */
    void deletePropertyValueByPropertyId(Long propertyId);

}
