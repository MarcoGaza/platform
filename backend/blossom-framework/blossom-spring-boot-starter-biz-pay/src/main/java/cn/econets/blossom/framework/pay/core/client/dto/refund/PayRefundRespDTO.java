package cn.econets.blossom.framework.pay.core.client.dto.refund;

import cn.econets.blossom.framework.pay.core.client.exception.PayException;
import cn.econets.blossom.framework.pay.core.enums.refund.PayRefundStatusRespEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Channel refund order Response DTO
 *
 */
@Data
public class PayRefundRespDTO {

    /**
     * Refund Status
     *
     * Enumeration {@link PayRefundStatusRespEnum}
     */
    private Integer status;

    /**
     * External refund number
     *
     * Corresponding PayRefundDO of no Field
     */
    private String outRefundNo;

    /**
     * Channel refund order number
     *
     * Corresponding PayRefundDO.channelRefundNo Field
     */
    private String channelRefundNo;

    /**
     * Refund success time
     */
    private LocalDateTime successTime;

    /**
     * Original asynchronous notification result
     */
    private Object rawData;

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

    private PayRefundRespDTO() {
    }

    /**
     * Create【WAITING】Status of refund return
     */
    public static PayRefundRespDTO waitingOf(String channelRefundNo,
                                             String outRefundNo, Object rawData) {
        PayRefundRespDTO respDTO = new PayRefundRespDTO();
        respDTO.status = PayRefundStatusRespEnum.WAITING.getStatus();
        respDTO.channelRefundNo = channelRefundNo;
        // Relatively common fields
        respDTO.outRefundNo = outRefundNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

    /**
     * Create【SUCCESS】Status of refund return
     */
    public static PayRefundRespDTO successOf(String channelRefundNo, LocalDateTime successTime,
                                             String outRefundNo, Object rawData) {
        PayRefundRespDTO respDTO = new PayRefundRespDTO();
        respDTO.status = PayRefundStatusRespEnum.SUCCESS.getStatus();
        respDTO.channelRefundNo = channelRefundNo;
        respDTO.successTime = successTime;
        // Relatively common fields
        respDTO.outRefundNo = outRefundNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

    /**
     * Create【FAILURE】Refund status returned
     */
    public static PayRefundRespDTO failureOf(String outRefundNo, Object rawData) {
        return failureOf(null, null,
                outRefundNo, rawData);
    }

    /**
     * Create【FAILURE】Refund status returned
     */
    public static PayRefundRespDTO failureOf(String channelErrorCode, String channelErrorMsg,
                                             String outRefundNo, Object rawData) {
        PayRefundRespDTO respDTO = new PayRefundRespDTO();
        respDTO.status = PayRefundStatusRespEnum.FAILURE.getStatus();
        respDTO.channelErrorCode = channelErrorCode;
        respDTO.channelErrorMsg = channelErrorMsg;
        // Relatively common fields
        respDTO.outRefundNo = outRefundNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

}
