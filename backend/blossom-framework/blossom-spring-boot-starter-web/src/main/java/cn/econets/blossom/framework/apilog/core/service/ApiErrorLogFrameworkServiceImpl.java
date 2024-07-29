package cn.econets.blossom.framework.apilog.core.service;

import cn.econets.blossom.module.infrastructure.api.logger.ApiErrorLogApi;
import cn.econets.blossom.module.infrastructure.api.logger.dto.ApiErrorLogCreateReqDTO;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * API Error log Framework Service Implementation class
 *
 * Based on {@link ApiErrorLogApi} Service，Record error log
 *
 */
@RequiredArgsConstructor
public class ApiErrorLogFrameworkServiceImpl implements ApiErrorLogFrameworkService {

    private final ApiErrorLogApi apiErrorLogApi;

    @Override
    @Async
    public void createApiErrorLog(ApiErrorLog apiErrorLog) {
        ApiErrorLogCreateReqDTO reqDTO = BeanUtil.copyProperties(apiErrorLog, ApiErrorLogCreateReqDTO.class);
        apiErrorLogApi.createApiErrorLog(reqDTO);
    }

}
