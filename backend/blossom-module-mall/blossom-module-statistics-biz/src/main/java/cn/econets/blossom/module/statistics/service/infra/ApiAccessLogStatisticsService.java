package cn.econets.blossom.module.statistics.service.infra;

import java.time.LocalDateTime;

/**
 * API Access log statistics Service Interface
 *
 */
public interface ApiAccessLogStatisticsService {

    // TODO Already review
    /**
     * Get the number of active users
     *
     * @param userType  User Type
     * @param beginTime Starting time
     * @param endTime   Deadline
     * @return Number of active users
     */
    Integer getUserCount(Integer userType, LocalDateTime beginTime, LocalDateTime endTime);

    // TODO Already review
    /**
     * Get the number of visiting users
     *
     * @param userType  User Type
     * @param beginTime Starting time
     * @param endTime   Deadline
     * @return Number of visiting users
     */
    Integer getIpCount(Integer userType, LocalDateTime beginTime, LocalDateTime endTime);

}
