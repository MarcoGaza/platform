package cn.econets.blossom.module.infrastructure.api.logger.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * API Access log
 *
 */
@Data
public class ApiAccessLogCreateReqDTO {

    /**
     * Link tracking number
     */
    private String traceId;
    /**
     * User ID
     */
    private Long userId;
    /**
     * User Type
     */
    private Integer userType;
    /**
     * Application name
     */
    @NotNull(message = "Application name cannot be empty")
    private String applicationName;

    /**
     * Request method name
     */
    @NotNull(message = "http The request method cannot be empty")
    private String requestMethod;
    /**
     * Access address
     */
    @NotNull(message = "The access address cannot be empty")
    private String requestUrl;
    /**
     * Request parameters
     */
    @NotNull(message = "Request parameters cannot be empty")
    private String requestParams;
    /**
     * User IP
     */
    @NotNull(message = "ip Cannot be empty")
    private String userIp;
    /**
     * Browser UA
     */
    @NotNull(message = "User-Agent Cannot be empty")
    private String userAgent;

    /**
     * Start request time
     */
    @NotNull(message = "The start request time cannot be empty")
    private LocalDateTime beginTime;
    /**
     * End request time
     */
    @NotNull(message = "The end request time cannot be empty")
    private LocalDateTime endTime;
    /**
     * Execution duration，Unit：Milliseconds
     */
    @NotNull(message = "Execution duration cannot be empty")
    private Integer duration;
    /**
     * Result code
     */
    @NotNull(message = "Error code cannot be empty")
    private Integer resultCode;
    /**
     * Result prompt
     */
    private String resultMsg;

}
