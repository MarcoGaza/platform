package cn.econets.blossom.module.system.api.mail;

import cn.econets.blossom.module.system.api.mail.dto.MailSendSingleToUserReqDTO;

import javax.validation.Valid;

/**
 * Send by email API Interface
 *
 */
public interface MailSendApi {

    /**
     * Send a single email to Admin User
     *
     * In mail When empty，Use userId Load corresponding Admin Email
     *
     * @param reqDTO Send request
     * @return Send log number
     */
    Long sendSingleMailToAdmin(@Valid MailSendSingleToUserReqDTO reqDTO);

    /**
     * Send a single email to Member User
     *
     * In mail When empty，Use userId Load corresponding Member Email
     *
     * @param reqDTO Send request
     * @return Send log number
     */
    Long sendSingleMailToMember(@Valid MailSendSingleToUserReqDTO reqDTO);

}
