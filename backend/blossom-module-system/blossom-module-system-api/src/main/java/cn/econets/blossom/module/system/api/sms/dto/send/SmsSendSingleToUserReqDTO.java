package cn.econets.blossom.module.system.api.sms.dto.send;

import cn.econets.blossom.framework.common.validation.Mobile;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Map;

/**
 * Send message to Admin Or Member User
 *
 */
@Data
public class SmsSendSingleToUserReqDTO {

    /**
     * User Number
     */
    private Long userId;
    /**
     * Mobile phone number
     */
    @Mobile
    private String mobile;
    /**
     * SMS template number
     */
    @NotEmpty(message = "The SMS template number cannot be empty")
    private String templateCode;
    /**
     * SMS template parameters
     */
    private Map<String, Object> templateParams;

}
