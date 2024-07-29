package cn.econets.blossom.module.infrastructure.service.logger;

import cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.infrastructure.api.logger.dto.ApiErrorLogCreateReqDTO;
import cn.econets.blossom.module.infrastructure.controller.admin.logger.vo.apierrorlog.ApiErrorLogPageReqVO;
import cn.econets.blossom.module.infrastructure.dal.mysql.logger.ApiErrorLogMapper;
import cn.econets.blossom.module.infrastructure.dal.dataobject.logger.ApiErrorLogDO;
import cn.econets.blossom.module.infrastructure.enums.ErrorCodeConstants;
import cn.econets.blossom.module.infrastructure.enums.logger.ApiErrorLogProcessStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;


/**
 * API Error log Service Implementation class
 *
 */
@Slf4j
@Service
@Validated
public class ApiErrorLogServiceImpl implements ApiErrorLogService {

    @Resource
    private ApiErrorLogMapper apiErrorLogMapper;

    @Override
    public void createApiErrorLog(ApiErrorLogCreateReqDTO createDTO) {
        ApiErrorLogDO apiErrorLog = BeanUtils.toBean(createDTO, ApiErrorLogDO.class);
        apiErrorLog.setProcessStatus(ApiErrorLogProcessStatusEnum.INIT.getStatus());
        apiErrorLogMapper.insert(apiErrorLog);
    }

    @Override
    public PageResult<ApiErrorLogDO> getApiErrorLogPage(ApiErrorLogPageReqVO pageReqVO) {
        return apiErrorLogMapper.selectPage(pageReqVO);
    }

    @Override
    public void updateApiErrorLogProcess(Long id, Integer processStatus, Long processUserId) {
        ApiErrorLogDO errorLog = apiErrorLogMapper.selectById(id);
        if (errorLog == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.API_ERROR_LOG_NOT_FOUND);
        }
        if (!ApiErrorLogProcessStatusEnum.INIT.getStatus().equals(errorLog.getProcessStatus())) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.API_ERROR_LOG_PROCESSED);
        }
        // Mark processing
        apiErrorLogMapper.updateById(ApiErrorLogDO.builder().id(id).processStatus(processStatus)
                .processUserId(processUserId).processTime(LocalDateTime.now()).build());
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public Integer cleanErrorLog(Integer exceedDay, Integer deleteLimit) {
        int count = 0;
        LocalDateTime expireDate = LocalDateTime.now().minusDays(exceedDay);
        // Loop deletion，Until there is no data that meets the conditions
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            int deleteCount = apiErrorLogMapper.deleteByCreateTimeLt(expireDate, deleteLimit);
            count += deleteCount;
            // The expected number of entries to be deleted has been reached，The explanation is complete
            if (deleteCount < deleteLimit) {
                break;
            }
        }
        return count;
    }

}
