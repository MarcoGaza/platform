package cn.econets.blossom.module.trade.service.order.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Creation of order log Request BO
 *
 */
@Data
public class TradeOrderLogCreateReqBO {

    /**
     * User Number
     */
    @NotNull(message = "User ID cannot be empty")
    private Long userId;
    /**
     * User Type
     */
    @NotNull(message = "User type cannot be empty")
    private Integer userType;

    /**
     * Order number
     */
    @NotNull(message = "Order number cannot be empty")
    private Long orderId;
    /**
     * Status before operation
     */
    private Integer beforeStatus;
    /**
     * Status after operation
     */
    @NotNull(message = "The status after operation cannot be empty")
    private Integer afterStatus;

    /**
     * Operation type
     */
    @NotNull(message = "Operation type cannot be empty")
    private Integer operateType;
    /**
     * Operation details
     */
    @NotEmpty(message = "Operation details cannot be empty")
    private String content;

}
