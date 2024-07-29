package cn.econets.blossom.module.crm.service.business;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.number.MoneyUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.business.vo.business.CrmBusinessPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.business.CrmBusinessSaveReqVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.business.CrmBusinessTransferReqVO;
import cn.econets.blossom.module.crm.convert.business.CrmBusinessConvert;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessDO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessProductDO;
import cn.econets.blossom.module.crm.dal.dataobject.contact.CrmContactBusinessDO;
import cn.econets.blossom.module.crm.dal.dataobject.product.CrmProductDO;
import cn.econets.blossom.module.crm.dal.mysql.business.CrmBusinessMapper;
import cn.econets.blossom.module.crm.dal.mysql.business.CrmBusinessProductMapper;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import cn.econets.blossom.module.crm.framework.permission.core.annotations.CrmPermission;
import cn.econets.blossom.module.crm.service.business.bo.CrmBusinessUpdateProductReqBO;
import cn.econets.blossom.module.crm.service.contact.CrmContactBusinessService;
import cn.econets.blossom.module.crm.service.contract.CrmContractService;
import cn.econets.blossom.module.crm.service.followup.bo.CrmUpdateFollowUpReqBO;
import cn.econets.blossom.module.crm.service.permission.CrmPermissionService;
import cn.econets.blossom.module.crm.service.permission.bo.CrmPermissionCreateReqBO;
import cn.econets.blossom.module.crm.service.product.CrmProductService;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.*;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.*;
import static cn.econets.blossom.module.crm.enums.LogRecordConstants.*;

/**
 * Business Opportunities Service Implementation class
 *
 */
@Service
@Validated
public class CrmBusinessServiceImpl implements CrmBusinessService {

    @Resource
    private CrmBusinessMapper businessMapper;
    @Resource
    private CrmBusinessProductMapper businessProductMapper;

    @Resource
    @Lazy // Delayed loading，Avoid circular dependencies
    private CrmContractService contractService;
    @Resource
    private CrmPermissionService permissionService;
    @Resource
    private CrmContactBusinessService contactBusinessService;
    @Resource
    private CrmProductService productService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = CRM_BUSINESS_TYPE, subType = CRM_BUSINESS_CREATE_SUB_TYPE, bizNo = "{{#business.id}}",
            success = CRM_BUSINESS_CREATE_SUCCESS)
    public Long createBusiness(CrmBusinessSaveReqVO createReqVO, Long userId) {
        createReqVO.setId(null);
        // 1. Insert business opportunity
        CrmBusinessDO business = BeanUtils.toBean(createReqVO, CrmBusinessDO.class).setOwnerUserId(userId);
        businessMapper.insert(business);
        // 1.2 Insert opportunity related products
        if (CollUtil.isNotEmpty(createReqVO.getProductItems())) { // If any
            List<CrmBusinessProductDO> productList = buildBusinessProductList(createReqVO.getProductItems(), business.getId());
            businessProductMapper.insertBatch(productList);
            // Update the total amount of contract goods
            businessMapper.updateById(new CrmBusinessDO().setId(business.getId()).setProductPrice(
                    getSumValue(productList, CrmBusinessProductDO::getTotalPrice, Integer::sum)));
        }
        // TODO @puhui999：On the contact's details page，If directly【New business opportunity】，You need to associate。We need to do something here CrmContactBusinessDO Table
        createContactBusiness(business.getId(), createReqVO.getContactId());

        // 2. Create data permissions
        // Set the person who is currently operating as the responsible person
        permissionService.createPermission(new CrmPermissionCreateReqBO().setBizType(CrmBizTypeEnum.CRM_BUSINESS.getType())
                .setBizId(business.getId()).setUserId(userId).setLevel(CrmPermissionLevelEnum.OWNER.getLevel()));

        // 3. Record operation log context
        LogRecordContext.putVariable("business", business);
        return business.getId();
    }

    // TODO @lzxhqs：CrmContactBusinessService Call this；Only in this way can the logic be converged；
    private void createContactBusiness(Long businessId, Long contactId) {
        CrmContactBusinessDO contactBusiness = new CrmContactBusinessDO();
        contactBusiness.setBusinessId(businessId);
        contactBusiness.setContactId(contactId);
        contactBusinessService.insert(contactBusiness);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = CRM_BUSINESS_TYPE, subType = CRM_BUSINESS_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = CRM_BUSINESS_UPDATE_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_BUSINESS, bizId = "#updateReqVO.id", level = CrmPermissionLevelEnum.WRITE)
    public void updateBusiness(CrmBusinessSaveReqVO updateReqVO) {
        // 1. Check existence
        CrmBusinessDO oldBusiness = validateBusinessExists(updateReqVO.getId());

        // 2.1 Update business opportunities
        CrmBusinessDO updateObj = BeanUtils.toBean(updateReqVO, CrmBusinessDO.class);
        businessMapper.updateById(updateObj);
        // 2.2 Update business opportunity related products
        List<CrmBusinessProductDO> productList = buildBusinessProductList(updateReqVO.getProductItems(), updateObj.getId());
        updateBusinessProduct(productList, updateObj.getId());

        // TODO @Business opportunity pending：If the status changes，Insert business opportunity status change record table
        // 3. Record operation log context
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldBusiness, CrmBusinessSaveReqVO.class));
        LogRecordContext.putVariable("businessName", oldBusiness.getName());
    }

    @Override
    public void updateBusinessFollowUpBatch(List<CrmUpdateFollowUpReqBO> updateFollowUpReqBOList) {
        businessMapper.updateBatch(CrmBusinessConvert.INSTANCE.convertList(updateFollowUpReqBOList));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = CRM_BUSINESS_TYPE, subType = CRM_BUSINESS_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = CRM_BUSINESS_DELETE_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_BUSINESS, bizId = "#id", level = CrmPermissionLevelEnum.OWNER)
    public void deleteBusiness(Long id) {
        // Check existence
        CrmBusinessDO business = validateBusinessExists(id);
        // TODO @Business opportunity pending：Need to check if there is any associated contract。CrmContractDO of businessId Field
        validateContractExists(id);

        // Delete
        businessMapper.deleteById(id);
        // Delete data permissions
        permissionService.deletePermission(CrmBizTypeEnum.CRM_BUSINESS.getType(), id);

        // Record operation log context
        LogRecordContext.putVariable("businessName", business.getName());
    }

    private void updateBusinessProduct(List<CrmBusinessProductDO> newProductList, Long businessId) {
        List<CrmBusinessProductDO> oldProducts = businessProductMapper.selectListByBusinessId(businessId);
        List<List<CrmBusinessProductDO>> diffList = CollectionUtils.diffList(oldProducts, newProductList, (oldValue, newValue) -> {
            boolean condition = ObjectUtil.equal(oldValue.getProductId(), newValue.getProductId());
            if (condition) {
                newValue.setId(oldValue.getId()); // Update requires original number
            }
            return condition;
        });
        if (CollUtil.isNotEmpty(diffList.get(0))) {
            businessProductMapper.insertBatch(diffList.get(0));
        }
        if (CollUtil.isNotEmpty(diffList.get(1))) {
            businessProductMapper.updateBatch(diffList.get(1));
        }
        if (CollUtil.isNotEmpty(diffList.get(2))) {
            businessProductMapper.deleteBatchIds(convertSet(diffList.get(2), CrmBusinessProductDO::getId));
        }
    }

    private List<CrmBusinessProductDO> buildBusinessProductList(List<CrmBusinessSaveReqVO.CrmBusinessProductItem> productItems,
                                                                Long businessId) {
        // Verify product existence
        Set<Long> productIds = convertSet(productItems, CrmBusinessSaveReqVO.CrmBusinessProductItem::getId);
        List<CrmProductDO> productList = productService.getProductList(productIds);
        if (CollUtil.isEmpty(productIds) || productList.size() != productIds.size()) {
            throw exception(PRODUCT_NOT_EXISTS);
        }
        Map<Long, CrmProductDO> productMap = convertMap(productList, CrmProductDO::getId);
        return convertList(productItems, productItem -> {
            CrmProductDO product = productMap.get(productItem.getId());
            return BeanUtils.toBean(product, CrmBusinessProductDO.class)
                    .setId(null).setProductId(productItem.getId()).setBusinessId(businessId)
                    .setCount(productItem.getCount()).setDiscountPercent(productItem.getDiscountPercent())
                    .setTotalPrice(MoneyUtils.calculator(product.getPrice(), productItem.getCount(), productItem.getDiscountPercent()));
        });
    }

    /**
     * The deletion verification contract is an associated contract
     *
     * @param businessId Business Opportunitiesid
     * @author lzxhqs
     */
    private void validateContractExists(Long businessId) {
        if (contractService.getContractCountByBusinessId(businessId) > 0) {
            throw exception(BUSINESS_CONTRACT_EXISTS);
        }
    }

    private CrmBusinessDO validateBusinessExists(Long id) {
        CrmBusinessDO crmBusiness = businessMapper.selectById(id);
        if (crmBusiness == null) {
            throw exception(BUSINESS_NOT_EXISTS);
        }
        return crmBusiness;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = CRM_BUSINESS_TYPE, subType = CRM_BUSINESS_TRANSFER_SUB_TYPE, bizNo = "{{#reqVO.id}}",
            success = CRM_BUSINESS_TRANSFER_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_BUSINESS, bizId = "#reqVO.id", level = CrmPermissionLevelEnum.OWNER)
    public void transferBusiness(CrmBusinessTransferReqVO reqVO, Long userId) {
        // 1 Check if the business opportunity exists
        CrmBusinessDO business = validateBusinessExists(reqVO.getId());

        // 2.1 Data Permission Transfer
        permissionService.transferPermission(
                CrmBusinessConvert.INSTANCE.convert(reqVO, userId).setBizType(CrmBizTypeEnum.CRM_BUSINESS.getType()));
        // 2.2 Set a new person in charge
        businessMapper.updateOwnerUserIdById(reqVO.getId(), reqVO.getNewOwnerUserId());

        // Record operation log context
        LogRecordContext.putVariable("business", business);
    }

    @Override
    public void updateBusinessProduct(CrmBusinessUpdateProductReqBO updateProductReqBO) {
        // Update business opportunity related products
        List<CrmBusinessProductDO> productList = buildBusinessProductList(
                BeanUtils.toBean(updateProductReqBO.getProductItems(), CrmBusinessSaveReqVO.CrmBusinessProductItem.class), updateProductReqBO.getId());
        updateBusinessProduct(productList, updateProductReqBO.getId());
    }

    //======================= Query related =======================

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_BUSINESS, bizId = "#id", level = CrmPermissionLevelEnum.READ)
    public CrmBusinessDO getBusiness(Long id) {
        return businessMapper.selectById(id);
    }

    @Override
    public List<CrmBusinessDO> getBusinessList(Collection<Long> ids, Long userId) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return businessMapper.selectBatchIds(ids, userId);
    }

    @Override
    public List<CrmBusinessDO> getBusinessList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return ListUtil.empty();
        }
        return businessMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CrmBusinessDO> getBusinessPage(CrmBusinessPageReqVO pageReqVO, Long userId) {
        return businessMapper.selectPage(pageReqVO, userId);
    }

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CUSTOMER, bizId = "#pageReqVO.customerId", level = CrmPermissionLevelEnum.READ)
    public PageResult<CrmBusinessDO> getBusinessPageByCustomerId(CrmBusinessPageReqVO pageReqVO) {
        return businessMapper.selectPageByCustomerId(pageReqVO);
    }

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CONTACT, bizId = "#pageReqVO.contactId", level = CrmPermissionLevelEnum.READ)
    public PageResult<CrmBusinessDO> getBusinessPageByContact(CrmBusinessPageReqVO pageReqVO) {
        // 1. Query the associated opportunity number
        List<CrmContactBusinessDO> contactBusinessList = contactBusinessService.getContactBusinessListByContactId(
                pageReqVO.getContactId());
        if (CollUtil.isEmpty(contactBusinessList)) {
            return PageResult.empty();
        }
        // 2. Query business opportunities by page
        return businessMapper.selectPageByContactId(pageReqVO,
                convertSet(contactBusinessList, CrmContactBusinessDO::getBusinessId));
    }

    @Override
    public Long getBusinessCountByCustomerId(Long customerId) {
        return businessMapper.selectCount(CrmBusinessDO::getCustomerId, customerId);
    }

}
