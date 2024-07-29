package cn.econets.blossom.module.crm.service.receivable;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan.CrmReceivablePlanCreateReqVO;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan.CrmReceivablePlanPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.receivable.vo.plan.CrmReceivablePlanUpdateReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.dal.dataobject.receivable.CrmReceivablePlanDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * CRM Payment Refund Plan Service Interface
 *
 */
public interface CrmReceivablePlanService {

    /**
     * Create a payment collection plan
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createReceivablePlan(@Valid CrmReceivablePlanCreateReqVO createReqVO, Long userId);

    /**
     * Update payment plan
     *
     * @param updateReqVO Update information
     */
    void updateReceivablePlan(@Valid CrmReceivablePlanUpdateReqVO updateReqVO);

    /**
     * Delete payment plan
     *
     * @param id Number
     */
    void deleteReceivablePlan(Long id);

    /**
     * Get the payment plan
     *
     * @param id Number
     * @return Payment Refund Plan
     */
    CrmReceivablePlanDO getReceivablePlan(Long id);

    /**
     * Get the payment plan list
     *
     * @param ids Number
     * @return Payment collection plan list
     */
    List<CrmReceivablePlanDO> getReceivablePlanList(Collection<Long> ids);

    /**
     * Get the payment plan page
     *
     * Data permissions：Based on {@link CrmReceivablePlanDO} Read
     *
     * @param pageReqVO Paged query
     * @param userId    User ID
     * @return Payment Refund Plan Pagination
     */
    PageResult<CrmReceivablePlanDO> getReceivablePlanPage(CrmReceivablePlanPageReqVO pageReqVO, Long userId);

    /**
     * Get the payment plan page，Based on specified customers
     *
     * Data permissions：Based on {@link CrmCustomerDO} Read
     *
     * @param pageReqVO Paged query
     * @return Payment Refund Plan Pagination
     */
    PageResult<CrmReceivablePlanDO> getReceivablePlanPageByCustomerId(CrmReceivablePlanPageReqVO pageReqVO);

}
