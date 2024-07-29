package cn.econets.blossom.module.infrastructure.dal.mysql.logger;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.mybatis.core.mapper.BaseMapperX;
import cn.econets.blossom.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.econets.blossom.module.infrastructure.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.logger.ApiAccessLogDO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * API Access log Mapper
 *
 */
@Mapper
public interface ApiAccessLogMapper extends BaseMapperX<ApiAccessLogDO> {

    default PageResult<ApiAccessLogDO> selectPage(ApiAccessLogPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ApiAccessLogDO>()
                .eqIfPresent(ApiAccessLogDO::getUserId, reqVO.getUserId())
                .eqIfPresent(ApiAccessLogDO::getUserType, reqVO.getUserType())
                .eqIfPresent(ApiAccessLogDO::getApplicationName, reqVO.getApplicationName())
                .likeIfPresent(ApiAccessLogDO::getRequestUrl, reqVO.getRequestUrl())
                .betweenIfPresent(ApiAccessLogDO::getBeginTime, reqVO.getBeginTime())
                .geIfPresent(ApiAccessLogDO::getDuration, reqVO.getDuration())
                .eqIfPresent(ApiAccessLogDO::getResultCode, reqVO.getResultCode())
                .orderByDesc(ApiAccessLogDO::getId)
        );
    }

    /**
     * Physically delete logs before the specified time
     *
     * @param createTime Maximum time
     * @param limit Number of deleted items，Prevent deleting too much at a time
     * @return Number of deleted items
     */
    @Delete("DELETE FROM infra_api_access_log WHERE create_time < #{createTime} LIMIT #{limit}")
    Integer deleteByCreateTimeLt(@Param("createTime") LocalDateTime createTime, @Param("limit") Integer limit);

}
