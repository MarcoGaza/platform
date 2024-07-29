package cn.econets.blossom.module.system.api.notify;

import cn.econets.blossom.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;

import javax.validation.Valid;

/**
 * Send internal message API Interface
 *
 */
public interface NotifyMessageSendApi {

    /**
     * Send a single internal message to Admin User
     *
     * @param reqDTO Send request
     * @return Send message ID
     */
    Long sendSingleMessageToAdmin(@Valid NotifySendSingleToUserReqDTO reqDTO);

    /**
     * Send a single internal message to Member User
     *
     * @param reqDTO Send request
     * @return Send message ID
     */
    Long sendSingleMessageToMember(@Valid NotifySendSingleToUserReqDTO reqDTO);

}
