package cn.econets.blossom.module.pay.service.refund;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.pay.core.client.dto.refund.PayRefundRespDTO;
import cn.econets.blossom.module.pay.api.refund.dto.PayRefundCreateReqDTO;
import cn.econets.blossom.module.pay.controller.admin.refund.vo.PayRefundExportReqVO;
import cn.econets.blossom.module.pay.controller.admin.refund.vo.PayRefundPageReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.refund.PayRefundDO;

import java.util.List;

/**
 * Refund order Service Interface
 *
 *
 */
public interface PayRefundService {

    /**
     * Get a refund order
     *
     * @param id Number
     * @return Refund order
     */
    PayRefundDO getRefund(Long id);

    /**
     * Get a refund order
     *
     * @param no External refund order number
     * @return Refund order
     */
    PayRefundDO getRefundByNo(String no);

    /**
     * Get the refund quantity of the specified application
     *
     * @param appId Application Number
     * @return Refund Quantity
     */
    Long getRefundCountByAppId(Long appId);

    /**
     * Get the refund order page
     *
     * @param pageReqVO Paged query
     * @return Refund order pagination
     */
    PageResult<PayRefundDO> getRefundPage(PayRefundPageReqVO pageReqVO);

    /**
     * Get refund order list, Used for Excel Export
     *
     * @param exportReqVO Query conditions
     * @return Refund order list
     */
    List<PayRefundDO> getRefundList(PayRefundExportReqVO exportReqVO);

    /**
     * Create a refund request
     *
     * @param reqDTO Refund application information
     * @return Refund order number
     */
    Long createPayRefund(PayRefundCreateReqDTO reqDTO);

    /**
     * Channel refund notification
     *
     * @param channelId  Channel Number
     * @param notify     Notification
     */
    void notifyRefund(Long channelId, PayRefundRespDTO notify);

    /**
     * Refund status of synchronous channel refunds
     *
     * @return Number of refunds synchronized to status，Including successful refund、Refund failed
     */
    int syncRefund();

}
