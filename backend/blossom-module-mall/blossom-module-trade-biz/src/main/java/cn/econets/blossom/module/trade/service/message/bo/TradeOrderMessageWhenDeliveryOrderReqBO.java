package cn.econets.blossom.module.trade.service.message.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Notify when order is shipped Req BO
 *
 */
@Data
public class TradeOrderMessageWhenDeliveryOrderReqBO {

    /**
     * Order number
     */
    @NotNull(message = "Order number cannot be empty")
    private Long orderId;
    /**
     * User Number
     */
    @NotNull(message = "User ID cannot be empty")
    private Long userId;
    /**
     * Message
     */
    @NotEmpty(message = "The message to be sent cannot be empty")
    private String message;

}
