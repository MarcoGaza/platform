package cn.econets.blossom.module.infrastructure.api.logger;

import cn.econets.blossom.module.infrastructure.api.logger.dto.ApiErrorLogCreateReqDTO;
import cn.econets.blossom.module.infrastructure.service.logger.ApiErrorLogService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * API Access log API Interface
 *
 */
@Service
@Validated
public class ApiErrorLogApiImpl implements ApiErrorLogApi {

    @Resource
    private ApiErrorLogService apiErrorLogService;

    @Override
    public void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        apiErrorLogService.createApiErrorLog(createDTO);
    }

}
