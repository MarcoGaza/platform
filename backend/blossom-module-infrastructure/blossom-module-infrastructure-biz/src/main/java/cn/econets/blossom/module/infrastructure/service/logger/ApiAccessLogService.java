package cn.econets.blossom.module.infrastructure.service.logger;


import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.infrastructure.api.logger.dto.ApiAccessLogCreateReqDTO;
import cn.econets.blossom.module.infrastructure.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.logger.ApiAccessLogDO;

/**
 * API Access log Service Interface
 *
 */
public interface ApiAccessLogService {

    /**
     * Create API Access log
     *
     * @param createReqDTO API Access log
     */
    void createApiAccessLog(ApiAccessLogCreateReqDTO createReqDTO);

    /**
     * Obtain API Access log paging
     *
     * @param pageReqVO Paged query
     * @return API Access log paging
     */
    PageResult<ApiAccessLogDO> getApiAccessLogPage(ApiAccessLogPageReqVO pageReqVO);

    /**
     * Clean up exceedDay Access log from days ago
     *
     * @param exceedDay After how many days, clean up will be done
     * @param deleteLimit Number of intervals to clean
     */
    Integer cleanAccessLog(Integer exceedDay, Integer deleteLimit);

}
