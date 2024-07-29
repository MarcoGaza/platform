package cn.econets.blossom.module.trade.service.brokerage;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.trade.controller.admin.brokerage.vo.withdraw.BrokerageWithdrawPageReqVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.withdraw.AppBrokerageWithdrawCreateReqVO;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageWithdrawDO;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawStatusEnum;
import cn.econets.blossom.module.trade.service.brokerage.bo.BrokerageWithdrawSummaryRespBO;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * Commission withdrawal Service Interface
 *
 */
public interface BrokerageWithdrawService {

    /**
     * 【Administrator】Audit commission withdrawal
     *
     * @param id          Commission Number
     * @param status      Review Status
     * @param auditReason Rejection reason
     */
    void auditBrokerageWithdraw(Integer id, BrokerageWithdrawStatusEnum status, String auditReason);

    /**
     * Get commission withdrawal
     *
     * @param id Number
     * @return Commission withdrawal
     */
    BrokerageWithdrawDO getBrokerageWithdraw(Integer id);

    /**
     * Get the commission withdrawal page
     *
     * @param pageReqVO Paged query
     * @return Commission withdrawal page
     */
    PageResult<BrokerageWithdrawDO> getBrokerageWithdrawPage(BrokerageWithdrawPageReqVO pageReqVO);

    /**
     * 【Member】Create commission withdrawal
     *
     * @param userId      Member User Number
     * @param createReqVO Create information
     * @return Commission withdrawal number
     */
    Long createBrokerageWithdraw(Long userId, AppBrokerageWithdrawCreateReqVO createReqVO);

    /**
     * According to userId，Summarize withdrawals of each user
     *
     * @param userIds User Number
     * @param status  Withdrawal status
     * @return User withdrawal summary List
     */
    List<BrokerageWithdrawSummaryRespBO> getWithdrawSummaryListByUserId(Collection<Long> userIds,
                                                                        BrokerageWithdrawStatusEnum status);

    /**
     * According to userId，Summarize the withdrawals of each user
     *
     * @param userIds User Number
     * @param status  Withdrawal status
     * @return User withdrawal summary Map
     */
    default Map<Long, BrokerageWithdrawSummaryRespBO> getWithdrawSummaryMapByUserId(Set<Long> userIds,
                                                                                    BrokerageWithdrawStatusEnum status) {
        return convertMap(getWithdrawSummaryListByUserId(userIds, status), BrokerageWithdrawSummaryRespBO::getUserId);
    }

}
