package cn.econets.blossom.module.crm.service.contact;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.crm.controller.admin.contact.vo.CrmContactPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.contact.vo.CrmContactSaveReqVO;
import cn.econets.blossom.module.crm.controller.admin.contact.vo.CrmContactTransferReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.contact.CrmContactDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.service.followup.bo.CrmUpdateFollowUpReqBO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * CRM Contact Person Service Interface
 *
 */
public interface CrmContactService {

    /**
     * Create contact
     *
     * @param createReqVO Create information
     * @param userId      User Number
     * @return Number
     */
    Long createContact(@Valid CrmContactSaveReqVO createReqVO, Long userId);

    /**
     * Update Contact
     *
     * @param updateReqVO Update information
     */
    void updateContact(@Valid CrmContactSaveReqVO updateReqVO);

    /**
     * Delete contact
     *
     * @param id Number
     */
    void deleteContact(Long id);

    /**
     * Contact transfer
     *
     * @param reqVO  Request
     * @param userId User Number
     */
    void transferContact(CrmContactTransferReqVO reqVO, Long userId);

    /**
     * Update customer contact person in charge
     *
     * @param customerId  Customer Number
     * @param ownerUserId User Number
     */
    void updateOwnerUserIdByCustomerId(Long customerId, Long ownerUserId);

    /**
     * Update contact follow-up information
     *
     * @param updateFollowUpReqBOList Follow-up information
     */
    void updateContactFollowUpBatch(List<CrmUpdateFollowUpReqBO> updateFollowUpReqBOList);

    /**
     * Get contacts
     *
     * @param id Number
     * @return Contact Person
     */
    CrmContactDO getContact(Long id);

    /**
     * Get contact list
     *
     * @param ids    Number
     * @param userId User ID
     * @return Contact List
     */
    List<CrmContactDO> getContactListByIds(Collection<Long> ids, Long userId);

    /**
     * Get contact list
     *
     * @param ids Number
     * @return Contact List
     */
    List<CrmContactDO> getContactListByIds(Collection<Long> ids);

    /**
     * Get contact list
     *
     * @return Contact List
     */
    List<CrmContactDO> getContactList();

    /**
     * Get contact list（Verify permissions）
     *
     * @param userId User ID
     * @return Contact List
     */
    List<CrmContactDO> getSimpleContactList(Long userId);

    /**
     * Get contact paging
     *
     * Data permissions：Based on {@link CrmContactDO}
     *
     * @param pageReqVO Paged query
     * @param userId    User ID
     * @return Contacts Paging
     */
    PageResult<CrmContactDO> getContactPage(CrmContactPageReqVO pageReqVO, Long userId);

    /**
     * Get contact paging
     *
     * Data permissions：Based on {@link CrmCustomerDO}
     *
     * @param pageVO Paged query
     * @return Contacts Paging
     */
    PageResult<CrmContactDO> getContactPageByCustomerId(CrmContactPageReqVO pageVO);

    /**
     * Get the number of contacts associated with the customer
     *
     * @param customerId Customer Number
     * @return Quantity
     */
    Long getContactCountByCustomerId(Long customerId);

}
