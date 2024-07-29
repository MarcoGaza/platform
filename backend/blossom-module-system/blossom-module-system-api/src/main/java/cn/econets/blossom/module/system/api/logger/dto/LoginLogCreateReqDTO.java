package cn.econets.blossom.module.system.api.logger.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Login log creation Request DTO
 *
 */
@Data
public class LoginLogCreateReqDTO {

    /**
     * Log Type
     */
    @NotNull(message = "Log type cannot be empty")
    private Integer logType;
    /**
     * Link tracking number
     */
    private String traceId;

    /**
     * User Number
     */
    private Long userId;
    /**
     * User Type
     */
    @NotNull(message = "User type cannot be empty")
    private Integer userType;
    /**
     * User Account
     */
    @NotBlank(message = "User account cannot be empty")
    @Size(max = 30, message = "The length of the user account cannot exceed30Characters")
    private String username;

    /**
     * Login result
     */
    @NotNull(message = "Login result cannot be empty")
    private Integer result;

    /**
     * User IP
     */
    @NotEmpty(message = "User IP Cannot be empty")
    private String userIp;
    /**
     * Browser UserAgent
     *
     * Allow empty space，Reason：Job When logging out of the account expires，Cannot be delivered UserAgent of
     */
    private String userAgent;

}
