package cn.econets.blossom.framework.pay.core.client.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Payment system abnormality Exception
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayException extends RuntimeException {

    public PayException(Throwable cause) {
        super(cause);
    }

}
