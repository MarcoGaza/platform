package cn.econets.blossom.module.crm.service.contract;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.bpm.api.listener.dto.BpmResultListenerRespDTO;
import cn.econets.blossom.module.crm.controller.admin.contract.vo.CrmContractPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.contract.vo.CrmContractSaveReqVO;
import cn.econets.blossom.module.crm.controller.admin.contract.vo.CrmContractTransferReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.contract.CrmContractDO;
import cn.econets.blossom.module.crm.dal.dataobject.contract.CrmContractProductDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.service.followup.bo.CrmUpdateFollowUpReqBO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * CRM Contract Service Interface
 *
 */
public interface CrmContractService {

    /**
     * Create a contract
     *
     * @param createReqVO Create information
     * @param userId      User Number
     * @return Number
     */
    Long createContract(@Valid CrmContractSaveReqVO createReqVO, Long userId);

    /**
     * Update Contract
     *
     * @param updateReqVO Update information
     */
    void updateContract(@Valid CrmContractSaveReqVO updateReqVO);

    /**
     * Delete contract
     *
     * @param id Number
     */
    void deleteContract(Long id);

    /**
     * Contract transfer
     *
     * @param reqVO  Request
     * @param userId User Number
     */
    void transferContract(CrmContractTransferReqVO reqVO, Long userId);

    /**
     * Update contract related information
     *
     * @param contractUpdateFollowUpReqBO Information
     */
    void updateContractFollowUp(CrmUpdateFollowUpReqBO contractUpdateFollowUpReqBO);

    /**
     * Initiate contract approval process
     *
     * @param id     Contract Number
     * @param userId User Number
     */
    void submitContract(Long id, Long userId);

    /**
     * Update contract process approval results
     *
     * @param event Approval results
     */
    void updateContractAuditStatus(BpmResultListenerRespDTO event);

    /**
     * Get the contract
     *
     * @param id Number
     * @return Contract
     */
    CrmContractDO getContract(Long id);

    /**
     * Get the contract list
     *
     * @param ids Number
     * @return Contract List
     */
    List<CrmContractDO> getContractList(Collection<Long> ids);

    /**
     * Get the contract page
     *
     * Data permissions：Based on {@link CrmContractDO} Read
     *
     * @param pageReqVO Paged query
     * @param userId    User ID
     * @return Contract Pagination
     */
    PageResult<CrmContractDO> getContractPage(CrmContractPageReqVO pageReqVO, Long userId);

    /**
     * Get the contract page，Based on specified customers
     *
     * Data permissions：Based on {@link CrmCustomerDO} Read
     *
     * @param pageReqVO Paged query
     * @return Contacts Paging
     */
    PageResult<CrmContractDO> getContractPageByCustomerId(CrmContractPageReqVO pageReqVO);

    /**
     * Query the number of contracts belonging to a certain contact
     *
     * @param contactId Contact PersonID
     * @return Contract
     */
    Long getContractCountByContactId(Long contactId);

    /**
     * Get the number of contracts for associated customers
     *
     * @param customerId Customer Number
     * @return Quantity
     */
    Long getContractCountByCustomerId(Long customerId);

    /**
     * Based on business opportunitiesIDGet the number of contracts for associated customers
     *
     * @param businessId Opportunity Number
     * @return Quantity
     */
    Long getContractCountByBusinessId(Long businessId);

    /**
     * Get the contract product list
     *
     * @param contactId Contract Number
     * @return Contract Product List
     */
    List<CrmContractProductDO> getContractProductListByContractId(Long contactId);

}
