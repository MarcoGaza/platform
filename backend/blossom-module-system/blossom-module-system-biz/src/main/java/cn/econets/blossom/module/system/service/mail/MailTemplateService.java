package cn.econets.blossom.module.system.service.mail;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.mail.vo.template.MailTemplatePageReqVO;
import cn.econets.blossom.module.system.controller.admin.mail.vo.template.MailTemplateSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.mail.MailTemplateDO;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Mail Template Service Interface
 *
 */
public interface MailTemplateService {

    /**
     * Email template creation
     *
     * @param createReqVO Mail information
     * @return Number
     */
    Long createMailTemplate(@Valid MailTemplateSaveReqVO createReqVO);

    /**
     * Mail template modification
     *
     * @param updateReqVO Mail information
     */
    void updateMailTemplate(@Valid MailTemplateSaveReqVO updateReqVO);

    /**
     * Mail template deletion
     *
     * @param id Number
     */
    void deleteMailTemplate(Long id);

    /**
     * Get email template
     *
     * @param id Number
     * @return Mail Template
     */
    MailTemplateDO getMailTemplate(Long id);

    /**
     * Get email template paging
     *
     * @param pageReqVO Template information
     * @return Mail template paging information
     */
    PageResult<MailTemplateDO> getMailTemplatePage(MailTemplatePageReqVO pageReqVO);

    /**
     * Get email template array
     *
     * @return Template array
     */
    List<MailTemplateDO> getMailTemplateList();

    /**
     * Get email template from cache
     *
     * @param code Template encoding
     * @return Mail template
     */
    MailTemplateDO getMailTemplateByCodeFromCache(String code);

    /**
     * Mail template content synthesis
     *
     * @param content Mail Template
     * @param params Synthesis parameters
     * @return Formatted content
     */
    String formatMailTemplateContent(String content, Map<String, Object> params);

    /**
     * Get the number of email templates under the specified email account
     *
     * @param accountId Account Number
     * @return Quantity
     */
    long getMailTemplateCountByAccountId(Long accountId);

}
