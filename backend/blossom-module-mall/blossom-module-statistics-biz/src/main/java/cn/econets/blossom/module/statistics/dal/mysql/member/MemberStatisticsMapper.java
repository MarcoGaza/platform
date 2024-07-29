package cn.econets.blossom.module.statistics.dal.mysql.member;

import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.module.statistics.controller.admin.member.vo.MemberRegisterCountRespVO;
import cn.econets.blossom.module.statistics.controller.admin.member.vo.MemberSexStatisticsRespVO;
import cn.econets.blossom.module.statistics.controller.admin.member.vo.MemberTerminalStatisticsRespVO;
import cn.econets.blossom.module.statistics.service.member.bo.MemberAreaStatisticsRespBO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Statistics of member information Mapper
 *
 */
@Mapper
@SuppressWarnings("rawtypes")
public interface MemberStatisticsMapper extends BaseMapperX {

    // TODO Already review
    List<MemberAreaStatisticsRespBO> selectSummaryListByAreaId();

    // TODO Already review
    List<MemberSexStatisticsRespVO> selectSummaryListBySex();

    // TODO Already review
    List<MemberTerminalStatisticsRespVO> selectSummaryListByRegisterTerminal();

    // TODO Already review
    Integer selectUserCount(@Param("beginTime") LocalDateTime beginTime,
                            @Param("endTime") LocalDateTime endTime);

    // TODO Already review
    /**
     * Get the daily registration quantity list of users
     *
     * @param beginTime Start time
     * @param endTime End time
     * @return Daily registration quantity list
     */
    List<MemberRegisterCountRespVO> selectListByCreateTimeBetween(@Param("beginTime") LocalDateTime beginTime,
                                                                  @Param("endTime") LocalDateTime endTime);

}
