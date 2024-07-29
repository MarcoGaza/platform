package cn.econets.blossom.module.system.service.logger;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.econets.blossom.module.system.controller.admin.logger.vo.loginlog.LoginLogPageReqVO;
import cn.econets.blossom.module.system.dal.dataobject.logger.LoginLogDO;

import javax.validation.Valid;

/**
 * Login log Service Interface
 */
public interface LoginLogService {

    /**
     * Get login log paging
     *
     * @param pageReqVO Pagination conditions
     * @return Login log paging
     */
    PageResult<LoginLogDO> getLoginLogPage(LoginLogPageReqVO pageReqVO);

    /**
     * Create login log
     *
     * @param reqDTO Log information
     */
    void createLoginLog(@Valid LoginLogCreateReqDTO reqDTO);

}
