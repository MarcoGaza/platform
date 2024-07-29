package cn.econets.blossom.module.bpm.service.oa;


import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.bpm.controller.admin.oa.vo.BpmOALeaveCreateReqVO;
import cn.econets.blossom.module.bpm.controller.admin.oa.vo.BpmOALeavePageReqVO;
import cn.econets.blossom.module.bpm.dal.dataobject.oa.BpmOALeaveDO;

import javax.validation.Valid;

/**
 * Leave Application Service Interface
 *
 *
 *
 */
public interface BpmOALeaveService {

    /**
     * Create a leave application
     *
     * @param userId User Number
     * @param createReqVO Create information
     * @return Number
     */
    Long createLeave(Long userId, @Valid BpmOALeaveCreateReqVO createReqVO);

    /**
     * Update the status of leave application
     *
     * @param id Number
     * @param result Results
     */
    void updateLeaveResult(Long id, Integer result);

    /**
     * Get leave application
     *
     * @param id Number
     * @return Leave Application
     */
    BpmOALeaveDO getLeave(Long id);

    /**
     * Get the leave application page
     *
     * @param userId User Number
     * @param pageReqVO Paged query
     * @return Leave Application Page
     */
    PageResult<BpmOALeaveDO> getLeavePage(Long userId, BpmOALeavePageReqVO pageReqVO);

}
