package cn.econets.blossom.module.system.service.notify;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.controller.admin.notify.vo.template.NotifyTemplatePageReqVO;
import cn.econets.blossom.module.system.controller.admin.notify.vo.template.NotifyTemplateSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.notify.NotifyTemplateDO;
import cn.econets.blossom.module.system.dal.mysql.notify.NotifyTemplateMapper;
import cn.econets.blossom.module.system.dal.redis.RedisKeyConstants;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.NOTIFY_TEMPLATE_CODE_DUPLICATE;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.NOTIFY_TEMPLATE_NOT_EXISTS;

/**
 * Internal message template Service Implementation class
 *r
 */
@Service
@Validated
@Slf4j
public class NotifyTemplateServiceImpl implements NotifyTemplateService {

    /**
     * Regular expression，Match {} Variables in
     */
    private static final Pattern PATTERN_PARAMS = Pattern.compile("\\{(.*?)}");

    @Resource
    private NotifyTemplateMapper notifyTemplateMapper;

    @Override
    public Long createNotifyTemplate(NotifyTemplateSaveReqVO createReqVO) {
        // Check if the code in the station is repeated
        validateNotifyTemplateCodeDuplicate(null, createReqVO.getCode());

        // Insert
        NotifyTemplateDO notifyTemplate = BeanUtils.toBean(createReqVO, NotifyTemplateDO.class);
        notifyTemplate.setParams(parseTemplateContentParams(notifyTemplate.getContent()));
        notifyTemplateMapper.insert(notifyTemplate);
        return notifyTemplate.getId();
    }

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.NOTIFY_TEMPLATE,
            allEntries = true) // allEntries Clear all caches，Because it may be modified code Field，Difficult to clean
    public void updateNotifyTemplate(NotifyTemplateSaveReqVO updateReqVO) {
        // Check existence
        validateNotifyTemplateExists(updateReqVO.getId());
        // Check if the code in the station is repeated
        validateNotifyTemplateCodeDuplicate(updateReqVO.getId(), updateReqVO.getCode());

        // Update
        NotifyTemplateDO updateObj = BeanUtils.toBean(updateReqVO, NotifyTemplateDO.class);
        updateObj.setParams(parseTemplateContentParams(updateObj.getContent()));
        notifyTemplateMapper.updateById(updateObj);
    }

    @VisibleForTesting
    public List<String> parseTemplateContentParams(String content) {
        return ReUtil.findAllGroup1(PATTERN_PARAMS, content);
    }

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.NOTIFY_TEMPLATE,
            allEntries = true) // allEntries Clear all caches，Because id Not a direct cache code，Difficult to clean
    public void deleteNotifyTemplate(Long id) {
        // Check existence
        validateNotifyTemplateExists(id);
        // Delete
        notifyTemplateMapper.deleteById(id);
    }

    private void validateNotifyTemplateExists(Long id) {
        if (notifyTemplateMapper.selectById(id) == null) {
            throw exception(NOTIFY_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public NotifyTemplateDO getNotifyTemplate(Long id) {
        return notifyTemplateMapper.selectById(id);
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.NOTIFY_TEMPLATE, key = "#code",
            unless = "#result == null")
    public NotifyTemplateDO getNotifyTemplateByCodeFromCache(String code) {
        return notifyTemplateMapper.selectByCode(code);
    }

    @Override
    public PageResult<NotifyTemplateDO> getNotifyTemplatePage(NotifyTemplatePageReqVO pageReqVO) {
        return notifyTemplateMapper.selectPage(pageReqVO);
    }

    @VisibleForTesting
    void validateNotifyTemplateCodeDuplicate(Long id, String code) {
        NotifyTemplateDO template = notifyTemplateMapper.selectByCode(code);
        if (template == null) {
            return;
        }
        // If id Empty，Indicates that there is no need to compare whether they are the same id Dictionary type
        if (id == null) {
            throw exception(NOTIFY_TEMPLATE_CODE_DUPLICATE, code);
        }
        if (!template.getId().equals(id)) {
            throw exception(NOTIFY_TEMPLATE_CODE_DUPLICATE, code);
        }
    }

    /**
     * Format the content of the internal message
     *
     * @param content Contents of the internal message template
     * @param params  Parameters of internal message content
     * @return Formatted content
     */
    @Override
    public String formatNotifyTemplateContent(String content, Map<String, Object> params) {
        return StrUtil.format(content, params);
    }

}
