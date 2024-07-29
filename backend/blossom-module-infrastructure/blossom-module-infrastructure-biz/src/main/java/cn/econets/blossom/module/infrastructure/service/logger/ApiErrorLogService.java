package cn.econets.blossom.module.infrastructure.service.logger;


import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.infrastructure.api.logger.dto.ApiErrorLogCreateReqDTO;
import cn.econets.blossom.module.infrastructure.controller.admin.logger.vo.apierrorlog.ApiErrorLogPageReqVO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.logger.ApiErrorLogDO;

/**
 * API Error log Service Interface
 *
 */
public interface ApiErrorLogService {

    /**
     * Create API Error log
     *
     * @param createReqDTO API Error log
     */
    void createApiErrorLog(ApiErrorLogCreateReqDTO createReqDTO);

    /**
     * Get API Error log paging
     *
     * @param pageReqVO Paged query
     * @return API Error log paging
     */
    PageResult<ApiErrorLogDO> getApiErrorLogPage(ApiErrorLogPageReqVO pageReqVO);

    /**
     * Update API Error log processed
     *
     * @param id API Log number
     * @param processStatus Processing results
     * @param processUserId Handler
     */
    void updateApiErrorLogProcess(Long id, Integer processStatus, Long processUserId);

    /**
     * Clean up exceedDay Error log from days ago
     *
     * @param exceedDay After how many days, clean up will be done
     * @param deleteLimit Number of intervals to clean
     */
    Integer cleanErrorLog(Integer exceedDay, Integer deleteLimit);

}
