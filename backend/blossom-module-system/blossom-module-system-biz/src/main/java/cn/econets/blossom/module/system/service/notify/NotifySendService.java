package cn.econets.blossom.module.system.service.notify;

import java.util.List;
import java.util.Map;

/**
 * Send internal message Service Interface
 *
 */
public interface NotifySendService {

    /**
     * Send a single internal message to the user in the management backend
     *
     * In mobile When empty，Use userId Load the corresponding administrator's phone number
     *
     * @param userId User Number
     * @param templateCode SMS template number
     * @param templateParams SMS template parameters
     * @return Send log number
     */
    Long sendSingleNotifyToAdmin(Long userId,
                                 String templateCode, Map<String, Object> templateParams);
    /**
     * Send a single internal message to the user APP Users
     *
     * In mobile When empty，Use userId Load the corresponding member's mobile phone number
     *
     * @param userId User Number
     * @param templateCode Internal letter template number
     * @param templateParams Internal message template parameters
     * @return Send log number
     */
    Long sendSingleNotifyToMember(Long userId,
                                  String templateCode, Map<String, Object> templateParams);

    /**
     * Send a single internal message to the user
     *
     * @param userId User Number
     * @param userType User Type
     * @param templateCode Internal letter template number
     * @param templateParams Internal message template parameters
     * @return Send log number
     */
    Long sendSingleNotify( Long userId, Integer userType,
                           String templateCode, Map<String, Object> templateParams);

    default void sendBatchNotify(List<String> mobiles, List<Long> userIds, Integer userType,
                                 String templateCode, Map<String, Object> templateParams) {
        throw new UnsupportedOperationException("This operation is not supported at the moment，If you are interested, you can implement this function！");
    }

}
