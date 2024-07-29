package cn.econets.blossom.module.crm.service.business;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.crm.controller.admin.business.vo.business.CrmBusinessPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.business.CrmBusinessSaveReqVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.business.CrmBusinessTransferReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessDO;
import cn.econets.blossom.module.crm.dal.dataobject.contact.CrmContactDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.service.business.bo.CrmBusinessUpdateProductReqBO;
import cn.econets.blossom.module.crm.service.followup.bo.CrmUpdateFollowUpReqBO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Business Opportunities Service Interface
 *
 */
public interface CrmBusinessService {

    /**
     * Create business opportunity
     *
     * @param createReqVO Create information
     * @param userId      User Number
     * @return Number
     */
    Long createBusiness(@Valid CrmBusinessSaveReqVO createReqVO, Long userId);

    /**
     * Update business opportunities
     *
     * @param updateReqVO Update information
     */
    void updateBusiness(@Valid CrmBusinessSaveReqVO updateReqVO);

    /**
     * Update follow-up information on business opportunities
     *
     * @param updateFollowUpReqBOList Follow-up information
     */
    void updateBusinessFollowUpBatch(List<CrmUpdateFollowUpReqBO> updateFollowUpReqBOList);

    /**
     * Delete business opportunity
     *
     * @param id Number
     */
    void deleteBusiness(Long id);

    /**
     * Business Opportunity Transfer
     *
     * @param reqVO  Request
     * @param userId User Number
     */
    void transferBusiness(CrmBusinessTransferReqVO reqVO, Long userId);

    /**
     * Update business opportunity related products
     *
     * @param updateProductReqBO Request
     */
    void updateBusinessProduct(CrmBusinessUpdateProductReqBO updateProductReqBO);

    /**
     * Obtain business opportunities
     *
     * @param id Number
     * @return Business Opportunities
     */
    CrmBusinessDO getBusiness(Long id);

    /**
     * Get a list of business opportunities
     *
     * @param ids Number
     * @return Business Opportunity List
     */
    List<CrmBusinessDO> getBusinessList(Collection<Long> ids, Long userId);

    /**
     * Get a list of business opportunities
     *
     * @param ids Number
     * @return Business Opportunity List
     */
    List<CrmBusinessDO> getBusinessList(Collection<Long> ids);

    /**
     * Get business opportunity paging
     *
     * Data permissions：Based on {@link CrmBusinessDO}
     *
     * @param pageReqVO Paged query
     * @param userId    User Number
     * @return Business Opportunities Paging
     */
    PageResult<CrmBusinessDO> getBusinessPage(CrmBusinessPageReqVO pageReqVO, Long userId);

    /**
     * Get business opportunity paging，Based on specified customers
     *
     * Data permissions：Based on {@link CrmCustomerDO} Read
     *
     * @param pageReqVO Paged query
     * @return Business Opportunities Paging
     */
    PageResult<CrmBusinessDO> getBusinessPageByCustomerId(CrmBusinessPageReqVO pageReqVO);

    /**
     * Get business opportunity paging，Based on specified contacts
     *
     * Data permissions：Based on {@link CrmContactDO} Read
     *
     * @param pageReqVO Paging parameters
     * @return Business Opportunities Paging
     */
    PageResult<CrmBusinessDO> getBusinessPageByContact(CrmBusinessPageReqVO pageReqVO);

    /**
     * Get the number of business opportunities for associated customers
     *
     * @param customerId Customer Number
     * @return Quantity
     */
    Long getBusinessCountByCustomerId(Long customerId);

}
