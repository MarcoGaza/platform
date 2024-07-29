package cn.econets.blossom.module.pay.api.transfer;

import cn.econets.blossom.module.pay.api.transfer.dto.PayTransferCreateReqDTO;

import javax.validation.Valid;

/**
 * Transfer slip API Interface
 *
 *
 */
public interface PayTransferApi {

    /**
     * Create a transfer order
     *
     * @param reqDTO Create request
     * @return Transfer order number
     */
    Long createTransfer(@Valid PayTransferCreateReqDTO reqDTO);

}
