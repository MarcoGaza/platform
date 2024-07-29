package cn.econets.blossom.module.trade.service.brokerage;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.trade.controller.admin.brokerage.vo.record.BrokerageRecordPageReqVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.record.AppBrokerageProductPriceRespVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserRankByPriceRespVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserRankPageReqVO;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageRecordDO;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordBizTypeEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordStatusEnum;
import cn.econets.blossom.module.trade.service.brokerage.bo.BrokerageAddReqBO;
import cn.econets.blossom.module.trade.service.brokerage.bo.UserBrokerageSummaryRespBO;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * Commission Record Service Interface
 *
 */
public interface BrokerageRecordService {

    /**
     * Get commission records
     *
     * @param id Number
     * @return Commission Record
     */
    BrokerageRecordDO getBrokerageRecord(Integer id);

    /**
     * Get commission record paging
     *
     * @param pageReqVO Paged query
     * @return Commission record paging
     */
    PageResult<BrokerageRecordDO> getBrokerageRecordPage(BrokerageRecordPageReqVO pageReqVO);

    /**
     * Increase commission【Multi-level commission】
     *
     * @param userId  Member Number
     * @param bizType Business Type
     * @param list    Request parameter list
     */
    void addBrokerage(Long userId, BrokerageRecordBizTypeEnum bizType, @Valid List<BrokerageAddReqBO> list);

    /**
     * Increase commission【Only for myself】
     *
     * @param userId         Member Number
     * @param bizType        Business Type
     * @param bizId          Business Number
     * @param brokeragePrice Commission
     * @param title          Title
     */
    void addBrokerage(Long userId, BrokerageRecordBizTypeEnum bizType, String bizId, Integer brokeragePrice, String title);

    /**
     * Reduce commission【Only for myself】
     *
     * @param userId         Member Number
     * @param bizType        Business Type
     * @param bizId          Business Number
     * @param brokeragePrice Commission
     * @param title          Title
     */
    default void reduceBrokerage(Long userId, BrokerageRecordBizTypeEnum bizType, String bizId, Integer brokeragePrice, String title) {
        addBrokerage(userId, bizType, bizId, -brokeragePrice, title);
    }

    /**
     * Cancel commission：Record the commission，The status has been changed to invalid
     *
     * @param userId  Member Number
     * @param bizType Business Type
     * @param bizId   Business Number
     */
    void cancelBrokerage(Long userId, BrokerageRecordBizTypeEnum bizType, String bizId);

    /**
     * Unfreeze commission：Record the commission to be settled，Status changed to settled
     *
     * @return The amount of unfrozen commission
     */
    int unfreezeRecord();

    /**
     * According to userId，Summarize each user's commission
     *
     * @param userIds User Number
     * @param bizType Business Type
     * @param status  Commission Status
     * @return User Commission Summary List
     */
    List<UserBrokerageSummaryRespBO> getUserBrokerageSummaryListByUserId(Collection<Long> userIds,
                                                                         Integer bizType, Integer status);

    /**
     * According to userId，Summarize each user's commission
     *
     * @param userIds User Number
     * @param bizType Business Type
     * @param status  Commission Status
     * @return User Commission Summary Map
     */
    default Map<Long, UserBrokerageSummaryRespBO> getUserBrokerageSummaryMapByUserId(Collection<Long> userIds,
                                                                                     Integer bizType, Integer status) {
        return convertMap(getUserBrokerageSummaryListByUserId(userIds, bizType, status),
                UserBrokerageSummaryRespBO::getUserId);
    }

    /**
     * Get the total commission of users
     *
     * @param userId    User Number
     * @param bizType   Business Type
     * @param status    Status
     * @param beginTime Start time
     * @param endTime   Deadline
     * @return Total user commission
     */
    Integer getSummaryPriceByUserId(Long userId, BrokerageRecordBizTypeEnum bizType, BrokerageRecordStatusEnum status,
                                    LocalDateTime beginTime, LocalDateTime endTime);

    /**
     * Get the paginated list of user commission rankings（Based on total commission）
     *
     * @param pageReqVO Paged query
     * @return Ranking Pagination
     */
    PageResult<AppBrokerageUserRankByPriceRespVO> getBrokerageUserChildSummaryPageByPrice(
            AppBrokerageUserRankPageReqVO pageReqVO);

    /**
     * Get the user's ranking（Based on total commission）
     *
     * @param userId User Number
     * @param times  Time Range
     * @return User ranking
     */
    Integer getUserRankByPrice(Long userId, LocalDateTime[] times);

    /**
     * Calculate after the product is purchased，The commission that promoters can get
     *
     * @param userId User Number
     * @param spuId  Product Number
     * @return User Commission
     */
    AppBrokerageProductPriceRespVO calculateProductBrokeragePrice(Long userId, Long spuId);

}
