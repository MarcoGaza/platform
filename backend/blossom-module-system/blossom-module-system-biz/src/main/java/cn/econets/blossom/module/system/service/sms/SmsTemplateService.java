package cn.econets.blossom.module.system.service.sms;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.sms.vo.template.SmsTemplatePageReqVO;
import cn.econets.blossom.module.system.controller.admin.sms.vo.template.SmsTemplateSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.sms.SmsTemplateDO;

import javax.validation.Valid;
import java.util.Map;

/**
 * SMS template Service Interface
 *
 */
public interface SmsTemplateService {

    /**
     * Create SMS template
     *
     * @param createReqVO Create information
     * @return Number
     */
    Long createSmsTemplate(@Valid SmsTemplateSaveReqVO createReqVO);

    /**
     * Update SMS template
     *
     * @param updateReqVO Update information
     */
    void updateSmsTemplate(@Valid SmsTemplateSaveReqVO updateReqVO);

    /**
     * Delete SMS template
     *
     * @param id Number
     */
    void deleteSmsTemplate(Long id);

    /**
     * Get SMS template
     *
     * @param id Number
     * @return SMS template
     */
    SmsTemplateDO getSmsTemplate(Long id);

    /**
     * Get SMS template，From cache
     *
     * @param code Template encoding
     * @return SMS template
     */
    SmsTemplateDO getSmsTemplateByCodeFromCache(String code);

    /**
     * Get SMS template paging
     *
     * @param pageReqVO Paged query
     * @return SMS template paging
     */
    PageResult<SmsTemplateDO> getSmsTemplatePage(SmsTemplatePageReqVO pageReqVO);

    /**
     * Get the number of SMS templates under the specified SMS channel
     *
     * @param channelId SMS channel number
     * @return Quantity
     */
    Long getSmsTemplateCountByChannelId(Long channelId);

    /**
     * Format SMS content
     *
     * @param content Contents of SMS template
     * @param params Content parameters
     * @return Formatted content
     */
    String formatSmsTemplateContent(String content, Map<String, Object> params);

}
