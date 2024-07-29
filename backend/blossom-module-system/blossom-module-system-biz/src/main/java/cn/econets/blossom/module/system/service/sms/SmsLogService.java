package cn.econets.blossom.module.system.service.sms;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.sms.vo.log.SmsLogPageReqVO;
import cn.econets.blossom.module.system.dal.dataobject.sms.SmsLogDO;
import cn.econets.blossom.module.system.dal.dataobject.sms.SmsTemplateDO;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * SMS log Service Interface
 *
 */
public interface SmsLogService {

    /**
     * Create SMS log
     *
     * @param mobile Mobile phone number
     * @param userId User Number
     * @param userType User Type
     * @param isSend Send
     * @param template SMS template
     * @param templateContent SMS content
     * @param templateParams SMS parameters
     * @return Send log number
     */
    Long createSmsLog(String mobile, Long userId, Integer userType, Boolean isSend,
                      SmsTemplateDO template, String templateContent, Map<String, Object> templateParams);

    /**
     * Update log sending results
     *
     * @param id Log number
     * @param success Is the sending successful?
     * @param apiSendCode SMS API The encoding of the sent result
     * @param apiSendMsg SMS API Send failed prompt
     * @param apiRequestId SMS API Send the only request returned ID
     * @param apiSerialNo SMS API Send the returned sequence number
     */
    void updateSmsSendResult(Long id, Boolean success,
                             String apiSendCode, String apiSendMsg,
                             String apiRequestId, String apiSerialNo);

    /**
     * Update log reception results
     *
     * @param id Log number
     * @param success Whether the reception was successful
     * @param receiveTime User receiving time
     * @param apiReceiveCode API The encoding of the received result
     * @param apiReceiveMsg API Description of receiving results
     */
    void updateSmsReceiveResult(Long id, Boolean success,
                                LocalDateTime receiveTime, String apiReceiveCode, String apiReceiveMsg);

    /**
     * Get SMS log paging
     *
     * @param pageReqVO Paged query
     * @return SMS log paging
     */
    PageResult<SmsLogDO> getSmsLogPage(SmsLogPageReqVO pageReqVO);

}
