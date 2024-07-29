package cn.econets.blossom.module.crm.service.customer;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.*;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerPoolConfigDO;
import cn.econets.blossom.module.crm.service.customer.bo.CrmCustomerCreateReqBO;
import cn.econets.blossom.module.crm.service.followup.bo.CrmUpdateFollowUpReqBO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Customer Service Interface
 *
 */
public interface CrmCustomerService {

    /**
     * Create a customer
     *
     * @param createReqVO Create information
     * @param userId      User Number
     * @return Number
     */
    Long createCustomer(@Valid CrmCustomerSaveReqVO createReqVO, Long userId);

    /**
     * Update Customer
     *
     * @param updateReqVO Update information
     */
    void updateCustomer(@Valid CrmCustomerSaveReqVO updateReqVO);

    /**
     * Delete customer
     *
     * @param id Number
     */
    void deleteCustomer(Long id);

    /**
     * Get customers
     *
     * @param id Number
     * @return Customer
     */
    CrmCustomerDO getCustomer(Long id);

    /**
     * Get customer list
     *
     * @param ids Customer number array
     * @return Customer List
     * @author ljlleo
     */
    List<CrmCustomerDO> getCustomerList(Collection<Long> ids);

    /**
     * Get customer paging
     *
     * @param pageReqVO Paged query
     * @param userId    User Number
     * @return Customer Paging
     */
    PageResult<CrmCustomerDO> getCustomerPage(CrmCustomerPageReqVO pageReqVO, Long userId);

    /**
     * Check if the customer exists
     *
     * @param customerId Customer id
     */
    void validateCustomer(Long customerId);

    /**
     * Customer transfer
     *
     * @param reqVO  Request
     * @param userId User ID
     */
    void transferCustomer(CrmCustomerTransferReqVO reqVO, Long userId);

    /**
     * Lock/Unlock customer
     *
     * @param lockReqVO Update information
     * @param userId    User Number
     */
    void lockCustomer(@Valid CrmCustomerLockReqVO lockReqVO, Long userId);

    /**
     * Update customer related information
     *
     * @param customerUpdateFollowUpReqBO Request
     */
    void updateCustomerFollowUp(CrmUpdateFollowUpReqBO customerUpdateFollowUpReqBO);

    /**
     * Create a customer
     *
     * @param customerCreateReq Request information
     * @param userId            User ID
     * @return Customer List
     */
    Long createCustomer(CrmCustomerCreateReqBO customerCreateReq, Long userId);

    /**
     * Batch import customers
     *
     * @param importCustomers Import customer list
     * @param importReqVO     Request
     * @return Import results
     */
    CrmCustomerImportRespVO importCustomerList(List<CrmCustomerImportExcelVO> importCustomers, CrmCustomerImportReqVO importReqVO);

    // ==================== High Seas Operations ====================

    /**
     * Customers are placed in the high seas
     *
     * @param id Customer Number
     */
    void putCustomerPool(Long id);

    /**
     * Get high seas customers
     *
     * @param ids         Array of customer numbers to be collected
     * @param ownerUserId Person in charge
     * @param isReceive   Yes/No
     */
    void receiveCustomer(List<Long> ids, Long ownerUserId, Boolean isReceive);

    /**
     * 【System】The customer automatically fell into the high seas
     *
     * @return Number of items that fell into the high seas
     */
    int autoPutCustomerPool();

    PageResult<CrmCustomerDO> getPutInPoolRemindCustomerPage(CrmCustomerPageReqVO pageVO,
                                                             CrmCustomerPoolConfigDO poolConfigDO,
                                                             Long loginUserId);
}
