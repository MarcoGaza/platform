package cn.econets.blossom.module.crm.service.receivable;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.receivable.CrmReceivableCreateReqVO;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.receivable.CrmReceivablePageReqVO;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.receivable.CrmReceivableUpdateReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.dal.dataobject.receivable.CrmReceivableDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * CRM Payback Service Interface
 *
 */
public interface CrmReceivableService {

    /**
     * Create payment collection
     *
     * @param createReqVO Create information
     * @param userId      User Number
     * @return Number
     */
    Long createReceivable(@Valid CrmReceivableCreateReqVO createReqVO, Long userId);

    /**
     * Update payment
     *
     * @param updateReqVO Update information
     */
    void updateReceivable(@Valid CrmReceivableUpdateReqVO updateReqVO);

    /**
     * Delete payment
     *
     * @param id Number
     */
    void deleteReceivable(Long id);

    /**
     * Get payment
     *
     * @param id Number
     * @return Payback
     */
    CrmReceivableDO getReceivable(Long id);

    /**
     * Get the payment list
     *
     * @param ids Number
     * @return Payment Receipt List
     */
    List<CrmReceivableDO> getReceivableList(Collection<Long> ids);

    /**
     * Get the payment page
     *
     * Data permissions：Based on {@link CrmReceivableDO} Read
     *
     * @param pageReqVO Paged query
     * @param userId    User Number
     * @return Payment Collection Pagination
     */
    PageResult<CrmReceivableDO> getReceivablePage(CrmReceivablePageReqVO pageReqVO, Long userId);

    /**
     * Get the payment page，Based on specified customers
     *
     * Data permissions：Based on {@link CrmCustomerDO} Read
     *
     * @param pageReqVO Paged query
     * @return Payment Collection Pagination
     */
    PageResult<CrmReceivableDO> getReceivablePageByCustomerId(CrmReceivablePageReqVO pageReqVO);

}
