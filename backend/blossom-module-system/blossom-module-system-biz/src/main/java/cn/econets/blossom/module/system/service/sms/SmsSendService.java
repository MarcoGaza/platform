package cn.econets.blossom.module.system.service.sms;

import cn.econets.blossom.module.system.mq.message.sms.SmsSendMessage;

import java.util.List;
import java.util.Map;

/**
 * Send SMS Service Interface
 *
 */
public interface SmsSendService {

    /**
     * Send a single SMS message to the user in the management backend
     *
     * In mobile When empty，Use userId Load the corresponding administrator's phone number
     *
     * @param mobile Mobile phone number
     * @param userId User Number
     * @param templateCode SMS template number
     * @param templateParams SMS template parameters
     * @return Send log number
     */
    Long sendSingleSmsToAdmin(String mobile, Long userId,
                              String templateCode, Map<String, Object> templateParams);

    /**
     * Send a single SMS message to the user APP Users
     *
     * In mobile When empty，Use userId Load the corresponding member's mobile phone number
     *
     * @param mobile Mobile phone number
     * @param userId User Number
     * @param templateCode SMS template number
     * @param templateParams SMS template parameters
     * @return Send log number
     */
    Long sendSingleSmsToMember(String mobile, Long userId,
                               String templateCode, Map<String, Object> templateParams);

    /**
     * Send a single SMS message to the user
     *
     * @param mobile Mobile phone number
     * @param userId User Number
     * @param userType User Type
     * @param templateCode SMS template number
     * @param templateParams SMS template parameters
     * @return Send log number
     */
    Long sendSingleSms(String mobile, Long userId, Integer userType,
                       String templateCode, Map<String, Object> templateParams);

    default void sendBatchSms(List<String> mobiles, List<Long> userIds, Integer userType,
                              String templateCode, Map<String, Object> templateParams) {
        throw new UnsupportedOperationException("This operation is not supported at the moment，If you are interested, you can implement this function！");
    }

    /**
     * Execute the actual SMS sending
     * Attention，This method is only available to MQ Consumer Use
     *
     * @param message SMS
     */
    void doSendSms(SmsSendMessage message);

    /**
     * The result of receiving SMS messages
     *
     * @param channelCode Channel Code
     * @param text Result content
     * @throws Throwable When processing fails，Throws an exception
     */
    void receiveSmsStatus(String channelCode, String text) throws Throwable;

}
