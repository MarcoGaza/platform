package cn.econets.blossom.module.crm.service.customer;

import cn.econets.blossom.module.crm.controller.admin.customer.vo.poolconfig.CrmCustomerPoolConfigSaveReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerPoolConfigDO;

import javax.validation.Valid;

/**
 * Customer high seas configuration Service Interface
 *
 */
public interface CrmCustomerPoolConfigService {

    /**
     * Get the customer's high seas configuration
     *
     * @return Customer high seas configuration
     */
    CrmCustomerPoolConfigDO getCustomerPoolConfig();

    /**
     * Save customer high seas configuration
     *
     * @param saveReqVO Update information
     */
    void saveCustomerPoolConfig(@Valid CrmCustomerPoolConfigSaveReqVO saveReqVO);

}
