package cn.econets.blossom.module.product.service.property;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.product.controller.admin.property.vo.value.ProductPropertyValuePageReqVO;
import cn.econets.blossom.module.product.controller.admin.property.vo.value.ProductPropertyValueSaveReqVO;
import cn.econets.blossom.module.product.dal.dataobject.property.ProductPropertyValueDO;
import cn.econets.blossom.module.product.dal.mysql.property.ProductPropertyValueMapper;
import cn.econets.blossom.module.product.service.sku.ProductSkuService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.PROPERTY_VALUE_EXISTS;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.PROPERTY_VALUE_NOT_EXISTS;

/**
 * Product attribute value Service Implementation class
 *
 */
@Service
@Validated
public class ProductPropertyValueServiceImpl implements ProductPropertyValueService {

    @Resource
    private ProductPropertyValueMapper productPropertyValueMapper;

    @Resource
    @Lazy // Delayed loading，Avoid circular dependencies
    private ProductSkuService productSkuService;

    @Override
    public Long createPropertyValue(ProductPropertyValueSaveReqVO createReqVO) {
        // If the attribute value has been added，Return directly
        ProductPropertyValueDO dbValue = productPropertyValueMapper.selectByName(
                createReqVO.getPropertyId(), createReqVO.getName());
        if (dbValue != null) {
            return dbValue.getId();
        }

        // Newly added
        ProductPropertyValueDO value = BeanUtils.toBean(createReqVO, ProductPropertyValueDO.class);
        productPropertyValueMapper.insert(value);
        return value.getId();
    }

    @Override
    public void updatePropertyValue(ProductPropertyValueSaveReqVO updateReqVO) {
        validatePropertyValueExists(updateReqVO.getId());
        // Verify name uniqueness
        ProductPropertyValueDO value = productPropertyValueMapper.selectByName
                (updateReqVO.getPropertyId(), updateReqVO.getName());
        if (value != null && !value.getId().equals(updateReqVO.getId())) {
            throw exception(PROPERTY_VALUE_EXISTS);
        }

        // Update
        ProductPropertyValueDO updateObj = BeanUtils.toBean(updateReqVO, ProductPropertyValueDO.class);
        productPropertyValueMapper.updateById(updateObj);
        // Update sku Related properties
        productSkuService.updateSkuPropertyValue(updateObj.getId(), updateObj.getName());
    }

    @Override
    public void deletePropertyValue(Long id) {
        validatePropertyValueExists(id);
        productPropertyValueMapper.deleteById(id);
    }

    private void validatePropertyValueExists(Long id) {
        if (productPropertyValueMapper.selectById(id) == null) {
            throw exception(PROPERTY_VALUE_NOT_EXISTS);
        }
    }

    @Override
    public ProductPropertyValueDO getPropertyValue(Long id) {
        return productPropertyValueMapper.selectById(id);
    }

    @Override
    public List<ProductPropertyValueDO> getPropertyValueListByPropertyId(Collection<Long> propertyIds) {
        return productPropertyValueMapper.selectListByPropertyId(propertyIds);
    }

    @Override
    public Integer getPropertyValueCountByPropertyId(Long propertyId) {
        return productPropertyValueMapper.selectCountByPropertyId(propertyId);
    }

    @Override
    public PageResult<ProductPropertyValueDO> getPropertyValuePage(ProductPropertyValuePageReqVO pageReqVO) {
        return productPropertyValueMapper.selectPage(pageReqVO);
    }

    @Override
    public void deletePropertyValueByPropertyId(Long propertyId) {
        productPropertyValueMapper.deleteByPropertyId(propertyId);
    }

}
