package cn.econets.blossom.module.system.api.notify.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Send internal message to Admin Or Member User
 *
 */
@Data
public class NotifySendSingleToUserReqDTO {

    /**
     * User Number
     */
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    /**
     * Internal letter template number
     */
    @NotEmpty(message = "The internal message template number cannot be empty")
    private String templateCode;

    /**
     * Internal message template parameters
     */
    private Map<String, Object> templateParams;
}
