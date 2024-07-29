package cn.econets.blossom.module.trade.framework.delivery.core.client;

import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.ExpressTrackQueryReqDTO;
import cn.econets.blossom.module.trade.framework.delivery.core.client.dto.ExpressTrackRespDTO;

import java.util.List;

/**
 * Express client interface
 *
 */
public interface ExpressClient {

    /**
     * Real-time express query
     *
     * @param reqDTO Query request parameters
     */
    // TODO ：Return fields for reference https://doc.youzanyun.com/detail/API/0/5 Response data
    List<ExpressTrackRespDTO> getExpressTrackList(ExpressTrackQueryReqDTO reqDTO);

}
