package cn.econets.blossom.module.system.api.sms;

import cn.econets.blossom.module.system.api.sms.dto.send.SmsSendSingleToUserReqDTO;

import javax.validation.Valid;

/**
 * Send SMS API Interface
 *
 */
public interface SmsSendApi {

    /**
     * Send a single message to Admin User
     *
     * In mobile When empty，Use userId Load corresponding Admin Mobile phone number
     *
     * @param reqDTO Send request
     * @return Send log number
     */
    Long sendSingleSmsToAdmin(@Valid SmsSendSingleToUserReqDTO reqDTO);

    /**
     * Send a single message to Member User
     *
     * In mobile When empty，Use userId Load corresponding Member Mobile phone number
     *
     * @param reqDTO Send request
     * @return Send log number
     */
    Long sendSingleSmsToMember(@Valid SmsSendSingleToUserReqDTO reqDTO);

}
