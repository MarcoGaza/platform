package cn.econets.blossom.framework.pay.core.client.dto.order;

import cn.econets.blossom.framework.pay.core.client.exception.PayException;
import cn.econets.blossom.framework.pay.core.enums.order.PayOrderDisplayModeEnum;
import cn.econets.blossom.framework.pay.core.enums.order.PayOrderStatusRespEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Channel payment order Response DTO
 *
 */
@Data
public class PayOrderRespDTO {

    /**
     * Payment Status
     *
     * Enumeration：{@link PayOrderStatusRespEnum}
     */
    private Integer status;

    /**
     * External order number
     *
     * Corresponding PayOrderExtensionDO of no Field
     */
    private String outTradeNo;

    /**
     * Payment channel number
     */
    private String channelOrderNo;
    /**
     * Payment channel user number
     */
    private String channelUserId;

    /**
     * Payment success time
     */
    private LocalDateTime successTime;

    /**
     * Original synchronization/Asynchronous notification result
     */
    private Object rawData;

    // ========== When actively initiating payment，Fields that will be returned ==========

    /**
     * Display Mode
     *
     * Enumeration {@link PayOrderDisplayModeEnum} Class
     */
    private String displayMode;
    /**
     * Display content
     */
    private String displayContent;

    /**
     * Error code for calling the channel
     *
     * Attention：What is returned here is a business exception，It's not a system abnormality。
     * If it is a system abnormality，will throw {@link PayException}
     */
    private String channelErrorCode;
    /**
     * When calling a channel, an error is reported，Error message
     */
    private String channelErrorMsg;

    public PayOrderRespDTO() {
    }

    /**
     * Create【WAITING】Status of order returned
     */
    public static PayOrderRespDTO waitingOf(String displayMode, String displayContent,
                                            String outTradeNo, Object rawData) {
        PayOrderRespDTO respDTO = new PayOrderRespDTO();
        respDTO.status = PayOrderStatusRespEnum.WAITING.getStatus();
        respDTO.displayMode = displayMode;
        respDTO.displayContent = displayContent;
        // Relatively common fields
        respDTO.outTradeNo = outTradeNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

    /**
     * Create【SUCCESS】State of order returned
     */
    public static PayOrderRespDTO successOf(String channelOrderNo, String channelUserId, LocalDateTime successTime,
                                            String outTradeNo, Object rawData) {
        PayOrderRespDTO respDTO = new PayOrderRespDTO();
        respDTO.status = PayOrderStatusRespEnum.SUCCESS.getStatus();
        respDTO.channelOrderNo = channelOrderNo;
        respDTO.channelUserId = channelUserId;
        respDTO.successTime = successTime;
        // Relatively common fields
        respDTO.outTradeNo = outTradeNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

    /**
     * Create an order return with a specified status，Suitable for payment channel callback
     */
    public static PayOrderRespDTO of(Integer status, String channelOrderNo, String channelUserId, LocalDateTime successTime,
                                     String outTradeNo, Object rawData) {
        PayOrderRespDTO respDTO = new PayOrderRespDTO();
        respDTO.status = status;
        respDTO.channelOrderNo = channelOrderNo;
        respDTO.channelUserId = channelUserId;
        respDTO.successTime = successTime;
        // Relatively common fields
        respDTO.outTradeNo = outTradeNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

    /**
     * Create【CLOSED】Status of order return，Suitable for when payment channel call fails
     */
    public static PayOrderRespDTO closedOf(String channelErrorCode, String channelErrorMsg,
                                           String outTradeNo, Object rawData) {
        PayOrderRespDTO respDTO = new PayOrderRespDTO();
        respDTO.status = PayOrderStatusRespEnum.CLOSED.getStatus();
        respDTO.channelErrorCode = channelErrorCode;
        respDTO.channelErrorMsg = channelErrorMsg;
        // Relatively common fields
        respDTO.outTradeNo = outTradeNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

}
