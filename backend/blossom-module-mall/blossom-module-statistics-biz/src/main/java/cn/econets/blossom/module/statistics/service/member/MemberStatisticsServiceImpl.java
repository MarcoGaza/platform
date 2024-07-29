package cn.econets.blossom.module.statistics.service.member;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.ip.core.Area;
import cn.econets.blossom.framework.ip.core.enums.AreaTypeEnum;
import cn.econets.blossom.framework.ip.core.utils.AreaUtils;
import cn.econets.blossom.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import cn.econets.blossom.module.statistics.controller.admin.member.vo.*;
import cn.econets.blossom.module.statistics.convert.member.MemberStatisticsConvert;
import cn.econets.blossom.module.statistics.dal.mysql.member.MemberStatisticsMapper;
import cn.econets.blossom.module.statistics.service.infra.ApiAccessLogStatisticsService;
import cn.econets.blossom.module.statistics.service.member.bo.MemberAreaStatisticsRespBO;
import cn.econets.blossom.module.statistics.service.pay.PayWalletStatisticsService;
import cn.econets.blossom.module.statistics.service.pay.bo.RechargeSummaryRespBO;
import cn.econets.blossom.module.statistics.service.trade.TradeOrderStatisticsService;
import cn.econets.blossom.module.statistics.service.trade.TradeStatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static cn.econets.blossom.framework.common.util.collection.CollectionUtils.convertMap;

/**
 * Statistics of member information Service Implementation class
 *
 */
@Service
@Validated
public class MemberStatisticsServiceImpl implements MemberStatisticsService {

    @Resource
    private MemberStatisticsMapper memberStatisticsMapper;

    @Resource
    private PayWalletStatisticsService payWalletStatisticsService;
    @Resource
    private TradeStatisticsService tradeStatisticsService;
    @Resource
    private TradeOrderStatisticsService tradeOrderStatisticsService;
    @Resource
    private ApiAccessLogStatisticsService apiAccessLogStatisticsService;

    @Override
    public MemberSummaryRespVO getMemberSummary() {
        RechargeSummaryRespBO rechargeSummary = payWalletStatisticsService.getUserRechargeSummary(null, null);
        // TODO 1）Here are real-time statistics，It's not easy to walk TradeStatistics Table；2）Because this is placed in the mall，So only consider order data，According to trade_order of pay_price and paid to calculate；
        Integer expensePrice = tradeStatisticsService.getExpensePrice(null, null);
        Integer userCount = memberStatisticsMapper.selectUserCount(null, null);
        return MemberStatisticsConvert.INSTANCE.convert(rechargeSummary, expensePrice, userCount);
    }

    @Override
    public List<MemberAreaStatisticsRespVO> getMemberAreaStatisticsList() {
        // Statistics users
        // TODO Maybe we need to list the users in each province，All found out，Then go order Over there in；Because the calculation is based on these people；；Large user scale may not be good，But let's just do it this way for now = =
        Map<Integer, Integer> userCountMap = convertMap(memberStatisticsMapper.selectSummaryListByAreaId(),
                vo -> AreaUtils.getParentIdByType(vo.getAreaId(), AreaTypeEnum.PROVINCE),
                MemberAreaStatisticsRespBO::getUserCount, Integer::sum);
        // Statistical Orders
        Map<Integer, MemberAreaStatisticsRespBO> orderMap = convertMap(tradeOrderStatisticsService.getSummaryListByAreaId(),
                bo -> AreaUtils.getParentIdByType(bo.getAreaId(), AreaTypeEnum.PROVINCE),
                bo -> bo,
                (a, b) -> new MemberAreaStatisticsRespBO()
                        .setOrderCreateUserCount(a.getOrderCreateUserCount() + b.getOrderCreateUserCount())
                        .setOrderPayUserCount(a.getOrderPayUserCount() + b.getOrderPayUserCount())
                        .setOrderPayPrice(a.getOrderPayPrice() + b.getOrderPayPrice()));
        // Splicing data
        List<Area> areaList = AreaUtils.getByType(AreaTypeEnum.PROVINCE, area -> area);
        areaList.add(new Area().setId(null).setName("Unknown"));
        return MemberStatisticsConvert.INSTANCE.convertList(areaList, userCountMap, orderMap);
    }

    @Override
    public DataComparisonRespVO<MemberAnalyseDataRespVO> getMemberAnalyseComparisonData(LocalDateTime beginTime, LocalDateTime endTime) {
        // Current data
        MemberAnalyseDataRespVO vo = getMemberAnalyseData(beginTime, endTime);
        // Comparison data
        LocalDateTime referenceEndDate = beginTime.minusDays(1); // Reduce1Sky，Prevent time overlap
        LocalDateTime referenceBeginDate = referenceEndDate.minus(Duration.between(beginTime, endTime));
        MemberAnalyseDataRespVO reference = getMemberAnalyseData(
                LocalDateTimeUtil.beginOfDay(referenceBeginDate), LocalDateTimeUtil.endOfDay(referenceEndDate));
        return new DataComparisonRespVO<>(vo, reference);
    }

    private MemberAnalyseDataRespVO getMemberAnalyseData(LocalDateTime beginTime, LocalDateTime endTime) {
        Integer rechargeUserCount = Optional.ofNullable(payWalletStatisticsService.getUserRechargeSummary(beginTime, endTime))
                .map(RechargeSummaryRespBO::getRechargeUserCount).orElse(0);
        return new MemberAnalyseDataRespVO()
                .setRegisterUserCount(memberStatisticsMapper.selectUserCount(beginTime, endTime))
                .setVisitUserCount(apiAccessLogStatisticsService.getUserCount(UserTypeEnum.MEMBER.getValue(), beginTime, endTime))
                .setRechargeUserCount(rechargeUserCount);
    }

    @Override
    public List<MemberSexStatisticsRespVO> getMemberSexStatisticsList() {
        return memberStatisticsMapper.selectSummaryListBySex();
    }

    @Override
    public List<MemberTerminalStatisticsRespVO> getMemberTerminalStatisticsList() {
        return memberStatisticsMapper.selectSummaryListByRegisterTerminal();
    }

    @Override
    public List<MemberRegisterCountRespVO> getMemberRegisterCountList(LocalDateTime beginTime, LocalDateTime endTime) {
        return memberStatisticsMapper.selectListByCreateTimeBetween(beginTime, endTime);
    }

    @Override
    public DataComparisonRespVO<MemberCountRespVO> getUserCountComparison() {
        // Today's time range
        LocalDateTime beginOfToday = LocalDateTimeUtil.beginOfDay(LocalDateTime.now());
        LocalDateTime endOfToday = LocalDateTimeUtil.endOfDay(beginOfToday);
        // Yesterday's time range
        LocalDateTime beginOfYesterday = LocalDateTimeUtil.beginOfDay(beginOfToday.minusDays(1));
        LocalDateTime endOfYesterday = LocalDateTimeUtil.endOfDay(beginOfYesterday);
        return new DataComparisonRespVO<MemberCountRespVO>()
                .setValue(getUserCount(beginOfToday, endOfToday))
                .setReference(getUserCount(beginOfYesterday, endOfYesterday));
    }

    private MemberCountRespVO getUserCount(LocalDateTime beginTime, LocalDateTime endTime) {
        return new MemberCountRespVO()
                .setRegisterUserCount(memberStatisticsMapper.selectUserCount(beginTime, endTime))
                .setVisitUserCount(apiAccessLogStatisticsService.getIpCount(UserTypeEnum.MEMBER.getValue(), beginTime, endTime));
    }

}
