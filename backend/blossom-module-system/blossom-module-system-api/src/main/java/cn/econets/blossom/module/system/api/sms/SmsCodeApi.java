package cn.econets.blossom.module.system.api.sms;

import cn.econets.blossom.framework.common.exception.ServiceException;
import cn.econets.blossom.module.system.api.sms.dto.code.SmsCodeSendReqDTO;
import cn.econets.blossom.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import cn.econets.blossom.module.system.api.sms.dto.code.SmsCodeValidateReqDTO;

import javax.validation.Valid;

/**
 * SMS verification code API Interface
 *
 */
public interface SmsCodeApi {

    /**
     * Create SMS verification code，And send
     *
     * @param reqDTO Send request
     */
    void sendSmsCode(@Valid SmsCodeSendReqDTO reqDTO);

    /**
     * Verify SMS verification code，and use it
     * If correct，Mark the verification code as used
     * If error，Throw {@link ServiceException} Abnormal
     *
     * @param reqDTO Use request
     */
    void useSmsCode(@Valid SmsCodeUseReqDTO reqDTO);

    /**
     * Check if the verification code is valid
     *
     * @param reqDTO Verification request
     */
    void validateSmsCode(@Valid SmsCodeValidateReqDTO reqDTO);

}
