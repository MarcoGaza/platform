package cn.econets.blossom.framework.common.exception;

import cn.econets.blossom.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.econets.blossom.framework.common.exception.enums.ServiceErrorCodeRange;
import lombok.Data;

/**
 * Error code object
 *
 * Global error code，Occupied [0, 999], See {@link GlobalErrorCodeConstants}
 * Business exception error code，Occupied [1 000 000 000, +∞)，See {@link ServiceErrorCodeRange}
 *
 * TODO The reason why error codes are designed as objects，For the future i18 Prepare for internationalization
 */
@Data
public class ErrorCode {

    /**
     * Error code
     */
    private final Integer code;
    /**
     * Error message
     */
    private final String msg;

    public ErrorCode(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }

}
