package cn.econets.blossom.module.infrastructure.api.logger;

import cn.econets.blossom.module.infrastructure.api.logger.dto.ApiAccessLogCreateReqDTO;
import cn.econets.blossom.module.infrastructure.service.logger.ApiAccessLogService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

/**
 * API Access log API Implementation class
 *
 */
@Service
@Validated
public class ApiAccessLogApiImpl implements ApiAccessLogApi {

    @Resource
    private ApiAccessLogService apiAccessLogService;

    @Override
    public void createApiAccessLog(ApiAccessLogCreateReqDTO createDTO) {
        apiAccessLogService.createApiAccessLog(createDTO);
    }

}
