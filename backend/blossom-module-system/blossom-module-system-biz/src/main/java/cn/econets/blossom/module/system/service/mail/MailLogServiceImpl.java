package cn.econets.blossom.module.system.service.mail;

import cn.econets.blossom.framework.common.pojo.PageResult;
import cn.econets.blossom.module.system.controller.admin.mail.vo.log.MailLogPageReqVO;
import cn.econets.blossom.module.system.dal.dataobject.mail.MailAccountDO;
import cn.econets.blossom.module.system.dal.dataobject.mail.MailLogDO;
import cn.econets.blossom.module.system.dal.dataobject.mail.MailTemplateDO;
import cn.econets.blossom.module.system.dal.mysql.mail.MailLogMapper;
import cn.econets.blossom.module.system.enums.mail.MailSendStatusEnum;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import static cn.hutool.core.exceptions.ExceptionUtil.getRootCauseMessage;

/**
 * Mail log Service Implementation class
 *
 */
@Service
@Validated
public class MailLogServiceImpl implements MailLogService {

    @Resource
    private MailLogMapper mailLogMapper;

    @Override
    public PageResult<MailLogDO> getMailLogPage(MailLogPageReqVO pageVO) {
        return mailLogMapper.selectPage(pageVO);
    }

    @Override
    public MailLogDO getMailLog(Long id) {
        return mailLogMapper.selectById(id);
    }

    @Override
    public Long createMailLog(Long userId, Integer userType, String toMail,
                              MailAccountDO account, MailTemplateDO template,
                              String templateContent, Map<String, Object> templateParams, Boolean isSend) {
        MailLogDO.MailLogDOBuilder logDOBuilder = MailLogDO.builder();
        // Depending on whether to send，Set status
        logDOBuilder.sendStatus(Objects.equals(isSend, true) ? MailSendStatusEnum.INIT.getStatus()
                : MailSendStatusEnum.IGNORE.getStatus())
                // User Information
                .userId(userId).userType(userType).toMail(toMail)
                .accountId(account.getId()).fromMail(account.getMail())
                // Template related fields
                .templateId(template.getId()).templateCode(template.getCode()).templateNickname(template.getNickname())
                .templateTitle(template.getTitle()).templateContent(templateContent).templateParams(templateParams);

        // Insert into database
        MailLogDO logDO = logDOBuilder.build();
        mailLogMapper.insert(logDO);
        return logDO.getId();
    }

    @Override
    public void updateMailSendResult(Long logId, String messageId, Exception exception) {
        // 1. Success
        if (exception == null) {
            mailLogMapper.updateById(new MailLogDO().setId(logId).setSendTime(LocalDateTime.now())
                    .setSendStatus(MailSendStatusEnum.SUCCESS.getStatus()).setSendMessageId(messageId));
            return;
        }
        // 2. Failed
        mailLogMapper.updateById(new MailLogDO().setId(logId).setSendTime(LocalDateTime.now())
                .setSendStatus(MailSendStatusEnum.FAILURE.getStatus()).setSendException(getRootCauseMessage(exception)));

    }

}
