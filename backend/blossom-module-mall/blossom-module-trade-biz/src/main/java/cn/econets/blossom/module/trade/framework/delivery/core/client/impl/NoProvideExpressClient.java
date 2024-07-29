package cn.econets.blossom.module.trade.framework.delivery.core.client.impl;

import cn.econets.blossom.module.trade.framework.delivery.core.client.ExpressClient;
import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.ExpressTrackQueryReqDTO;
import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.ExpressTrackRespDTO;

import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.trade.enums.ErrorCodeConstants.EXPRESS_CLIENT_NOT_PROVIDE;

/**
 * Unimplemented express client，Used to remind users that they need to access express service providers，
 *
 */
public class NoProvideExpressClient implements ExpressClient {

    @Override
    public List<ExpressTrackRespDTO> getExpressTrackList(ExpressTrackQueryReqDTO reqDTO) {
        throw exception(EXPRESS_CLIENT_NOT_PROVIDE);
    }

}
