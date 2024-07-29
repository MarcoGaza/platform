package cn.econets.blossom.module.pay.service.demo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.pay.controller.admin.demo.vo.order.PayDemoOrderCreateReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.demo.PayDemoOrderDO;

import javax.validation.Valid;

/**
 * Sample Order Service Interface
 *
 *
 */
public interface PayDemoOrderService {

    /**
     * Create a sample order
     *
     * @param userId      User Number
     * @param createReqVO Create information
     * @return Number
     */
    Long createDemoOrder(Long userId, @Valid PayDemoOrderCreateReqVO createReqVO);

    /**
     * Get sample order
     *
     * @param id Number
     * @return Sample Order
     */
    PayDemoOrderDO getDemoOrder(Long id);

    /**
     * Get sample order paging
     *
     * @param pageReqVO Paged query
     * @return Sample order pagination
     */
    PageResult<PayDemoOrderDO> getDemoOrderPage(PageParam pageReqVO);

    /**
     * Update the sample order to be paid
     *
     * @param id Number
     * @param payOrderId Payment order number
     */
    void updateDemoOrderPaid(Long id, Long payOrderId);

    /**
     * Initiate a refund for the sample order
     *
     * @param id Number
     * @param userIp User Number
     */
    void refundDemoOrder(Long id, String userIp);

    /**
     * Update the example order to be refunded
     *
     * @param id Number
     * @param payRefundId Refund order number
     */
    void updateDemoOrderRefunded(Long id, Long payRefundId);

}
