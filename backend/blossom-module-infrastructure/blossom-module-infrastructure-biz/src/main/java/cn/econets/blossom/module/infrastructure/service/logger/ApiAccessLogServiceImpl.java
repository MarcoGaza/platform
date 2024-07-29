package cn.econets.blossom.module.infrastructure.service.logger;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.infrastructure.api.logger.dto.ApiAccessLogCreateReqDTO;
import cn.econets.blossom.module.infrastructure.controller.admin.logger.vo.apiaccesslog.ApiAccessLogPageReqVO;
import cn.econets.blossom.module.infrastructure.dal.mysql.logger.ApiAccessLogMapper;
import cn.econets.blossom.module.infrastructure.dal.dataobject.logger.ApiAccessLogDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * API Access log Service Implementation class
 *
 */
@Slf4j
@Service
@Validated
public class ApiAccessLogServiceImpl implements ApiAccessLogService {

    @Resource
    private ApiAccessLogMapper apiAccessLogMapper;

    @Override
    public void createApiAccessLog(ApiAccessLogCreateReqDTO createDTO) {
        ApiAccessLogDO apiAccessLog = BeanUtils.toBean(createDTO, ApiAccessLogDO.class);
        apiAccessLogMapper.insert(apiAccessLog);
    }

    @Override
    public PageResult<ApiAccessLogDO> getApiAccessLogPage(ApiAccessLogPageReqVO pageReqVO) {
        return apiAccessLogMapper.selectPage(pageReqVO);
    }

    @Override
    @SuppressWarnings("DuplicatedCode")
    public Integer cleanAccessLog(Integer exceedDay, Integer deleteLimit) {
        int count = 0;
        LocalDateTime expireDate = LocalDateTime.now().minusDays(exceedDay);
        // Loop deletion，Until there is no data that meets the conditions
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            int deleteCount = apiAccessLogMapper.deleteByCreateTimeLt(expireDate, deleteLimit);
            count += deleteCount;
            // The expected number of entries to be deleted has been reached，This is the explanation
            if (deleteCount < deleteLimit) {
                break;
            }
        }
        return count;
    }

}
