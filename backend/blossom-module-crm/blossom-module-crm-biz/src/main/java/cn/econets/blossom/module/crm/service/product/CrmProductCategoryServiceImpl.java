package cn.econets.blossom.module.crm.service.product;

import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.product.vo.category.CrmProductCategoryCreateReqVO;
import cn.econets.blossom.module.crm.controller.admin.product.vo.category.CrmProductCategoryListReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.product.CrmProductCategoryDO;
import cn.econets.blossom.module.crm.dal.mysql.product.CrmProductCategoryMapper;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.crm.dal.dataobject.product.CrmProductCategoryDO.PARENT_ID_NULL;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.*;
import static cn.econets.blossom.module.crm.enums.LogRecordConstants.*;

/**
 * CRM Product Category Service Implementation class
 *
 */
@Service
@Validated
public class CrmProductCategoryServiceImpl implements CrmProductCategoryService {

    @Resource(name = "crmProductCategoryMapper")
    private CrmProductCategoryMapper productCategoryMapper;

    @Resource
    @Lazy // Delayed loading，Solve the circular dependency problem
    private CrmProductService crmProductService;

    @Override
    @LogRecord(type = CRM_PRODUCT_CATEGORY_TYPE, subType = CRM_PRODUCT_CATEGORY_CREATE_SUB_TYPE, bizNo = "{{#productCategoryId}}",
            success = CRM_PRODUCT_CATEGORY_CREATE_SUCCESS)
    public Long createProductCategory(CrmProductCategoryCreateReqVO createReqVO) {
        // 1.1 Check if the parent category exists
        validateParentProductCategory(createReqVO.getParentId());
        // 1.2 Does the category name exist?
        validateProductNameExists(null, createReqVO.getParentId(), createReqVO.getName());

        // 2. Insert category
        CrmProductCategoryDO category = BeanUtils.toBean(createReqVO, CrmProductCategoryDO.class);
        productCategoryMapper.insert(category);

        // 3. Record operation log context
        LogRecordContext.putVariable("productCategoryId", category.getId());
        return category.getId();
    }

    @Override
    @LogRecord(type = CRM_PRODUCT_CATEGORY_TYPE, subType = CRM_PRODUCT_CATEGORY_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = CRM_PRODUCT_CATEGORY_UPDATE_SUCCESS)
    public void updateProductCategory(CrmProductCategoryCreateReqVO updateReqVO) {
        // 1.1 Verify existence
        validateProductCategoryExists(updateReqVO.getId());
        // 1.2 Check if the parent category exists
        validateParentProductCategory(updateReqVO.getParentId());
        // 1.3 Does the category name exist?
        validateProductNameExists(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // 2. Update category
        CrmProductCategoryDO updateObj = BeanUtils.toBean(updateReqVO, CrmProductCategoryDO.class);
        productCategoryMapper.updateById(updateObj);
    }

    private void validateProductCategoryExists(Long id) {
        if (productCategoryMapper.selectById(id) == null) {
            throw exception(PRODUCT_CATEGORY_NOT_EXISTS);
        }
    }

    private void validateParentProductCategory(Long id) {
        // If it is a root category，No verification required
        if (Objects.equals(id, PARENT_ID_NULL)) {
            return;
        }
        // The parent category does not exist
        CrmProductCategoryDO category = productCategoryMapper.selectById(id);
        if (category == null) {
            throw exception(PRODUCT_CATEGORY_PARENT_NOT_EXISTS);
        }
        // The parent category cannot be a secondary category
        if (!Objects.equals(category.getParentId(), PARENT_ID_NULL)) {
            throw exception(PRODUCT_CATEGORY_PARENT_NOT_FIRST_LEVEL);
        }
    }

    private void validateProductNameExists(Long id, Long parentId, String name) {
        CrmProductCategoryDO category = productCategoryMapper.selectByParentIdAndName(parentId, name);
        if (category == null
            || category.getId().equals(id)) {
            return;
        }
        throw exception(PRODUCT_CATEGORY_EXISTS);
    }

    @Override
    @LogRecord(type = CRM_PRODUCT_CATEGORY_TYPE, subType = CRM_PRODUCT_CATEGORY_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = CRM_PRODUCT_CATEGORY_DELETE_SUCCESS)
    public void deleteProductCategory(Long id) {
        // 1.1 Check existence
        validateProductCategoryExists(id);
        // 1.2 Check if there are subcategories
        if (productCategoryMapper.selectCountByParentId(id) > 0) {
            throw exception(product_CATEGORY_EXISTS_CHILDREN);
        }
        // 1.3 Check whether it is used by the product
        if (crmProductService.getProductByCategoryId(id) !=null) {
            throw exception(PRODUCT_CATEGORY_USED);
        }
        // 2. Delete
        productCategoryMapper.deleteById(id);
    }

    @Override
    public CrmProductCategoryDO getProductCategory(Long id) {
        return productCategoryMapper.selectById(id);
    }

    @Override
    public List<CrmProductCategoryDO> getProductCategoryList(CrmProductCategoryListReqVO listReqVO) {
        return productCategoryMapper.selectList(listReqVO);
    }

    @Override
    public List<CrmProductCategoryDO> getProductCategoryList(Collection<Long> ids) {
        return productCategoryMapper.selectBatchIds(ids);
    }

}
