package cn.econets.blossom.module.trade.convert.brokerage;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.member.api.user.dto.MemberUserRespDTO;
import cn.econets.blossom.module.trade.controller.admin.brokerage.vo.user.BrokerageUserRespVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserChildSummaryRespVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserMySummaryRespVO;
import cn.econets.blossom.module.trade.controller.app.brokerage.vo.user.AppBrokerageUserRankByUserCountRespVO;
import cn.econets.blossom.module.trade.dal.dataobject.brokerage.BrokerageUserDO;
import cn.econets.blossom.module.trade.service.brokerage.bo.BrokerageWithdrawSummaryRespBO;
import cn.econets.blossom.module.trade.service.brokerage.bo.UserBrokerageSummaryRespBO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Distribution User Convert
 *
 */
@Mapper
public interface BrokerageUserConvert {

    BrokerageUserConvert INSTANCE = Mappers.getMapper(BrokerageUserConvert.class);

    BrokerageUserRespVO convert(BrokerageUserDO bean);

    List<BrokerageUserRespVO> convertList(List<BrokerageUserDO> list);

    PageResult<BrokerageUserRespVO> convertPage(PageResult<BrokerageUserDO> page, Map<Long, MemberUserRespDTO> userMap, Map<Long, Long> brokerageUserCountMap, Map<Long, UserBrokerageSummaryRespBO> userOrderSummaryMap);

    default PageResult<BrokerageUserRespVO> convertPage(PageResult<BrokerageUserDO> pageResult,
                                                        Map<Long, MemberUserRespDTO> userMap,
                                                        Map<Long, Long> brokerageUserCountMap,
                                                        Map<Long, UserBrokerageSummaryRespBO> userOrderSummaryMap,
                                                        Map<Long, BrokerageWithdrawSummaryRespBO> withdrawMap) {
        PageResult<BrokerageUserRespVO> result = convertPage(pageResult, userMap, brokerageUserCountMap, userOrderSummaryMap);
        for (BrokerageUserRespVO userVO : result.getList()) {
            // User Information
            copyTo(userMap.get(userVO.getId()), userVO);
            // Number of promoted users
            userVO.setBrokerageUserCount(MapUtil.getInt(brokerageUserCountMap, userVO.getId(), 0));
            // Number of promotion orders、Promotion order amount
            Optional<UserBrokerageSummaryRespBO> orderSummaryOptional = Optional.ofNullable(userOrderSummaryMap.get(userVO.getId()));
            userVO.setBrokerageOrderCount(orderSummaryOptional.map(UserBrokerageSummaryRespBO::getCount).orElse(0))
                    .setBrokerageOrderPrice(orderSummaryOptional.map(UserBrokerageSummaryRespBO::getPrice).orElse(0));
            // Number of withdrawals、Amount withdrawn
            Optional<BrokerageWithdrawSummaryRespBO> withdrawSummaryOptional = Optional.ofNullable(withdrawMap.get(userVO.getId()));
            userVO.setWithdrawCount(withdrawSummaryOptional.map(BrokerageWithdrawSummaryRespBO::getCount).orElse(0))
                    .setWithdrawPrice(withdrawSummaryOptional.map(BrokerageWithdrawSummaryRespBO::getPrice).orElse(0));
        }
        return result;
    }

    default BrokerageUserRespVO copyTo(MemberUserRespDTO source, BrokerageUserRespVO target) {
        Optional.ofNullable(source).ifPresent(
                user -> target.setNickname(user.getNickname()).setAvatar(user.getAvatar()));
        return target;
    }

    default PageResult<AppBrokerageUserRankByUserCountRespVO> convertPage03(PageResult<AppBrokerageUserRankByUserCountRespVO> pageResult,
                                                                            Map<Long, MemberUserRespDTO> userMap) {
        pageResult.getList().forEach(vo -> copyTo(userMap.get(vo.getId()), vo));
        return pageResult;
    }

    void copyTo(MemberUserRespDTO from, @MappingTarget AppBrokerageUserRankByUserCountRespVO to);

    default AppBrokerageUserMySummaryRespVO convert(Integer yesterdayPrice, Integer withdrawPrice,
                                                    Long firstBrokerageUserCount, Long secondBrokerageUserCount,
                                                    BrokerageUserDO brokerageUser) {
        AppBrokerageUserMySummaryRespVO respVO = new AppBrokerageUserMySummaryRespVO()
                .setYesterdayPrice(ObjUtil.defaultIfNull(yesterdayPrice, 0))
                .setWithdrawPrice(ObjUtil.defaultIfNull(withdrawPrice, 0))
                .setBrokeragePrice(0).setFrozenPrice(0)
                .setFirstBrokerageUserCount(ObjUtil.defaultIfNull(firstBrokerageUserCount, 0L))
                .setSecondBrokerageUserCount(ObjUtil.defaultIfNull(secondBrokerageUserCount, 0L));
        // Settings brokeragePrice、frozenPrice Field
        Optional.ofNullable(brokerageUser)
                .ifPresent(user -> respVO.setBrokeragePrice(user.getBrokeragePrice()).setFrozenPrice(user.getFrozenPrice()));
        return respVO;
    }

    default void copyTo(List<AppBrokerageUserChildSummaryRespVO> list, Map<Long, MemberUserRespDTO> userMap) {
        for (AppBrokerageUserChildSummaryRespVO vo : list) {
            Optional.ofNullable(userMap.get(vo.getId())).ifPresent(user ->
                    vo.setNickname(user.getNickname()).setAvatar(user.getAvatar()));
        }
    }
}
