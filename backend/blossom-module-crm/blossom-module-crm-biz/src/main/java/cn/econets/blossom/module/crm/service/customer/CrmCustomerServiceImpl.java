package cn.econets.blossom.module.crm.service.customer;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.date.LocalDateTimeUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.*;
import cn.econets.blossom.module.crm.convert.customer.CrmCustomerConvert;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerLimitConfigDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerPoolConfigDO;
import cn.econets.blossom.module.crm.dal.mysql.customer.CrmCustomerMapper;
import cn.econets.blossom.module.crm.enums.common.CrmBizTypeEnum;
import cn.econets.blossom.module.crm.enums.permission.CrmPermissionLevelEnum;
import cn.econets.blossom.module.crm.framework.permission.core.annotations.CrmPermission;
import cn.econets.blossom.module.crm.framework.permission.core.util.CrmPermissionUtils;
import cn.econets.blossom.module.crm.service.business.CrmBusinessService;
import cn.econets.blossom.module.crm.service.contact.CrmContactService;
import cn.econets.blossom.module.crm.service.contract.CrmContractService;
import cn.econets.blossom.module.crm.service.customer.bo.CrmCustomerCreateReqBO;
import cn.econets.blossom.module.crm.service.followup.bo.CrmUpdateFollowUpReqBO;
import cn.econets.blossom.module.crm.service.permission.CrmPermissionService;
import cn.econets.blossom.module.crm.service.permission.bo.CrmPermissionCreateReqBO;
import cn.econets.blossom.module.system.api.user.AdminUserApi;
import cn.econets.blossom.module.system.api.user.dto.AdminUserRespDTO;
import com.mzt.logapi.context.LogRecordContext;
import com.mzt.logapi.service.impl.DiffParseFunction;
import com.mzt.logapi.starter.annotation.LogRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.filterList;
import static cn.econets.blossom.module.crm.enums.ErrorCodeConstants.*;
import static cn.econets.blossom.module.crm.enums.LogRecordConstants.*;
import static cn.econets.blossom.module.crm.enums.customer.CrmCustomerLimitConfigTypeEnum.CUSTOMER_LOCK_LIMIT;
import static cn.econets.blossom.module.crm.enums.customer.CrmCustomerLimitConfigTypeEnum.CUSTOMER_OWNER_LIMIT;
import static java.util.Collections.singletonList;

/**
 * Customer Service Implementation class
 *
 */
@Service
@Slf4j
@Validated
public class CrmCustomerServiceImpl implements CrmCustomerService {

    @Resource
    private CrmCustomerMapper customerMapper;

    @Resource
    private CrmPermissionService permissionService;
    @Resource
    private CrmCustomerLimitConfigService customerLimitConfigService;
    @Resource
    @Lazy
    private CrmCustomerPoolConfigService customerPoolConfigService;
    @Resource
    @Lazy
    private CrmContactService contactService;
    @Resource
    @Lazy
    private CrmBusinessService businessService;
    @Resource
    @Lazy
    private CrmContractService contractService;

    @Resource
    private AdminUserApi adminUserApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = CRM_CUSTOMER_TYPE, subType = CRM_CUSTOMER_CREATE_SUB_TYPE, bizNo = "{{#customer.id}}",
            success = CRM_CUSTOMER_CREATE_SUCCESS)
    public Long createCustomer(CrmCustomerSaveReqVO createReqVO, Long userId) {
        createReqVO.setId(null);
        // 1. Check whether the number of customers has reached the upper limit
        validateCustomerExceedOwnerLimit(createReqVO.getOwnerUserId(), 1);

        // 2. Insert customer
        CrmCustomerDO customer = initCustomer(createReqVO, userId);
        customerMapper.insert(customer);

        // 3. Create data permissions
        permissionService.createPermission(new CrmPermissionCreateReqBO().setBizType(CrmBizTypeEnum.CRM_CUSTOMER.getType())
                .setBizId(customer.getId()).setUserId(userId).setLevel(CrmPermissionLevelEnum.OWNER.getLevel())); // Set the person who is currently operating as the responsible person

        // 4. Record operation log context
        LogRecordContext.putVariable("customer", customer);
        return customer.getId();
    }

    /**
     * Initialize the customer's common fields
     *
     * @param customer    Customer Information
     * @param ownerUserId Person in charge number
     * @return Customer Information DO
     */
    private static CrmCustomerDO initCustomer(Object customer, Long ownerUserId) {
        return BeanUtils.toBean(customer, CrmCustomerDO.class).setOwnerUserId(ownerUserId)
                .setLockStatus(false).setDealStatus(false).setContactLastTime(LocalDateTime.now());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = CRM_CUSTOMER_TYPE, subType = CRM_CUSTOMER_UPDATE_SUB_TYPE, bizNo = "{{#updateReqVO.id}}",
            success = CRM_CUSTOMER_UPDATE_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CUSTOMER, bizId = "#updateReqVO.id", level = CrmPermissionLevelEnum.WRITE)
    public void updateCustomer(CrmCustomerSaveReqVO updateReqVO) {
        Assert.notNull(updateReqVO.getId(), "Customer number cannot be empty");
        updateReqVO.setOwnerUserId(null);  // When updating，Put forward updateReqVO The responsible person is set to empty，Avoid modification
        // 1. Check existence
        CrmCustomerDO oldCustomer = validateCustomerExists(updateReqVO.getId());

        // 2. Update Customer
        CrmCustomerDO updateObj = BeanUtils.toBean(updateReqVO, CrmCustomerDO.class);
        customerMapper.updateById(updateObj);

        // 3. Record operation log context
        LogRecordContext.putVariable(DiffParseFunction.OLD_OBJECT, BeanUtils.toBean(oldCustomer, CrmCustomerSaveReqVO.class));
        LogRecordContext.putVariable("customerName", oldCustomer.getName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = CRM_CUSTOMER_TYPE, subType = CRM_CUSTOMER_DELETE_SUB_TYPE, bizNo = "{{#id}}",
            success = CRM_CUSTOMER_DELETE_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CUSTOMER, bizId = "#id", level = CrmPermissionLevelEnum.OWNER)
    public void deleteCustomer(Long id) {
        // 1.1 Check existence
        CrmCustomerDO customer = validateCustomerExists(id);
        // 1.2 Check references
        checkCustomerReference(id);

        // 2. Delete
        customerMapper.deleteById(id);
        // 3. Delete data permission
        permissionService.deletePermission(CrmBizTypeEnum.CRM_CUSTOMER.getType(), id);

        // 4. Record operation log context
        LogRecordContext.putVariable("customerName", customer.getName());
    }

    /**
     * Check if the customer is quoted
     *
     * @param id Customer Number
     */
    private void checkCustomerReference(Long id) {
        if (contactService.getContactCountByCustomerId(id) > 0) {
            throw exception(CUSTOMER_DELETE_FAIL_HAVE_REFERENCE, CrmBizTypeEnum.CRM_CONTACT.getName());
        }
        if (businessService.getBusinessCountByCustomerId(id) > 0) {
            throw exception(CUSTOMER_DELETE_FAIL_HAVE_REFERENCE, CrmBizTypeEnum.CRM_BUSINESS.getName());
        }
        if (contractService.getContractCountByCustomerId(id) > 0) {
            throw exception(CUSTOMER_DELETE_FAIL_HAVE_REFERENCE, CrmBizTypeEnum.CRM_CONTRACT.getName());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = CRM_CUSTOMER_TYPE, subType = CRM_CUSTOMER_TRANSFER_SUB_TYPE, bizNo = "{{#reqVO.id}}",
            success = CRM_CUSTOMER_TRANSFER_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CUSTOMER, bizId = "#reqVO.id", level = CrmPermissionLevelEnum.OWNER)
    public void transferCustomer(CrmCustomerTransferReqVO reqVO, Long userId) {
        // 1.1 Check if the customer exists
        CrmCustomerDO customer = validateCustomerExists(reqVO.getId());
        // 1.2 Check whether the number of customers has reached the upper limit
        validateCustomerExceedOwnerLimit(reqVO.getNewOwnerUserId(), 1);

        // 2.1 Data permission transfer
        permissionService.transferPermission(
                CrmCustomerConvert.INSTANCE.convert(reqVO, userId).setBizType(CrmBizTypeEnum.CRM_CUSTOMER.getType()));
        // 2.2 Reset the person in charge after transfer
        customerMapper.updateOwnerUserIdById(reqVO.getId(), reqVO.getNewOwnerUserId());

        // 3. Record transfer log
        LogRecordContext.putVariable("customer", customer);
    }

    @Override
    @LogRecord(type = CRM_CUSTOMER_TYPE, subType = CRM_CUSTOMER_LOCK_SUB_TYPE, bizNo = "{{#lockReqVO.id}}",
            success = CRM_CUSTOMER_LOCK_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CUSTOMER, bizId = "#lockReqVO.id", level = CrmPermissionLevelEnum.OWNER)
    public void lockCustomer(CrmCustomerLockReqVO lockReqVO, Long userId) {
        // 1.1 Check if the current customer exists
        CrmCustomerDO customer = validateCustomerExists(lockReqVO.getId());
        // 1.2 Check whether the current repeated operation is locked/Unlocked status
        if (customer.getLockStatus().equals(lockReqVO.getLockStatus())) {
            throw exception(customer.getLockStatus() ? CUSTOMER_LOCK_FAIL_IS_LOCK : CUSTOMER_UNLOCK_FAIL_IS_UNLOCK);
        }
        // 1.3 Check lock upper limit。
        if (lockReqVO.getLockStatus()) {
            validateCustomerExceedLockLimit(userId);
        }

        // 2. Update lock status
        customerMapper.updateById(BeanUtils.toBean(lockReqVO, CrmCustomerDO.class));

        // 3. Record operation log context
        // tips: Because the old state is used here, the record is recorded in reverse order，That is lockStatus for true Then it is unlocked, otherwise it is locked
        LogRecordContext.putVariable("customer", customer);
    }

    @Override
    public void updateCustomerFollowUp(CrmUpdateFollowUpReqBO customerUpdateFollowUpReqBO) {
        customerMapper.updateById(BeanUtils.toBean(customerUpdateFollowUpReqBO, CrmCustomerDO.class).setId(customerUpdateFollowUpReqBO.getBizId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = CRM_CUSTOMER_TYPE, subType = CRM_CUSTOMER_CREATE_SUB_TYPE, bizNo = "{{#customer.id}}",
            success = CRM_CUSTOMER_CREATE_SUCCESS)
    public Long createCustomer(CrmCustomerCreateReqBO customerCreateReq, Long userId) {
        // 1. Insert customer
        CrmCustomerDO customer = BeanUtils.toBean(customerCreateReq, CrmCustomerDO.class).setOwnerUserId(userId)
                .setLockStatus(false).setDealStatus(false).setReceiveTime(LocalDateTime.now());
        customerMapper.insert(customer);

        // 2. Create data permissions
        permissionService.createPermission(new CrmPermissionCreateReqBO().setBizType(CrmBizTypeEnum.CRM_CUSTOMER.getType())
                .setBizId(customer.getId()).setUserId(userId).setLevel(CrmPermissionLevelEnum.OWNER.getLevel())); // Set the person who is currently operating as the responsible person

        // 3. Record operation log context
        LogRecordContext.putVariable("customer", customer);
        return customer.getId();
    }

    @Override
    public CrmCustomerImportRespVO importCustomerList(List<CrmCustomerImportExcelVO> importCustomers, CrmCustomerImportReqVO importReqVO) {
        if (CollUtil.isEmpty(importCustomers)) {
            throw exception(CUSTOMER_IMPORT_LIST_IS_EMPTY);
        }
        CrmCustomerImportRespVO respVO = CrmCustomerImportRespVO.builder().createCustomerNames(new ArrayList<>())
                .updateCustomerNames(new ArrayList<>()).failureCustomerNames(new LinkedHashMap<>()).build();
        importCustomers.forEach(importCustomer -> {
            // Verification，Judge whether there is a reason for non-compliance
            // TODO @puhui999：Can be used ValidationUtils Check parameters；May need to encapsulate a method，Return message；In this case，You can do it in CrmCustomerImportExcelVO Write the parameters that need to be verified；
            try {
                validateCustomerForCreate(importCustomer);
            } catch (ServiceException ex) {
                respVO.getFailureCustomerNames().put(importCustomer.getName(), ex.getMessage());
                return;
            }
            // Situation 1：Judge if it does not exist，Inserting
            CrmCustomerDO existCustomer = customerMapper.selectByCustomerName(importCustomer.getName());
            if (existCustomer == null) {
                // 1.1 Insert customer information
                CrmCustomerDO customer = initCustomer(importCustomer, importReqVO.getOwnerUserId());
                customerMapper.insert(customer);
                respVO.getCreateCustomerNames().add(importCustomer.getName());
                // 1.2 Create data permissions
                if (importReqVO.getOwnerUserId() != null) {
                    permissionService.createPermission(new CrmPermissionCreateReqBO().setBizType(CrmBizTypeEnum.CRM_CUSTOMER.getType())
                            .setBizId(customer.getId()).setUserId(importReqVO.getOwnerUserId()).setLevel(CrmPermissionLevelEnum.OWNER.getLevel()));
                }
                // 1.3 Record operation log
                getSelf().importCustomerLog(customer, false);
                return;
            }

            // Situation 2：If exists，Judge whether to allow update
            if (!importReqVO.getUpdateSupport()) {
                respVO.getFailureCustomerNames().put(importCustomer.getName(),
                        StrUtil.format(CUSTOMER_NAME_EXISTS.getMsg(), importCustomer.getName()));
                return;
            }
            // 2.1 Update customer information
            CrmCustomerDO updateCustomer = BeanUtils.toBean(importCustomer, CrmCustomerDO.class)
                    .setId(existCustomer.getId());
            customerMapper.updateById(updateCustomer);
            respVO.getUpdateCustomerNames().add(importCustomer.getName());
            // 2.2 Record operation log
            getSelf().importCustomerLog(updateCustomer, true);
        });
        return respVO;
    }

    /**
     * Record the operation log when importing customers
     *
     * @param customer Customer Information
     * @param isUpdate Whether to update；true - Update，false - Newly added
     */
    @LogRecord(type = CRM_CUSTOMER_TYPE, subType = CRM_CUSTOMER_IMPORT_SUB_TYPE, bizNo = "{{#customer.id}}",
            success = CRM_CUSTOMER_IMPORT_SUCCESS)
    public void importCustomerLog(CrmCustomerDO customer, boolean isUpdate) {
        LogRecordContext.putVariable("customer", customer);
        LogRecordContext.putVariable("isUpdate", isUpdate);
    }

    private void validateCustomerForCreate(CrmCustomerImportExcelVO importCustomer) {
        // Verify that the customer name cannot be empty
        if (StrUtil.isEmptyIfStr(importCustomer.getName())) {
            throw exception(CUSTOMER_CREATE_NAME_NOT_NULL);
        }
    }

    // ==================== High Seas Operations ====================

    @Override
    @Transactional(rollbackFor = Exception.class)
    @LogRecord(type = CRM_CUSTOMER_TYPE, subType = CRM_CUSTOMER_POOL_SUB_TYPE, bizNo = "{{#id}}",
            success = CRM_CUSTOMER_POOL_SUCCESS)
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CUSTOMER, bizId = "#id", level = CrmPermissionLevelEnum.OWNER)
    public void putCustomerPool(Long id) {
        // 1. Check existence
        CrmCustomerDO customer = customerMapper.selectById(id);
        if (customer == null) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }
        // 1.2. Check whether it is high seas data
        validateCustomerOwnerExists(customer, true);
        // 1.3. Check if the client is locked
        validateCustomerIsLocked(customer, true);

        // 2. Customers are placed in the high seas
        putCustomerPool(customer);

        // Record operation log context
        LogRecordContext.putVariable("customerName", customer.getName());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void receiveCustomer(List<Long> ids, Long ownerUserId, Boolean isReceive) {
        if (!isReceive && !CrmPermissionUtils.isCrmAdmin()) { // Only administrators can assign
            throw exception(CRM_PERMISSION_DENIED, CrmBizTypeEnum.CRM_CUSTOMER.getName());
        }

        // 1.1 Check existence
        List<CrmCustomerDO> customers = customerMapper.selectBatchIds(ids);
        if (customers.size() != ids.size()) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }
        // 1.2. Check if the person in charge exists
        adminUserApi.validateUserList(singletonList(ownerUserId));
        // 1.3. Verification status
        customers.forEach(customer -> {
            // Check if there is a responsible person
            validateCustomerOwnerExists(customer, false);
            // Check if it is locked
            validateCustomerIsLocked(customer, false);
            // Check transaction status
            validateCustomerDeal(customer);
        });
        // 1.4  Check whether the responsible person has reached the upper limit
        validateCustomerExceedOwnerLimit(ownerUserId, customers.size());

        // 2.1 Get high seas data
        List<CrmCustomerDO> updateCustomers = new ArrayList<>();
        List<CrmPermissionCreateReqBO> createPermissions = new ArrayList<>();
        customers.forEach(customer -> {
            // 2.1. Set the person in charge
            updateCustomers.add(new CrmCustomerDO().setId(customer.getId()).setOwnerUserId(ownerUserId));
            // 2.2. Create data permissions for responsible persons
            createPermissions.add(new CrmPermissionCreateReqBO().setBizType(CrmBizTypeEnum.CRM_CUSTOMER.getType())
                    .setBizId(customer.getId()).setUserId(ownerUserId).setLevel(CrmPermissionLevelEnum.OWNER.getLevel()));
        });
        // 2.2 Update customer responsible person
        customerMapper.updateBatch(updateCustomers);
        // 2.3 Create data permissions for responsible persons
        permissionService.createPermissionBatch(createPermissions);
        // TODO @Do you want to process the associated contacts?？？？

        // 3. Record operation log
        AdminUserRespDTO user = null;
        if (!isReceive) {
            user = adminUserApi.getUser(ownerUserId);
        }
        for (CrmCustomerDO customer : customers) {
            getSelf().receiveCustomerLog(customer, user == null ? null : user.getNickname());
        }
    }

    @Override
    public int autoPutCustomerPool() {
        CrmCustomerPoolConfigDO poolConfig = customerPoolConfigService.getCustomerPoolConfig();
        if (poolConfig == null || !poolConfig.getEnabled()) {
            return 0;
        }
        // 1.1 Get unblocked customers that are not on the high seas
        List<CrmCustomerDO> customerList = customerMapper.selectListByLockAndNotPool(Boolean.FALSE);
        // TODO @puhui999：Also got it down there sql Go to here；Write or Query，Not a big problem；Low 393 To 402；The reason is，Avoid querying too much useless data java In process；
        List<CrmCustomerDO> poolCustomerList = new ArrayList<>();
        poolCustomerList.addAll(filterList(customerList, customer ->
                !customer.getDealStatus() && (poolConfig.getDealExpireDays() - LocalDateTimeUtils.between(customer.getCreateTime())) <= 0));
        poolCustomerList.addAll(filterList(customerList, customer -> {
            if (!customer.getDealStatus()) { // Only transactions are processed here
                return false;
            }
            LocalDateTime lastTime = ObjUtil.defaultIfNull(customer.getContactLastTime(), customer.getCreateTime());
            return (poolConfig.getContactExpireDays() - LocalDateTimeUtils.between(lastTime)) <= 0;
        }));

        // 2. Put them into the high seas one by one
        int count = 0;
        for (CrmCustomerDO customer : poolCustomerList) {
            try {
                getSelf().putCustomerPool(customer);
                count++;
            } catch (Throwable e) {
                log.error("[autoPutCustomerPool][Customer Customer({}) Put into the high seas anomaly]", customer.getId(), e);
            }
        }
        return count;
    }

    private void putCustomerPool(CrmCustomerDO customer) {
        // 1. Set the person in charge to NULL
        int updateOwnerUserIncr = customerMapper.updateOwnerUserIdById(customer.getId(), null);
        if (updateOwnerUserIncr == 0) {
            throw exception(CUSTOMER_UPDATE_OWNER_USER_FAIL);
        }
        // 2. Delete the data permissions of the person in charge
        permissionService.deletePermission(CrmBizTypeEnum.CRM_CUSTOMER.getType(), customer.getId(),
                CrmPermissionLevelEnum.OWNER.getLevel());

        // 3. The person in charge of the contact，Also set to null。Because：Because after receiving it，The person in charge should also be associated，This piece and receiveCustomer It corresponds
        contactService.updateOwnerUserIdByCustomerId(customer.getId(), null);
    }

    @LogRecord(type = CRM_CUSTOMER_TYPE, subType = CRM_CUSTOMER_RECEIVE_SUB_TYPE, bizNo = "{{#customer.id}}",
            success = CRM_CUSTOMER_RECEIVE_SUCCESS)
    public void receiveCustomerLog(CrmCustomerDO customer, String ownerUserName) {
        // Record operation log context
        LogRecordContext.putVariable("customer", customer);
        LogRecordContext.putVariable("ownerUserName", ownerUserName);
    }

    //======================= Query related =======================

    @Override
    @CrmPermission(bizType = CrmBizTypeEnum.CRM_CUSTOMER, bizId = "#id", level = CrmPermissionLevelEnum.READ)
    public CrmCustomerDO getCustomer(Long id) {
        return customerMapper.selectById(id);
    }

    @Override
    public List<CrmCustomerDO> getCustomerList(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return customerMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<CrmCustomerDO> getCustomerPage(CrmCustomerPageReqVO pageReqVO, Long userId) {
        return customerMapper.selectPage(pageReqVO, userId);
    }

    public PageResult<CrmCustomerDO> getPutInPoolRemindCustomerPage(CrmCustomerPageReqVO pageReqVO,
                                                                    CrmCustomerPoolConfigDO poolConfigDO,
                                                                    Long userId) {
        return customerMapper.selectPutInPoolRemindCustomerPage(pageReqVO, poolConfigDO, userId);
    }

    // ======================= Verification related =======================

    /**
     * Check if the customer exists
     *
     * @param customerId Customer id
     */
    @Override
    public void validateCustomer(Long customerId) {
        validateCustomerExists(customerId);
    }

    private void validateCustomerOwnerExists(CrmCustomerDO customer, Boolean pool) {
        if (customer == null) { // Defend yourself
            throw exception(CUSTOMER_NOT_EXISTS);
        }
        // Check whether it is high seas data
        if (pool && customer.getOwnerUserId() == null) {
            throw exception(CUSTOMER_IN_POOL, customer.getName());
        }
        // The person in charge already exists
        if (!pool && customer.getOwnerUserId() != null) {
            throw exception(CUSTOMER_OWNER_EXISTS, customer.getName());
        }
    }

    private CrmCustomerDO validateCustomerExists(Long id) {
        CrmCustomerDO customerDO = customerMapper.selectById(id);
        if (customerDO == null) {
            throw exception(CUSTOMER_NOT_EXISTS);
        }
        return customerDO;
    }

    private void validateCustomerIsLocked(CrmCustomerDO customer, Boolean pool) {
        if (customer.getLockStatus()) {
            throw exception(pool ? CUSTOMER_LOCKED_PUT_POOL_FAIL : CUSTOMER_LOCKED, customer.getName());
        }
    }

    private void validateCustomerDeal(CrmCustomerDO customer) {
        if (customer.getDealStatus()) {
            throw exception(CUSTOMER_ALREADY_DEAL);
        }
    }

    /**
     * Check the number of customers a user has，Has the upper limit been reached?
     *
     * @param userId   User Number
     * @param newCount Additional quantity
     */
    private void validateCustomerExceedOwnerLimit(Long userId, int newCount) {
        List<CrmCustomerLimitConfigDO> limitConfigs = customerLimitConfigService.getCustomerLimitConfigListByUserId(
                CUSTOMER_OWNER_LIMIT.getType(), userId);
        if (CollUtil.isEmpty(limitConfigs)) {
            return;
        }
        Long ownerCount = customerMapper.selectCountByDealStatusAndOwnerUserId(null, userId);
        Long dealOwnerCount = customerMapper.selectCountByDealStatusAndOwnerUserId(true, userId);
        limitConfigs.forEach(limitConfig -> {
            long nowCount = limitConfig.getDealCountEnabled() ? ownerCount : ownerCount - dealOwnerCount;
            if (nowCount + newCount > limitConfig.getMaxCount()) {
                throw exception(CUSTOMER_OWNER_EXCEED_LIMIT);
            }
        });
    }

    /**
     * Check the number of customers locked by the user，Has the upper limit been reached?
     *
     * @param userId User ID
     */
    private void validateCustomerExceedLockLimit(Long userId) {
        List<CrmCustomerLimitConfigDO> limitConfigs = customerLimitConfigService.getCustomerLimitConfigListByUserId(
                CUSTOMER_LOCK_LIMIT.getType(), userId);
        if (CollUtil.isEmpty(limitConfigs)) {
            return;
        }
        Long lockCount = customerMapper.selectCountByLockStatusAndOwnerUserId(true, userId);
        Integer maxCount = CollectionUtils.getMaxValue(limitConfigs, CrmCustomerLimitConfigDO::getMaxCount);
        assert maxCount != null;
        if (lockCount >= maxCount) {
            throw exception(CUSTOMER_LOCK_EXCEED_LIMIT);
        }
    }


    /**
     * Get its own proxy object，Solved AOP Effectiveness Issues
     *
     * @return Myself
     */
    private CrmCustomerServiceImpl getSelf() {
        return SpringUtil.getBean(getClass());
    }

}
