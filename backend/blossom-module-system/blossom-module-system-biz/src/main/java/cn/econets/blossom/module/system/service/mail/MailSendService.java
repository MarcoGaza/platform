package cn.econets.blossom.module.system.service.mail;

import cn.econets.blossom.module.system.mq.message.mail.MailSendMessage;

import java.util.Map;

/**
 * Email sent Service Interface
 *
 */
public interface MailSendService {

    /**
     * Send a single email to the user in the admin background
     *
     * @param mail Mailbox
     * @param userId User code
     * @param templateCode Mail template encoding
     * @param templateParams Email template parameters
     * @return Send log number
     */
    Long sendSingleMailToAdmin(String mail, Long userId,
                               String templateCode, Map<String, Object> templateParams);

    /**
     * Send a single email to a user APP Users
     *
     * @param mail Mailbox
     * @param userId User Code
     * @param templateCode Mail template encoding
     * @param templateParams Email template parameters
     * @return Send log number
     */
    Long sendSingleMailToMember(String mail, Long userId,
                                String templateCode, Map<String, Object> templateParams);

    /**
     * Send a single email to a user
     *
     * @param mail Mailbox
     * @param userId User Code
     * @param userType User Type
     * @param templateCode Mail template encoding
     * @param templateParams Email template parameters
     * @return Send log number
     */
    Long sendSingleMail(String mail, Long userId, Integer userType,
                        String templateCode, Map<String, Object> templateParams);

    /**
     * Perform the actual mail sending
     * Attention，This method is only available to MQ Consumer Use
     *
     * @param message Mail
     */
    void doSendMail(MailSendMessage message);

}
