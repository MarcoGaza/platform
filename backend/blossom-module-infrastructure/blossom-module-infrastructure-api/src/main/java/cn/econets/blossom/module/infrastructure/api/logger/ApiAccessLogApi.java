package cn.econets.blossom.module.infrastructure.api.logger;


import cn.econets.blossom.module.infrastructure.api.logger.dto.ApiAccessLogCreateReqDTO;

import javax.validation.Valid;

/**
 * API Access log API Interface
 *
 */
public interface ApiAccessLogApi {

    /**
     * Create API Access log
     *
     * @param createDTO Create information
     */
    void createApiAccessLog(@Valid ApiAccessLogCreateReqDTO createDTO);

}
