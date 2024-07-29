package cn.econets.blossom.module.system.api.logger;

import cn.econets.blossom.module.system.api.logger.dto.LoginLogCreateReqDTO;

import javax.validation.Valid;

/**
 * Login log API Interface
 *
 */
public interface LoginLogApi {

    /**
     * Create login log
     *
     * @param reqDTO Log information
     */
    void createLoginLog(@Valid LoginLogCreateReqDTO reqDTO);

}
