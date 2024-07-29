package cn.econets.blossom.module.pay.service.transfer;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.pay.api.transfer.dto.PayTransferCreateReqDTO;
import cn.econets.blossom.module.pay.controller.admin.transfer.vo.PayTransferCreateReqVO;
import cn.econets.blossom.module.pay.controller.admin.transfer.vo.PayTransferPageReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.transfer.PayTransferDO;

import javax.validation.Valid;

/**
 * Transfer Service Interface
 *
 *
 */
public interface PayTransferService {

    /**
     * Create a transfer order，And initiate a transfer
     *
     * At this time，Will initiate a call to the transfer channel
     *
     * @param reqVO Request
     * @param userIp User ip
     * @return Channel return results
     */
    PayTransferDO createTransfer(@Valid PayTransferCreateReqVO reqVO, String userIp);

    /**
     * Create a transfer order，And initiate a transfer
     *
     * @param reqDTO Create request
     * @return Transfer order number
     */
    Long createTransfer(@Valid PayTransferCreateReqDTO reqDTO);

    /**
     * Get transfer slip
     * @param id Transfer order number
     */
    PayTransferDO getTransfer(Long id);

    /**
     * Get the transfer order page
     *
     * @param pageReqVO Paged query
     * @return Transfer Order Pagination
     */
    PageResult<PayTransferDO> getTransferPage(PayTransferPageReqVO pageReqVO);

    /**
     * Synchronize channel transfer order status
     *
     * @return Number of transfers synchronized to the status，Including successful transfer、Transfer failed、Transferring
     */
    int syncTransfer();
}
