package cn.econets.blossom.module.product.service.property;

import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.product.controller.admin.property.vo.property.ProductPropertyPageReqVO;
import cn.econets.blossom.module.product.controller.admin.property.vo.property.ProductPropertySaveReqVO;
import cn.econets.blossom.module.product.dal.dataobject.property.ProductPropertyDO;
import cn.econets.blossom.module.product.dal.mysql.property.ProductPropertyMapper;
import cn.econets.blossom.module.product.service.sku.ProductSkuService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.product.enums.ErrorCodeConstants.*;

/**
 * Product attribute item Service Implementation class
 *
 */
@Service
@Validated
public class ProductPropertyServiceImpl implements ProductPropertyService {

    @Resource
    private ProductPropertyMapper productPropertyMapper;

    @Resource
    @Lazy // Delayed loading，Solve the circular dependency problem
    private ProductPropertyValueService productPropertyValueService;

    @Resource
    private ProductSkuService productSkuService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createProperty(ProductPropertySaveReqVO createReqVO) {
        // If this property item has been added，Return directly
        ProductPropertyDO dbProperty = productPropertyMapper.selectByName(createReqVO.getName());
        if (dbProperty != null) {
            return dbProperty.getId();
        }

        // Insert
        ProductPropertyDO property = BeanUtils.toBean(createReqVO, ProductPropertyDO.class);
        productPropertyMapper.insert(property);
        // Return
        return property.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateProperty(ProductPropertySaveReqVO updateReqVO) {
        validatePropertyExists(updateReqVO.getId());
        // Check for duplicate names
        ProductPropertyDO property = productPropertyMapper.selectByName(updateReqVO.getName());
        if (property != null &&
                ObjUtil.notEqual(property.getId(), updateReqVO.getId())) {
            throw exception(PROPERTY_EXISTS);
        }

        // Update
        ProductPropertyDO updateObj = BeanUtils.toBean(updateReqVO, ProductPropertyDO.class);
        productPropertyMapper.updateById(updateObj);
        // Update sku Related properties
        productSkuService.updateSkuProperty(updateObj.getId(), updateObj.getName());
    }

    @Override
    public void deleteProperty(Long id) {
        // Check existence
        validatePropertyExists(id);
        // Check whether there is a specification value below
        if (productPropertyValueService.getPropertyValueCountByPropertyId(id) > 0) {
            throw exception(PROPERTY_DELETE_FAIL_VALUE_EXISTS);
        }

        // Delete
        productPropertyMapper.deleteById(id);
        // Synchronously delete attribute values
        productPropertyValueService.deletePropertyValueByPropertyId(id);
    }

    private void validatePropertyExists(Long id) {
        if (productPropertyMapper.selectById(id) == null) {
            throw exception(PROPERTY_NOT_EXISTS);
        }
    }

    @Override
    public PageResult<ProductPropertyDO> getPropertyPage(ProductPropertyPageReqVO pageReqVO) {
        return productPropertyMapper.selectPage(pageReqVO);
    }

    @Override
    public ProductPropertyDO getProperty(Long id) {
        return productPropertyMapper.selectById(id);
    }

    @Override
    public List<ProductPropertyDO> getPropertyList(Collection<Long> ids) {
        return productPropertyMapper.selectBatchIds(ids);
    }

}
