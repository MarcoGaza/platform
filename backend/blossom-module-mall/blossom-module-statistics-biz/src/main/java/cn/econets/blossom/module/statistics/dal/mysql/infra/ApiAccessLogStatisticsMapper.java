package cn.econets.blossom.module.statistics.dal.mysql.infra;

import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

// TODO api Access log，Will clean up now，Maybe you need a separate access table for business purposes；
/**
 * API Access log statistics Mapper
 *
 */
@Mapper
@SuppressWarnings("rawtypes")
public interface ApiAccessLogStatisticsMapper extends BaseMapperX {

    // TODO Already review
    Integer selectIpCountByUserTypeAndCreateTimeBetween(@Param("userType") Integer userType,
                                                        @Param("beginTime") LocalDateTime beginTime,
                                                        @Param("endTime") LocalDateTime endTime);

    // TODO Already review
    Integer selectUserCountByUserTypeAndCreateTimeBetween(@Param("userType") Integer userType,
                                                          @Param("beginTime") LocalDateTime beginTime,
                                                          @Param("endTime") LocalDateTime endTime);

}
