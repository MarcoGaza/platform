package cn.econets.blossom.module.bpm.service.oa;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.bpm.api.task.BpmProcessInstanceApi;
import cn.econets.blossom.module.bpm.api.task.dto.BpmProcessInstanceCreateReqDTO;
import cn.econets.blossom.module.bpm.controller.admin.oa.vo.BpmOALeaveCreateReqVO;
import cn.econets.blossom.module.bpm.controller.admin.oa.vo.BpmOALeavePageReqVO;
import cn.econets.blossom.module.bpm.convert.oa.BpmOALeaveConvert;
import cn.econets.blossom.module.bpm.dal.dataobject.oa.BpmOALeaveDO;
import cn.econets.blossom.module.bpm.dal.mysql.oa.BpmOALeaveMapper;
import cn.econets.blossom.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.bpm.enums.ErrorCodeConstants.OA_LEAVE_NOT_EXISTS;

/**
 * OA Leave Application Service Implementation class
 *
 *
 *
 */
@Service
@Validated
public class BpmOALeaveServiceImpl implements BpmOALeaveService {

    /**
     * OA Process definition for leave KEY
     */
    public static final String PROCESS_KEY = "oa_leave";

    @Resource
    private BpmOALeaveMapper leaveMapper;

    @Resource
    private BpmProcessInstanceApi processInstanceApi;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createLeave(Long userId, BpmOALeaveCreateReqVO createReqVO) {
        // Insert OA Leave request form
        long day = LocalDateTimeUtil.between(createReqVO.getStartTime(), createReqVO.getEndTime()).toDays();
        BpmOALeaveDO leave = BpmOALeaveConvert.INSTANCE.convert(createReqVO).setUserId(userId).setDay(day)
                .setResult(BpmProcessInstanceResultEnum.PROCESS.getResult());
        leaveMapper.insert(leave);

        // Initiate BPM Process
        Map<String, Object> processInstanceVariables = new HashMap<>();
        processInstanceVariables.put("day", day);
        String processInstanceId = processInstanceApi.createProcessInstance(userId,
                new BpmProcessInstanceCreateReqDTO().setProcessDefinitionKey(PROCESS_KEY)
                        .setVariables(processInstanceVariables).setBusinessKey(String.valueOf(leave.getId())));

        // Number the workflow，Update to OA Leave application in progress
        leaveMapper.updateById(new BpmOALeaveDO().setId(leave.getId()).setProcessInstanceId(processInstanceId));
        return leave.getId();
    }

    @Override
    public void updateLeaveResult(Long id, Integer result) {
        validateLeaveExists(id);
        leaveMapper.updateById(new BpmOALeaveDO().setId(id).setResult(result));
    }

    private void validateLeaveExists(Long id) {
        if (leaveMapper.selectById(id) == null) {
            throw exception(OA_LEAVE_NOT_EXISTS);
        }
    }

    @Override
    public BpmOALeaveDO getLeave(Long id) {
        return leaveMapper.selectById(id);
    }

    @Override
    public PageResult<BpmOALeaveDO> getLeavePage(Long userId, BpmOALeavePageReqVO pageReqVO) {
        return leaveMapper.selectPage(userId, pageReqVO);
    }

}
