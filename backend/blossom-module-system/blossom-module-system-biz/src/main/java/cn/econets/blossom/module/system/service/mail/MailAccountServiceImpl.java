package cn.econets.blossom.module.system.service.mail;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.framework.common.util.object.BeanUtils;
import cn.econets.blossom.module.system.controller.admin.mail.vo.account.MailAccountPageReqVO;
import cn.econets.blossom.module.system.controller.admin.mail.vo.account.MailAccountSaveReqVO;
import cn.econets.blossom.module.system.dal.dataobject.mail.MailAccountDO;
import cn.econets.blossom.module.system.dal.mysql.mail.MailAccountMapper;
import cn.econets.blossom.module.system.dal.redis.RedisKeyConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.MAIL_ACCOUNT_NOT_EXISTS;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.MAIL_ACCOUNT_RELATE_TEMPLATE_EXISTS;

/**
 * Email Account Service Implementation class
 *
 */
@Service
@Validated
@Slf4j
public class MailAccountServiceImpl implements MailAccountService {

    @Resource
    private MailAccountMapper mailAccountMapper;

    @Resource
    private MailTemplateService mailTemplateService;

    @Override
    public Long createMailAccount(MailAccountSaveReqVO createReqVO) {
        MailAccountDO account = BeanUtils.toBean(createReqVO, MailAccountDO.class);
        mailAccountMapper.insert(account);
        return account.getId();
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.MAIL_ACCOUNT, key = "#updateReqVO.id")
    public void updateMailAccount(MailAccountSaveReqVO updateReqVO) {
        // Check if it exists
        validateMailAccountExists(updateReqVO.getId());

        // Update
        MailAccountDO updateObj = BeanUtils.toBean(updateReqVO, MailAccountDO.class);
        mailAccountMapper.updateById(updateObj);
    }

    @Override
    @CacheEvict(value = RedisKeyConstants.MAIL_ACCOUNT, key = "#id")
    public void deleteMailAccount(Long id) {
        // Check if the account exists
        validateMailAccountExists(id);
        // Check if there is an associated template
        if (mailTemplateService.getMailTemplateCountByAccountId(id) > 0) {
            throw exception(MAIL_ACCOUNT_RELATE_TEMPLATE_EXISTS);
        }

        // Delete
        mailAccountMapper.deleteById(id);
    }

    private void validateMailAccountExists(Long id) {
        if (mailAccountMapper.selectById(id) == null) {
            throw exception(MAIL_ACCOUNT_NOT_EXISTS);
        }
    }

    @Override
    public MailAccountDO getMailAccount(Long id) {
        return mailAccountMapper.selectById(id);
    }

    @Override
    @Cacheable(value = RedisKeyConstants.MAIL_ACCOUNT, key = "#id", unless = "#result == null")
    public MailAccountDO getMailAccountFromCache(Long id) {
        return getMailAccount(id);
    }

    @Override
    public PageResult<MailAccountDO> getMailAccountPage(MailAccountPageReqVO pageReqVO) {
        return mailAccountMapper.selectPage(pageReqVO);
    }

    @Override
    public List<MailAccountDO> getMailAccountList() {
        return mailAccountMapper.selectList();
    }

}
