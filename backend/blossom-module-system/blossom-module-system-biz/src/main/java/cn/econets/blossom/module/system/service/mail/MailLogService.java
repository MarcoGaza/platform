package cn.econets.blossom.module.system.service.mail;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.mail.vo.log.MailLogPageReqVO;
import cn.econets.blossom.module.system.dal.dataobject.mail.MailAccountDO;
import cn.econets.blossom.module.system.dal.dataobject.mail.MailLogDO;
import cn.econets.blossom.module.system.dal.dataobject.mail.MailTemplateDO;

import java.util.Map;

/**
 * Mail log Service Interface
 *
 */
public interface MailLogService {

    /**
     * Mail log paging
     *
     * @param pageVO Paging parameters
     * @return Paged results
     */
    PageResult<MailLogDO> getMailLogPage(MailLogPageReqVO pageVO);

    /**
     * Get the email log with the specified number
     *
     * @param id Log number
     * @return Mail log
     */
    MailLogDO getMailLog(Long id);

    /**
     * Create mail log
     *
     * @param userId User Code
     * @param userType User Type
     * @param toMail Recipient Email
     * @param account Mail account information
     * @param template      Template information
     * @param templateContent Template content
     * @param templateParams Template parameters
     * @param isSend        Whether the sending was successful
     * @return Log number
     */
    Long createMailLog(Long userId, Integer userType, String toMail,
                       MailAccountDO account, MailTemplateDO template ,
                       String templateContent, Map<String, Object> templateParams, Boolean isSend);

    /**
     * Update email sending results
     *
     * @param logId  Log number
     * @param messageId The message number after sending
     * @param exception Sending exception
     */
    void updateMailSendResult(Long logId, String messageId, Exception exception);

}
