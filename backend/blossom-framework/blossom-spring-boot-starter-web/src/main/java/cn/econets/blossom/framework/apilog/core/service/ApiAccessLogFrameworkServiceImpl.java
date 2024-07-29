package cn.econets.blossom.framework.apilog.core.service;

import cn.econets.blossom.module.infrastructure.api.logger.ApiAccessLogApi;
import cn.econets.blossom.module.infrastructure.api.logger.dto.ApiAccessLogCreateReqDTO;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * API Access log Framework Service Implementation class
 *
 * Based on {@link ApiAccessLogApi} Service，Record access log
 *
 */
@RequiredArgsConstructor
public class ApiAccessLogFrameworkServiceImpl implements ApiAccessLogFrameworkService {

    private final ApiAccessLogApi apiAccessLogApi;

    @Override
    @Async
    public void createApiAccessLog(ApiAccessLog apiAccessLog) {
        ApiAccessLogCreateReqDTO reqDTO = BeanUtil.copyProperties(apiAccessLog, ApiAccessLogCreateReqDTO.class);
        apiAccessLogApi.createApiAccessLog(reqDTO);
    }

}
