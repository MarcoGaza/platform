package cn.econets.blossom.module.trade.dal.mysql.brokerage;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.pojo.SortingField;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.trade.controller.admin.brokerage.vo.user.BrokerageUserPageReqVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserChildSummaryRespVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserRankByUserCountRespVO;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageUserDO;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Distribution User Mapper
 *
 */
@Mapper
public interface BrokerageUserMapper extends BaseMapperX<BrokerageUserDO> {

    default PageResult<BrokerageUserDO> selectPage(BrokerageUserPageReqVO reqVO, List<Long> ids) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BrokerageUserDO>()
                .inIfPresent(BrokerageUserDO::getId, ids)
                .eqIfPresent(BrokerageUserDO::getBrokerageEnabled, reqVO.getBrokerageEnabled())
                .betweenIfPresent(BrokerageUserDO::getCreateTime, reqVO.getCreateTime())
                .betweenIfPresent(BrokerageUserDO::getBindUserTime, reqVO.getBindUserTime())
                .orderByDesc(BrokerageUserDO::getId));
    }

    /**
     * Update user available commission（Increase）
     *
     * @param id        User Number
     * @param incrCount Increase commission（Positive number）
     */
    default void updatePriceIncr(Long id, Integer incrCount) {
        Assert.isTrue(incrCount > 0);
        LambdaUpdateWrapper<BrokerageUserDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<BrokerageUserDO>()
                .setSql(" brokerage_price = brokerage_price + " + incrCount)
                .eq(BrokerageUserDO::getId, id);
        update(null, lambdaUpdateWrapper);
    }

    /**
     * Update user available commission（Reduce）
     * Attention：Theoretically, the commission may have been withdrawn，A negative number will be deducted at this time，Ensure that the platform does not cause losses
     *
     * @param id        User Number
     * @param incrCount Increase commission（Negative number）
     * @return Update number of rows
     */
    default int updatePriceDecr(Long id, Integer incrCount) {
        Assert.isTrue(incrCount < 0);
        LambdaUpdateWrapper<BrokerageUserDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<BrokerageUserDO>()
                .setSql(" brokerage_price = brokerage_price + " + incrCount) // Negative number，So use + Number
                .eq(BrokerageUserDO::getId, id);
        return update(null, lambdaUpdateWrapper);
    }

    /**
     * Update user frozen commission（Increase）
     *
     * @param id        User Number
     * @param incrCount Increase frozen commission（Positive number）
     */
    default void updateFrozenPriceIncr(Long id, Integer incrCount) {
        Assert.isTrue(incrCount > 0);
        LambdaUpdateWrapper<BrokerageUserDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<BrokerageUserDO>()
                .setSql(" frozen_price = frozen_price + " + incrCount)
                .eq(BrokerageUserDO::getId, id);
        update(null, lambdaUpdateWrapper);
    }

    /**
     * Update user frozen commission（Reduce）
     * Attention：Theoretically, the frozen commission may have been unfrozen，A negative number will be deducted at this time，Ensure that the platform does not cause losses
     *
     * @param id        User Number
     * @param incrCount Reduce frozen commission（Negative number）
     */
    default void updateFrozenPriceDecr(Long id, Integer incrCount) {
        Assert.isTrue(incrCount < 0);
        LambdaUpdateWrapper<BrokerageUserDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<BrokerageUserDO>()
                .setSql(" frozen_price = frozen_price + " + incrCount) // Negative number，So use + Number
                .eq(BrokerageUserDO::getId, id);
        update(null, lambdaUpdateWrapper);
    }

    /**
     * Update user frozen commission（Reduce）, Update user commission（Increase）
     *
     * @param id        User Number
     * @param incrCount Reduce frozen commission（Negative number）
     * @return Update number
     */
    default int updateFrozenPriceDecrAndPriceIncr(Long id, Integer incrCount) {
        Assert.isTrue(incrCount < 0);
        LambdaUpdateWrapper<BrokerageUserDO> lambdaUpdateWrapper = new LambdaUpdateWrapper<BrokerageUserDO>()
                .setSql(" frozen_price = frozen_price + " + incrCount + // Negative number，So use + Number
                        ", brokerage_price = brokerage_price + " + -incrCount) // Negative number，So use - Number
                .eq(BrokerageUserDO::getId, id)
                .ge(BrokerageUserDO::getFrozenPrice, -incrCount); // cas Logic
        return update(null, lambdaUpdateWrapper);
    }

    default void updateBindUserIdAndBindUserTimeToNull(Long id) {
        update(null, new LambdaUpdateWrapper<BrokerageUserDO>()
                .eq(BrokerageUserDO::getId, id)
                .set(BrokerageUserDO::getBindUserId, null).set(BrokerageUserDO::getBindUserTime, null));
    }

    default void updateEnabledFalseAndBrokerageTimeToNull(Long id) {
        update(null, new LambdaUpdateWrapper<BrokerageUserDO>()
                .eq(BrokerageUserDO::getId, id)
                .set(BrokerageUserDO::getBrokerageEnabled, false).set(BrokerageUserDO::getBrokerageTime, null));
    }

    @Select("SELECT bind_user_id AS id, COUNT(1) AS brokerageUserCount FROM trade_brokerage_user " +
            "WHERE bind_user_id IS NOT NULL AND deleted = FALSE " +
            "AND bind_user_time BETWEEN #{beginTime} AND #{endTime} " +
            "GROUP BY bind_user_id " +
            "ORDER BY brokerageUserCount DESC")
    IPage<AppBrokerageUserRankByUserCountRespVO> selectCountPageGroupByBindUserId(Page<?> page,
                                                                                  @Param("beginTime") LocalDateTime beginTime,
                                                                                  @Param("endTime") LocalDateTime endTime);

    /**
     * Subordinate distribution statistics（Pagination）
     *
     * @param bizType      Business Type
     * @param status       Status
     * @param ids          User ID list
     * @param sortingField Sort Field
     * @return Sub-level distribution statistics page list
     */
    IPage<AppBrokerageUserChildSummaryRespVO> selectSummaryPageByUserId(Page<?> page,
                                                                        @Param("bizType") Integer bizType,
                                                                        @Param("status") Integer status,
                                                                        @Param("ids") Collection<Long> ids,
                                                                        @Param("sortingField") SortingField sortingField);

    /**
     * Get bindUserIds Promoted user ID array
     *
     * @param bindUserIds Promoter number array
     * @return User ID array
     */
    default List<Long> selectIdListByBindUserIdIn(Collection<Long> bindUserIds) {
        return Convert.toList(Long.class,
                selectObjs(new LambdaQueryWrapperX<BrokerageUserDO>()
                        .select(Collections.singletonList(BrokerageUserDO::getId)) // Query only id Field，Accelerate return speed
                        .in(BrokerageUserDO::getBindUserId, bindUserIds)));
    }

}
