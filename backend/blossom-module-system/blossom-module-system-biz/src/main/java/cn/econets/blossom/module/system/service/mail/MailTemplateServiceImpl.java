package cn.econets.blossom.module.system.service.mail;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.controller.admin.mail.vo.template.MailTemplatePageReqVO;
import cn.econets.blossom.module.system.controller.admin.mail.vo.template.MailTemplateSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.mail.MailTemplateDO;
import cn.econets.blossom.module.system.dal.mysql.mail.MailTemplateMapper;
import cn.econets.blossom.module.system.dal.redis.RedisKeyConstants;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.MAIL_TEMPLATE_CODE_EXISTS;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.MAIL_TEMPLATE_NOT_EXISTS;

/**
 * Mailbox Template Service Implementation class
 *
 */
@Service
@Validated
@Slf4j
public class MailTemplateServiceImpl implements MailTemplateService {

    /**
     * Regular expression，Match {} Variables in
     */
    private static final Pattern PATTERN_PARAMS = Pattern.compile("\\{(.*?)}");

    @Resource
    private MailTemplateMapper mailTemplateMapper;

    @Override
    public Long createMailTemplate(MailTemplateSaveReqVO createReqVO) {
        // Verification code Is it unique?
        validateCodeUnique(null, createReqVO.getCode());

        // Insert
        MailTemplateDO template = BeanUtils.toBean(createReqVO, MailTemplateDO.class)
                .setParams(parseTemplateContentParams(createReqVO.getContent()));
        mailTemplateMapper.insert(template);
        return template.getId();
    }

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.MAIL_TEMPLATE,
            allEntries = true) // allEntries Clear all caches，Because it may be modified code Field，Difficult to clean
    public void updateMailTemplate(@Valid MailTemplateSaveReqVO updateReqVO) {
        // Check if it exists
        validateMailTemplateExists(updateReqVO.getId());
        // Verification code Is it unique?
        validateCodeUnique(updateReqVO.getId(),updateReqVO.getCode());

        // Update
        MailTemplateDO updateObj = BeanUtils.toBean(updateReqVO, MailTemplateDO.class)
                .setParams(parseTemplateContentParams(updateReqVO.getContent()));
        mailTemplateMapper.updateById(updateObj);
    }

    @VisibleForTesting
    void validateCodeUnique(Long id, String code) {
        MailTemplateDO template = mailTemplateMapper.selectByCode(code);
        if (template == null) {
            return;
        }
        // Exists template Recorded case
        if (id == null // When adding，Description repeated
                || ObjUtil.notEqual(id, template.getId())) { // Updating，If id Inconsistent，Description repeated
            throw exception(MAIL_TEMPLATE_CODE_EXISTS);
        }
    }

    @Override
    @CacheEvict(cacheNames = RedisKeyConstants.MAIL_TEMPLATE,
            allEntries = true) // allEntries Clear all caches，Because id Not a direct cache code，Difficult to clean
    public void deleteMailTemplate(Long id) {
        // Check if it exists
        validateMailTemplateExists(id);

        // Delete
        mailTemplateMapper.deleteById(id);
    }

    private void validateMailTemplateExists(Long id) {
        if (mailTemplateMapper.selectById(id) == null) {
            throw exception(MAIL_TEMPLATE_NOT_EXISTS);
        }
    }

    @Override
    public MailTemplateDO getMailTemplate(Long id) {return mailTemplateMapper.selectById(id);}

    @Override
    @Cacheable(value = RedisKeyConstants.MAIL_TEMPLATE, key = "#code", unless = "#result == null")
    public MailTemplateDO getMailTemplateByCodeFromCache(String code) {
        return mailTemplateMapper.selectByCode(code);
    }

    @Override
    public PageResult<MailTemplateDO> getMailTemplatePage(MailTemplatePageReqVO pageReqVO) {
        return mailTemplateMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MailTemplateDO> getMailTemplateList() {return mailTemplateMapper.selectList();}

    @Override
    public String formatMailTemplateContent(String content, Map<String, Object> params) {
        return StrUtil.format(content, params);
    }

    @VisibleForTesting
    public List<String> parseTemplateContentParams(String content) {
        return ReUtil.findAllGroup1(PATTERN_PARAMS, content);
    }

    @Override
    public long getMailTemplateCountByAccountId(Long accountId) {
        return mailTemplateMapper.selectCountByAccountId(accountId);
    }

}
