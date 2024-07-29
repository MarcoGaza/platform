package cn.econets.blossom.module.pay.service.notify;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.pay.controller.admin.notify.vo.PayNotifyTaskPageReqVO;
import cn.econets.blossom.module.pay.dal.dataobject.notify.PayNotifyLogDO;
import cn.econets.blossom.module.pay.dal.dataobject.notify.PayNotifyTaskDO;

import java.util.List;

/**
 * Callback notification Service Interface
 *
 *
 */
public interface PayNotifyService {

    /**
     * Create callback notification task
     *
     * @param type Type
     * @param dataId Data number
     */
    void createPayNotifyTask(Integer type, Long dataId);

    /**
     * Execute callback notification
     *
     * Attention，This method is provided for scheduled task calls。Currently blossom-server Make a call
     * @return Notification quantity
     */
    int executeNotify() throws InterruptedException;

    /**
     * Get callback notification
     *
     * @param id Number
     * @return Callback notification
     */
    PayNotifyTaskDO getNotifyTask(Long id);

    /**
     * Get callback notification paging
     *
     * @param pageReqVO Paged query
     * @return Callback notification paging
     */
    PageResult<PayNotifyTaskDO> getNotifyTaskPage(PayNotifyTaskPageReqVO pageReqVO);

    /**
     * Get callback log list
     *
     * @param taskId Task number
     * @return Log list
     */
    List<PayNotifyLogDO> getNotifyLogList(Long taskId);

}
