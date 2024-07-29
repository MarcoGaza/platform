package cn.econets.blossom.module.system.api.logger;

import cn.econets.blossom.module.system.api.logger.dto.LoginLogCreateReqDTO;
import cn.econets.blossom.module.system.service.logger.LoginLogService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * Login log API Implementation class
 *
 */
@Service
@Validated
public class LoginLogApiImpl implements LoginLogApi {

    @Resource
    private LoginLogService loginLogService;

    @Override
    public void createLoginLog(LoginLogCreateReqDTO reqDTO) {
        loginLogService.createLoginLog(reqDTO);
    }

}
