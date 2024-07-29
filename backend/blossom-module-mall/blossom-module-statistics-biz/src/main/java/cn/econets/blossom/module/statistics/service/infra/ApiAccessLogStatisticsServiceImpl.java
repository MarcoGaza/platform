package cn.econets.blossom.module.statistics.service.infra;

import cn.econets.blossom.module.statistics.dal.mysql.infra.ApiAccessLogStatisticsMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * API Access log statistics Service Implementation class
 *
 */
@Service
@Validated
public class ApiAccessLogStatisticsServiceImpl implements ApiAccessLogStatisticsService {

    @Resource
    private ApiAccessLogStatisticsMapper apiAccessLogStatisticsMapper;

    @Override
    public Integer getUserCount(Integer userType, LocalDateTime beginTime, LocalDateTime endTime) {
        return apiAccessLogStatisticsMapper.selectUserCountByUserTypeAndCreateTimeBetween(userType, beginTime, endTime);
    }

    @Override
    public Integer getIpCount(Integer userType, LocalDateTime beginTime, LocalDateTime endTime) {
        return apiAccessLogStatisticsMapper.selectIpCountByUserTypeAndCreateTimeBetween(userType, beginTime, endTime);
    }

}
