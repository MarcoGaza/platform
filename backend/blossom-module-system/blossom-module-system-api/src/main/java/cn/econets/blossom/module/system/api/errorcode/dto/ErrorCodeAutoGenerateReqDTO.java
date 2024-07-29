package cn.econets.blossom.module.system.api.errorcode.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Error code automatically generated DTO
 *
 */
@Data
@Accessors(chain = true)
public class ErrorCodeAutoGenerateReqDTO {

    /**
     * Application name
     */
    @NotNull(message = "Application name cannot be empty")
    private String applicationName;
    /**
     * Error code
     */
    @NotNull(message = "Error code cannot be empty")
    private Integer code;
    /**
     * Error code error prompt
     */
    @NotEmpty(message = "Error code error prompt cannot be empty")
    private String message;

}
