package cn.econets.blossom.module.trade.api.order.dto;

import cn.econets.blossom.framework.common.enums.TerminalEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderCancelTypeEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderStatusEnum;
import cn.econets.blossom.module.trade.enums.order.TradeOrderTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Order Information Response DTO
 *
 */
@Data
public class TradeOrderRespDTO {

    // ========== Basic order information ==========
    /**
     * Order Number，Primary key auto-increment
     */
    private Long id;
    /**
     * Order serial number
     *
     * For example，1146347329394184195
     */
    private String no;
    /**
     * Order Type
     *
     * Enumeration {@link TradeOrderTypeEnum}
     */
    private Integer type;
    /**
     * Order source
     *
     * Enumeration {@link TerminalEnum}
     */
    private Integer terminal;
    /**
     * User Number
     */
    private Long userId;
    /**
     * User IP
     */
    private String userIp;
    /**
     * User Notes
     */
    private String userRemark;
    /**
     * Order Status
     *
     * Enumeration {@link TradeOrderStatusEnum}
     */
    private Integer status;
    /**
     * Quantity of goods purchased
     */
    private Integer productCount;
    /**
     * Order completion time
     */
    private LocalDateTime finishTime;
    /**
     * Order cancellation time
     */
    private LocalDateTime cancelTime;
    /**
     * Cancel type
     *
     * Enumeration {@link TradeOrderCancelTypeEnum}
     */
    private Integer cancelType;
    /**
     * Merchant Notes
     */
    private String remark;
    /**
     * Do you want to comment?
     */
    private Boolean commentStatus;

    // ========== Price + Basic payment information ==========
    /**
     * Payment order number
     */
    private Long payOrderId;
    /**
     * Has it been paid?
     */
    private Boolean payStatus;

}
