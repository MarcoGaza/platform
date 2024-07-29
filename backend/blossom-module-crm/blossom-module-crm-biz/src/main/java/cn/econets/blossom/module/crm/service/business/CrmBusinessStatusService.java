package cn.econets.blossom.module.crm.service.business;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.crm.controller.admin.business.vo.status.CrmBusinessStatusPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.status.CrmBusinessStatusQueryVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.status.CrmBusinessStatusSaveReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessStatusDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Opportunity Status Service Interface
 *
 */
public interface CrmBusinessStatusService {

    /**
     * Creating opportunity status
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createBusinessStatus(@Valid CrmBusinessStatusSaveReqVO createReqVO);

    /**
     * Update opportunity status
     *
     * @param updateReqVO Update information
     */
    void updateBusinessStatus(@Valid CrmBusinessStatusSaveReqVO updateReqVO);

    /**
     * Delete opportunity status
     *
     * @param id Number
     */
    void deleteBusinessStatus(Long id);

    /**
     * Get business opportunity status
     *
     * @param id Number
     * @return Opportunity Status
     */
    CrmBusinessStatusDO getBusinessStatus(Long id);

    /**
     * Get the opportunity status page
     *
     * @param pageReqVO Paged query
     * @return Opportunity Status Paging
     */
    PageResult<CrmBusinessStatusDO> getBusinessStatusPage(CrmBusinessStatusPageReqVO pageReqVO);

    // TODO @ljlleo Commonly used ids Such queries，You can encapsulate separate methods，No need to go similar QueryVO，More convenient to use。
    // TODO @ljlleo Method name getBusinessStatusList
    /**
     * Get the opportunity status page
     *
     * @param queryVO Query parameters
     * @return Opportunity Status Paging
     */
    List<CrmBusinessStatusDO> selectList(CrmBusinessStatusQueryVO queryVO);

    /**
     * Get the opportunity status list
     *
     * @param ids Number array
     * @return Opportunity status list
     */
    List<CrmBusinessStatusDO> getBusinessStatusList(Collection<Long> ids);

}
