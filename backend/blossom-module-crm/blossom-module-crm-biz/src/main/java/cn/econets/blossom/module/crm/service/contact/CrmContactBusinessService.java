package cn.econets.blossom.module.crm.service.contact;

import cn.econets.blossom.module.crm.controller.admin.contact.vo.CrmContactBusinessReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.contact.CrmContactBusinessDO;

import javax.validation.Valid;
import java.util.List;

/**
 * CRM The relationship between contacts and opportunities Service Interface
 *
 */
public interface CrmContactBusinessService {

    /**
     * Create an association between contacts and opportunities
     *
     * @param createReqVO Create information
     */
    void createContactBusinessList(@Valid CrmContactBusinessReqVO createReqVO);

    /**
     * Delete the association between contacts and opportunities
     *
     * @param deleteReqVO Delete information
     */
    void deleteContactBusinessList(@Valid CrmContactBusinessReqVO deleteReqVO);

    /**
     * Delete the association between contacts and opportunities，Based on contact number
     *
     * @param contactId Contact Number
     */
    void deleteContactBusinessByContactId(Long contactId);

    /**
     * Get the list of contacts and opportunities associated with each other，Based on contact number
     *
     * @param contactId Contact Number
     * @return Contact Opportunity Association
     */
    List<CrmContactBusinessDO> getContactBusinessListByContactId(Long contactId);

    /**
     * Add a new association between contacts and business opportunities
     *
     * @param contactBusiness Add contacts and business opportunities
     */
    void insert(CrmContactBusinessDO contactBusiness);

}
