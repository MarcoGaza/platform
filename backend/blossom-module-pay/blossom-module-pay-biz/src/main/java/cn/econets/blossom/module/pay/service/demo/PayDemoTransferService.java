package cn.econets.blossom.module.pay.service.demo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.pay.controller.admin.demo.vo.transfer.PayDemoTransferCreateReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.demo.PayDemoTransferDO;

import javax.validation.Valid;

/**
 * Example transfer business Service Interface
 *
 *
 */
public interface PayDemoTransferService {

    /**
     * Create a sample order for transfer business
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createDemoTransfer(@Valid PayDemoTransferCreateReqVO createReqVO);

    /**
     * Get the example order paging of the transfer business
     *
     * @param pageVO Pagination query parameters
     */
    PageResult<PayDemoTransferDO> getDemoTransferPage(PageParam pageVO);

    /**
     * Update the transfer status of the transfer business example order
     *
     * @param id Number
     * @param payTransferId Transfer order number
     */
    void updateDemoTransferStatus(Long id, Long payTransferId);
}
