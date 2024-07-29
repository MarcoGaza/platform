package cn.econets.blossom.module.system.service.logger;


import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.api.logger.dto.OperateLogCreateReqDTO;
import cn.econets.blossom.module.system.api.logger.dto.OperateLogV2CreateReqDTO;
import cn.econets.blossom.module.system.api.logger.dto.OperateLogV2PageReqDTO;
import cn.econets.blossom.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import cn.econets.blossom.module.system.dal.dataobject.logger.OperateLogDO;
import cn.econets.blossom.module.system.dal.dataobject.logger.OperateLogV2DO;

/**
 * Operation log Service Interface
 *
 */
public interface OperateLogService {

    /**
     * Record operation log
     *
     * @param createReqDTO Operation log request
     */
    void createOperateLog(OperateLogCreateReqDTO createReqDTO);

    /**
     * Get the paging list of operation logs
     *
     * @param pageReqVO Pagination Conditions
     * @return Operation log paging list
     */
    PageResult<OperateLogDO> getOperateLogPage(OperateLogPageReqVO pageReqVO);

    // ======================= LOG V2 =======================

    /**
     * Record operation log V2
     *
     * @param createReqDTO Create request
     */
    void createOperateLogV2(OperateLogV2CreateReqDTO createReqDTO);

    /**
     * Get the paging list of operation logs
     *
     * @param pageReqVO Pagination conditions
     * @return Operation log paging list
     */
    PageResult<OperateLogV2DO> getOperateLogPage(OperateLogV2PageReqDTO pageReqVO);
}
