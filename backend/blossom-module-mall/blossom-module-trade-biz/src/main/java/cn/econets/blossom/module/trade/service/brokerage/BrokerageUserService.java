package cn.econets.blossom.module.trade.service.brokerage;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.trade.controller.admin.brokerage.vo.user.BrokerageUserPageReqVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserChildSummaryPageReqVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserChildSummaryRespVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserRankByUserCountRespVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserRankPageReqVO;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageUserDO;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * Distribution User Service Interface
 *
 */
public interface BrokerageUserService {

    /**
     * Get distribution users
     *
     * @param id Number
     * @return Distribution User
     */
    BrokerageUserDO getBrokerageUser(Long id);

    /**
     * Get the distribution user list
     *
     * @param ids Number
     * @return Distribution user list
     */
    List<BrokerageUserDO> getBrokerageUserList(Collection<Long> ids);

    /**
     * Get the distribution user paging
     *
     * @param pageReqVO Paged query
     * @return Distribution user paging
     */
    PageResult<BrokerageUserDO> getBrokerageUserPage(BrokerageUserPageReqVO pageReqVO);

    /**
     * Modify promoter number
     *
     * @param id         User ID
     * @param bindUserId Promoter Number
     */
    void updateBrokerageUserId(Long id, Long bindUserId);

    /**
     * Modify promotion qualifications
     *
     * @param id      User Number
     * @param enabled Promotion Qualification
     */
    void updateBrokerageUserEnabled(Long id, Boolean enabled);

    /**
     * Get the user's promoter
     *
     * @param id User Number
     * @return User's promoter
     */
    BrokerageUserDO getBindBrokerageUser(Long id);

    /**
     * Update user commission
     *
     * @param id    User Number
     * @param price User Available Commission
     * @return Update results
     */
    boolean updateUserPrice(Long id, Integer price);

    /**
     * Update user frozen commission
     *
     * @param id          User Number
     * @param frozenPrice User freeze commission
     */
    void updateUserFrozenPrice(Long id, Integer frozenPrice);

    /**
     * Update user frozen commission（Reduce），Update user commission（Increase）
     *
     * @param id          User Number
     * @param frozenPrice Reduce frozen commission（Negative number）
     */
    void updateFrozenPriceDecrAndPriceIncr(Long id, Integer frozenPrice);

    /**
     * The number of promoted users obtained
     *
     * @param bindUserId Bound promoter number
     * @param level      Promote user level
     * @return Number of promoted users
     */
    Long getBrokerageUserCountByBindUserId(Long bindUserId, Integer level);

    /**
     * 【Member】Bind promoter
     *
     * @param userId       User Number
     * @param bindUserId   Promoter Number
     * @return Whether to bind
     */
    boolean bindBrokerageUser(@NotNull Long userId, @NotNull Long bindUserId);

    /**
     * Get whether the user is eligible for distribution
     *
     * @param userId User Number
     * @return Is it eligible for distribution?
     */
    Boolean getUserBrokerageEnabled(Long userId);

    /**
     * Get promoter ranking
     *
     * @param pageReqVO Paged query
     * @return Promoter ranking
     */
    PageResult<AppBrokerageUserRankByUserCountRespVO> getBrokerageUserRankPageByUserCount(AppBrokerageUserRankPageReqVO pageReqVO);

    /**
     * Get the lower-level distribution statistics page
     *
     * @param pageReqVO Paged query
     * @param userId    User Number
     * @return Subordinate distribution statistics paging
     */
    PageResult<AppBrokerageUserChildSummaryRespVO> getBrokerageUserChildSummaryPage(AppBrokerageUserChildSummaryPageReqVO pageReqVO, Long userId);
}
