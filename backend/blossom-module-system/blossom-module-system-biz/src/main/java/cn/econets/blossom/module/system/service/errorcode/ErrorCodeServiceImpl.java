package cn.econets.blossom.module.system.service.errorcode;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.collection.CollectionUtils;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.api.errorcode.dto.ErrorCodeAutoGenerateReqDTO;
import cn.econets.blossom.module.system.api.errorcode.dto.ErrorCodeRespDTO;
import cn.econets.blossom.module.system.controller.admin.errorcode.vo.ErrorCodePageReqVO;
import cn.econets.blossom.module.system.controller.admin.errorcode.vo.ErrorCodeSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.errorcode.ErrorCodeDO;
import cn.econets.blossom.module.system.dal.mysql.errorcode.ErrorCodeMapper;
import cn.econets.blossom.module.system.enums.ErrorCodeConstants;
import cn.econets.blossom.module.system.enums.errorcode.ErrorCodeTypeEnum;
import cn.hutool.core.collection.CollUtil;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;


/**
 * Error code Service Implementation class
 *
 */
@Service
@Validated
@Slf4j
public class ErrorCodeServiceImpl implements ErrorCodeService {

    @Resource
    private ErrorCodeMapper errorCodeMapper;

    @Override
    public Long createErrorCode(ErrorCodeSaveReqVO createReqVO) {
        // Verification code Repeat
        validateCodeDuplicate(createReqVO.getCode(), null);

        // Insert
        ErrorCodeDO errorCode = BeanUtils.toBean(createReqVO, ErrorCodeDO.class)
                .setType(ErrorCodeTypeEnum.MANUAL_OPERATION.getType());
        errorCodeMapper.insert(errorCode);
        // Return
        return errorCode.getId();
    }

    @Override
    public void updateErrorCode(ErrorCodeSaveReqVO updateReqVO) {
        // Check existence
        validateErrorCodeExists(updateReqVO.getId());
        // Verification code Repeat
        validateCodeDuplicate(updateReqVO.getCode(), updateReqVO.getId());

        // Update
        ErrorCodeDO updateObj = BeanUtils.toBean(updateReqVO, ErrorCodeDO.class)
                .setType(ErrorCodeTypeEnum.MANUAL_OPERATION.getType());
        errorCodeMapper.updateById(updateObj);
    }

    @Override
    public void deleteErrorCode(Long id) {
        // Check existence
        validateErrorCodeExists(id);
        // Delete
        errorCodeMapper.deleteById(id);
    }

    /**
     * Check whether the only field of the error code is repeated
     *
     * Is there an error code with the same encoding?
     *
     * @param code Error code
     * @param id Error code number
     */
    @VisibleForTesting
    public void validateCodeDuplicate(Integer code, Long id) {
        ErrorCodeDO errorCodeDO = errorCodeMapper.selectByCode(code);
        if (errorCodeDO == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Error code
        if (id == null) {
            throw exception(ErrorCodeConstants.ERROR_CODE_DUPLICATE);
        }
        if (!errorCodeDO.getId().equals(id)) {
            throw exception(ErrorCodeConstants.ERROR_CODE_DUPLICATE);
        }
    }

    @VisibleForTesting
    void validateErrorCodeExists(Long id) {
        if (errorCodeMapper.selectById(id) == null) {
            throw exception(ErrorCodeConstants.ERROR_CODE_NOT_EXISTS);
        }
    }

    @Override
    public ErrorCodeDO getErrorCode(Long id) {
        return errorCodeMapper.selectById(id);
    }

    @Override
    public PageResult<ErrorCodeDO> getErrorCodePage(ErrorCodePageReqVO pageReqVO) {
        return errorCodeMapper.selectPage(pageReqVO);
    }

    @Override
    @Transactional
    public void autoGenerateErrorCodes(List<ErrorCodeAutoGenerateReqDTO> autoGenerateDTOs) {
        if (CollUtil.isEmpty(autoGenerateDTOs)) {
            return;
        }
        // Get error code
        List<ErrorCodeDO> errorCodeDOs = errorCodeMapper.selectListByCodes(
                CollectionUtils.convertSet(autoGenerateDTOs, ErrorCodeAutoGenerateReqDTO::getCode));
        Map<Integer, ErrorCodeDO> errorCodeDOMap = CollectionUtils.convertMap(errorCodeDOs, ErrorCodeDO::getCode);

        // Traverse autoGenerateBOs Array，Insert or update one by one。Considering that the amount of each time is not large，No more batch processing
        autoGenerateDTOs.forEach(autoGenerateDTO -> {
            ErrorCodeDO errorCode = errorCodeDOMap.get(autoGenerateDTO.getCode());
            // Does not exist，Then add new ones
            if (errorCode == null) {
                errorCode = BeanUtils.toBean(autoGenerateDTO, ErrorCodeDO.class)
                        .setType(ErrorCodeTypeEnum.AUTO_GENERATION.getType());
                errorCodeMapper.insert(errorCode);
                return;
            }
            // Exists，Then update。There are three prerequisites for updating：
            // Conditions 1. Only update the automatically generated error code，That is Type for ErrorCodeTypeEnum.AUTO_GENERATION
            if (!ErrorCodeTypeEnum.AUTO_GENERATION.getType().equals(errorCode.getType())) {
                return;
            }
            // Conditions 2. Grouping applicationName Must match，Avoid conflicting error codes
            if (!autoGenerateDTO.getApplicationName().equals(errorCode.getApplicationName())) {
                log.error("[autoGenerateErrorCodes][Automatically create({}/{}) Error code failed，Already exists in the database({}/{})]",
                        autoGenerateDTO.getCode(), autoGenerateDTO.getApplicationName(),
                        errorCode.getCode(), errorCode.getApplicationName());
                return;
            }
            // Conditions 3. Error messages differ
            if (autoGenerateDTO.getMessage().equals(errorCode.getMessage())) {
                return;
            }
            // Final match，Update
            errorCodeMapper.updateById(new ErrorCodeDO().setId(errorCode.getId()).setMessage(autoGenerateDTO.getMessage()));
        });
    }

    @Override
    public List<ErrorCodeRespDTO> getErrorCodeList(String applicationName, LocalDateTime minUpdateTime) {
        List<ErrorCodeDO> list = errorCodeMapper.selectListByApplicationNameAndUpdateTimeGt(
                applicationName, minUpdateTime);
        return BeanUtils.toBean(list, ErrorCodeRespDTO.class);
    }

}

