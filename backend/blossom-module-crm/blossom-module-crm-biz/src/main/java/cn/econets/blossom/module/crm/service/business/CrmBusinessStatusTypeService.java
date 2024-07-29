package cn.econets.blossom.module.crm.service.business;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.crm.controller.admin.business.vo.type.CrmBusinessStatusTypePageReqVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.type.CrmBusinessStatusTypeQueryVO;
import cn.econets.blossom.module.crm.controller.admin.business.vo.type.CrmBusinessStatusTypeSaveReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessStatusTypeDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * Opportunity status type Service Interface
 *
 */
public interface CrmBusinessStatusTypeService {

    /**
     * Create opportunity status type
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createBusinessStatusType(@Valid CrmBusinessStatusTypeSaveReqVO createReqVO);

    /**
     * Update opportunity status type
     *
     * @param updateReqVO Update information
     */
    void updateBusinessStatusType(@Valid CrmBusinessStatusTypeSaveReqVO updateReqVO);

    /**
     * Delete opportunity status type
     *
     * @param id Number
     */
    void deleteBusinessStatusType(Long id);

    /**
     * Get the opportunity status type
     *
     * @param id Number
     * @return Opportunity status type
     */
    CrmBusinessStatusTypeDO getBusinessStatusType(Long id);

    /**
     * Get the opportunity status type page
     *
     * @param pageReqVO Paged query
     * @return Opportunity status type paging
     */
    PageResult<CrmBusinessStatusTypeDO> getBusinessStatusTypePage(CrmBusinessStatusTypePageReqVO pageReqVO);

    // TODO @ljlleo Commonly used ids Such queries，Can encapsulate separate methods，No need to go similar QueryVO，More convenient to use。
    /**
     * Get the list of business opportunity status types
     *
     * @param queryVO Query parameters
     * @return Opportunity status type list
     */
    List<CrmBusinessStatusTypeDO> selectList(CrmBusinessStatusTypeQueryVO queryVO);

    /**
     * Get a list of business opportunity status types
     *
     * @param ids Number array
     * @return Opportunity status type list
     */
    List<CrmBusinessStatusTypeDO> getBusinessStatusTypeList(Collection<Long> ids);

}
