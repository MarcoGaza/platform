package cn.econets.blossom.framework.operatelog.core.service;

import cn.econets.blossom.module.system.api.logger.OperateLogApi;
import cn.econets.blossom.module.system.api.logger.dto.OperateLogCreateReqDTO;
import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;

/**
 * Operation log Framework Service Implementation class
 *
 * Based on {@link OperateLogApi} Realization，Record operation log
 *
 */
@RequiredArgsConstructor
public class OperateLogFrameworkServiceImpl implements OperateLogFrameworkService {

    private final OperateLogApi operateLogApi;

    @Override
    @Async
    public void createOperateLog(OperateLog operateLog) {
        OperateLogCreateReqDTO reqDTO = BeanUtil.toBean(operateLog, OperateLogCreateReqDTO.class);
        operateLogApi.createOperateLog(reqDTO);
    }

}
