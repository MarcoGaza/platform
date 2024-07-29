package cn.econets.blossom.module.system.service.mail;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.module.system.convert.mail.MailAccountConvert;
import cn.econets.blossom.module.system.dal.dataobject.mail.MailAccountDO;
import cn.econets.blossom.module.system.dal.dataobject.mail.MailTemplateDO;
import cn.econets.blossom.module.system.dal.dataobject.user.AdminUserDO;
import cn.econets.blossom.module.system.mq.message.mail.MailSendMessage;
import cn.econets.blossom.module.system.mq.producer.mail.MailProducer;
import cn.econets.blossom.module.system.service.member.MemberService;
import cn.econets.blossom.module.system.service.user.AdminUserService;
import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.Map;

import static cn.econets.blossom.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.econets.blossom.module.system.enums.ErrorCodeConstants.*;

/**
 * Send by email Service Implementation class
 *
 */
@Service
@Validated
@Slf4j
public class MailSendServiceImpl implements MailSendService {

    @Resource
    private AdminUserService adminUserService;
    @Resource
    private MemberService memberService;

    @Resource
    private MailAccountService mailAccountService;
    @Resource
    private MailTemplateService mailTemplateService;

    @Resource
    private MailLogService mailLogService;
    @Resource
    private MailProducer mailProducer;

    @Override
    public Long sendSingleMailToAdmin(String mail, Long userId,
                                      String templateCode, Map<String, Object> templateParams) {
        // If mail Empty，Then load the mailbox corresponding to the user number
        if (StrUtil.isEmpty(mail)) {
            AdminUserDO user = adminUserService.getUser(userId);
            if (user != null) {
                mail = user.getEmail();
            }
        }
        // Execute send
        return sendSingleMail(mail, userId, UserTypeEnum.ADMIN.getValue(), templateCode, templateParams);
    }

    @Override
    public Long sendSingleMailToMember(String mail, Long userId,
                                       String templateCode, Map<String, Object> templateParams) {
        // If mail Empty，Then load the mailbox corresponding to the user number
        if (StrUtil.isEmpty(mail)) {
            mail = memberService.getMemberUserEmail(userId);
        }
        // Execute send
        return sendSingleMail(mail, userId, UserTypeEnum.MEMBER.getValue(), templateCode, templateParams);
    }

    @Override
    public Long sendSingleMail(String mail, Long userId, Integer userType,
                               String templateCode, Map<String, Object> templateParams) {
        // Check whether the email template is legal
        MailTemplateDO template = validateMailTemplate(templateCode);
        // Check whether the email account is legal
        MailAccountDO account = validateMailAccount(template.getAccountId());

        // Check if the mailbox exists
        mail = validateMail(mail);
        validateTemplateParams(template, templateParams);

        // Create a sending log。If the template is disabled，Do not send SMS，Only log
        Boolean isSend = CommonStatusEnum.ENABLE.getStatus().equals(template.getStatus());
        String title = mailTemplateService.formatMailTemplateContent(template.getTitle(), templateParams);
        String content = mailTemplateService.formatMailTemplateContent(template.getContent(), templateParams);
        Long sendLogId = mailLogService.createMailLog(userId, userType, mail,
                account, template, content, templateParams, isSend);
        // Send MQ Message，Asynchronous execution of SMS sending
        if (isSend) {
            mailProducer.sendMailSendMessage(sendLogId, mail, account.getId(),
                    template.getNickname(), title, content);
        }
        return sendLogId;
    }

    @Override
    public void doSendMail(MailSendMessage message) {
        // 1. Create a sending account
        MailAccountDO account = validateMailAccount(message.getAccountId());
        MailAccount mailAccount  = MailAccountConvert.INSTANCE.convert(account, message.getNickname());
        // 2. Send email
        try {
            String messageId = MailUtil.send(mailAccount, message.getMail(),
                    message.getTitle(), message.getContent(),true);
            // 3. Update results（Success）
            mailLogService.updateMailSendResult(message.getLogId(), messageId, null);
        } catch (Exception e) {
            // 3. Update results（Abnormal）
            mailLogService.updateMailSendResult(message.getLogId(), null, e);
        }
    }

    @VisibleForTesting
    MailTemplateDO validateMailTemplate(String templateCode) {
        // Get email template。Taking efficiency into consideration，Get from cache
        MailTemplateDO template = mailTemplateService.getMailTemplateByCodeFromCache(templateCode);
        // The email template does not exist
        if (template == null) {
            throw exception(MAIL_TEMPLATE_NOT_EXISTS);
        }
        return template;
    }

    @VisibleForTesting
    MailAccountDO validateMailAccount(Long accountId) {
        // Get an email account。Taking efficiency into consideration，Get from cache
        MailAccountDO account = mailAccountService.getMailAccountFromCache(accountId);
        // The email account does not exist
        if (account == null) {
            throw exception(MAIL_ACCOUNT_NOT_EXISTS);
        }
        return account;
    }

    @VisibleForTesting
    String validateMail(String mail) {
        if (StrUtil.isEmpty(mail)) {
            throw exception(MAIL_SEND_MAIL_NOT_EXISTS);
        }
        return mail;
    }

    /**
     * Check whether the email parameters are correct
     *
     * @param template Email Template
     * @param templateParams Parameter list
     */
    @VisibleForTesting
    void validateTemplateParams(MailTemplateDO template, Map<String, Object> templateParams) {
        template.getParams().forEach(key -> {
            Object value = templateParams.get(key);
            if (value == null) {
                throw exception(MAIL_SEND_TEMPLATE_PARAM_MISS, key);
            }
        });
    }

}
