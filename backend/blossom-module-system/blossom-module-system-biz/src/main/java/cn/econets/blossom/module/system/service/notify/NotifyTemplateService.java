package cn.econets.blossom.module.system.service.notify;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.notify.vo.template.NotifyTemplatePageReqVO;
import cn.econets.blossom.module.system.controller.admin.notify.vo.template.NotifyTemplateSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.notify.NotifyTemplateDO;

import javax.validation.Valid;
import java.util.Map;

/**
 * Internal message template Service Interface
 *
 */
public interface NotifyTemplateService {

    /**
     * Create a template for internal messages
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createNotifyTemplate(@Valid NotifyTemplateSaveReqVO createReqVO);

    /**
     * Update the internal message template
     *
     * @param updateReqVO Update information
     */
    void updateNotifyTemplate(@Valid NotifyTemplateSaveReqVO updateReqVO);

    /**
     * Delete the internal message template
     *
     * @param id Number
     */
    void deleteNotifyTemplate(Long id);

    /**
     * Get the internal message template
     *
     * @param id Number
     * @return Internal message template
     */
    NotifyTemplateDO getNotifyTemplate(Long id);

    /**
     * Get the internal message template，From cache
     *
     * @param code Template encoding
     * @return Internal message template
     */
    NotifyTemplateDO getNotifyTemplateByCodeFromCache(String code);

    /**
     * Get the template paging for in-site messages
     *
     * @param pageReqVO Paged query
     * @return Pagination of internal message templates
     */
    PageResult<NotifyTemplateDO> getNotifyTemplatePage(NotifyTemplatePageReqVO pageReqVO);

    /**
     * Format the content of the internal message
     *
     * @param content The content of the internal message template
     * @param params Parameters of internal message content
     * @return Formatted content
     */
    String formatNotifyTemplateContent(String content, Map<String, Object> params);

}
