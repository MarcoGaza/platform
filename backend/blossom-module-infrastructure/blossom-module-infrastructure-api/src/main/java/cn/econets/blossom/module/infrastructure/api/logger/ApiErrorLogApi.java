package cn.econets.blossom.module.infrastructure.api.logger;

import cn.econets.blossom.module.infrastructure.api.logger.dto.ApiErrorLogCreateReqDTO;

import javax.validation.Valid;

/**
 * API Error log API Interface
 *
 */
public interface ApiErrorLogApi {

    /**
     * Create API Error log
     *
     * @param createDTO Create information
     */
    void createApiErrorLog(@Valid ApiErrorLogCreateReqDTO createDTO);

}
