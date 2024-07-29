package cn.econets.blossom.module.crm.service.message;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.crm.controller.admin.backlog.vo.CrmTodayCustomerPageReqVO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;

import javax.validation.Valid;

/**
 * CRM To-do messages Service Interface
 *
 */
public interface CrmBacklogService {

    /**
     * According to【Contact Status】、【Scene Type】Filter customer pages
     *
     * @param pageReqVO Paged query
     * @return Pagination data
     */
    PageResult<CrmCustomerDO> getTodayCustomerPage(@Valid CrmTodayCustomerPageReqVO pageReqVO, Long userId);

}
