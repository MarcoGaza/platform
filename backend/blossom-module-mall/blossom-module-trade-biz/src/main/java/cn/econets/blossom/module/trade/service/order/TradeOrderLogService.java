package cn.econets.blossom.module.trade.service.order;

import cn.econets.blossom.module.trade.dal.dataobject.order.TradeOrderLogDO;
import cn.econets.blossom.module.trade.service.order.bo.TradeOrderLogCreateReqBO;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * Transaction order log Service Interface
 *
 */
public interface TradeOrderLogService {

    /**
     * Create transaction order log
     *
     * @param logDTO Log Record\
     */
    @Async
    void createOrderLog(TradeOrderLogCreateReqBO logDTO);

    /**
     * Get the transaction order log list
     *
     * @param orderId Order number
     * @return Transaction order log list
     */
    List<TradeOrderLogDO> getOrderLogListByOrderId(Long orderId);

}
