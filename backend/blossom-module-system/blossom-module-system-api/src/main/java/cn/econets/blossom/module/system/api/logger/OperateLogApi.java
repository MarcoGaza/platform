package cn.econets.blossom.module.system.api.logger;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.api.logger.dto.OperateLogCreateReqDTO;
import cn.econets.blossom.module.system.api.logger.dto.OperateLogV2CreateReqDTO;
import cn.econets.blossom.module.system.api.logger.dto.OperateLogV2PageReqDTO;
import cn.econets.blossom.module.system.api.logger.dto.OperateLogV2RespDTO;

import javax.validation.Valid;

/**
 * Operation log API Interface
 *
 */
public interface OperateLogApi {

    /**
     * Create operation log
     *
     * @param createReqDTO Request
     */
    void createOperateLog(@Valid OperateLogCreateReqDTO createReqDTO);

    /**
     * Create operation log
     *
     * @param createReqDTO Request
     */
    void createOperateLogV2(@Valid OperateLogV2CreateReqDTO createReqDTO);

    /**
     * Get the operation log paging of the specified data of the specified module
     *
     * @param pageReqVO Request
     * @return Operation log paging
     */
    PageResult<OperateLogV2RespDTO> getOperateLogPage(OperateLogV2PageReqDTO pageReqVO);
}
