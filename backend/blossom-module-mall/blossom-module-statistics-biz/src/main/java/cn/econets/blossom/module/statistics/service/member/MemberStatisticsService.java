package cn.econets.blossom.module.statistics.service.member;

import cn.econets.blossom.module.statistics.controller.admin.common.vo.DataComparisonRespVO;
import cn.econets.blossom.module.statistics.controller.admin.member.vo.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Statistics of member information Service Interface
 *
 */
public interface MemberStatisticsService {

    // TODO Already review
    /**
     * Get member statistics（Real-time statistics）
     *
     * @return Member Statistics
     */
    MemberSummaryRespVO getMemberSummary();

    // TODO Already review
    /**
     * Get member analysis comparison data
     *
     * @param beginTime Starting time
     * @param endTime   Deadline
     * @return Member analysis comparison data
     */
    DataComparisonRespVO<MemberAnalyseDataRespVO> getMemberAnalyseComparisonData(LocalDateTime beginTime,
                                                                                 LocalDateTime endTime);

    // TODO Already review
    /**
     * By province，Get member statistics list
     *
     * @return Member statistics list
     */
    List<MemberAreaStatisticsRespVO> getMemberAreaStatisticsList();

    // TODO Already review
    /**
     * According to gender，Get member statistics list
     *
     * @return Member statistics list
     */
    List<MemberSexStatisticsRespVO> getMemberSexStatisticsList();

    /**
     * According to the terminal，Get member statistics list
     *
     * @return Member statistics list
     */
    List<MemberTerminalStatisticsRespVO> getMemberTerminalStatisticsList();

    // TODO Already review
    /**
     * Get the user registration quantity list
     *
     * @param beginTime Starting time
     * @param endTime   Deadline
     * @return Registration quantity list
     */
    List<MemberRegisterCountRespVO> getMemberRegisterCountList(LocalDateTime beginTime, LocalDateTime endTime);

    // TODO Already review
    /**
     * Get the user quantity statistics comparison
     *
     * @return User quantity statistics comparison
     */
    DataComparisonRespVO<MemberCountRespVO> getUserCountComparison();

}
