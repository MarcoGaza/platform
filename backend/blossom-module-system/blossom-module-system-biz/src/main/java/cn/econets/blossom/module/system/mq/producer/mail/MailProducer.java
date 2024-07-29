package cn.econets.blossom.module.system.mq.producer.mail;

import cn.econets.blossom.module.system.mq.message.mail.MailSendMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Mail Email related messages Producer
 *
 */
@Slf4j
@Component
public class MailProducer {

    @Resource
    private ApplicationContext applicationContext;

    /**
     * Send {@link MailSendMessage} Message
     *
     * @param sendLogId Send log code
     * @param mail Receiving email address
     * @param accountId Email account number
     * @param nickname Mail sender
     * @param title Email title
     * @param content Email content
     */
    public void sendMailSendMessage(Long sendLogId, String mail, Long accountId,
                                    String nickname, String title, String content) {
        MailSendMessage message = new MailSendMessage();
        message.setLogId(sendLogId);
        message.setMail(mail);
        message.setAccountId(accountId);
        message.setNickname(nickname);
        message.setTitle(title);
        message.setContent(content);
        applicationContext.publishEvent(message);
    }

}
