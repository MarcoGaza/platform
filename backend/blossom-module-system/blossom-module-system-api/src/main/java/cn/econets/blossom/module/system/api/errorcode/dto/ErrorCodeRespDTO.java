package cn.econets.blossom.module.system.api.errorcode.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Error code Response DTO
 *
 */
@Data
public class ErrorCodeRespDTO {

    /**
     * Error code
     */
    private Integer code;
    /**
     * Error code error prompt
     */
    private String message;
    /**
     * Update time
     */
    private LocalDateTime updateTime;

}
