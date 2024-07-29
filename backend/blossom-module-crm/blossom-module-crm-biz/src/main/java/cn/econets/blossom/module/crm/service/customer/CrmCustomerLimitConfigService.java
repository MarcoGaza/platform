package cn.econets.blossom.module.crm.service.customer;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.limitconfig.CrmCustomerLimitConfigPageReqVO;
import cn.econets.blossom.module.crm.controller.admin.customer.vo.limitconfig.CrmCustomerLimitConfigSaveReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerLimitConfigDO;

import javax.validation.Valid;
import java.util.List;

/**
 * Customer restriction configuration Service Interface
 *
 */
public interface CrmCustomerLimitConfigService {

    /**
     * Create customer restriction configuration
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createCustomerLimitConfig(@Valid CrmCustomerLimitConfigSaveReqVO createReqVO);

    /**
     * Update customer restriction configuration
     *
     * @param updateReqVO Update information
     */
    void updateCustomerLimitConfig(@Valid CrmCustomerLimitConfigSaveReqVO updateReqVO);

    /**
     * Delete customer restriction configuration
     *
     * @param id Number
     */
    void deleteCustomerLimitConfig(Long id);

    /**
     * Get client restriction configuration
     *
     * @param id Number
     * @return Customer restriction configuration
     */
    CrmCustomerLimitConfigDO getCustomerLimitConfig(Long id);

    /**
     * Get customer restriction configuration page
     *
     * @param pageReqVO Paged query
     * @return Customer restriction configuration paging
     */
    PageResult<CrmCustomerLimitConfigDO> getCustomerLimitConfigPage(CrmCustomerLimitConfigPageReqVO pageReqVO);

    /**
     * Query the configuration list corresponding to the user
     *
     * @param type   Type
     * @param userId User Type
     */
    List<CrmCustomerLimitConfigDO> getCustomerLimitConfigListByUserId(Integer type, Long userId);

}
