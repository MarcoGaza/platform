package cn.econets.blossom.framework.pay.core.client.dto.transfer;

import cn.econets.blossom.framework.pay.core.enums.transfer.PayTransferStatusRespEnum;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Uniform transfer Response DTO
 *
 */
@Data
public class PayTransferRespDTO {

    /**
     * Transfer Status
     *
     * Relationship {@link  PayTransferStatusRespEnum#getStatus()}
     */
    private Integer status;

    /**
     * External transfer order number
     *
     */
    private String outTransferNo;

    /**
     * Payment channel number
     */
    private String channelTransferNo;

    /**
     * Payment success time
     */
    private LocalDateTime successTime;

    /**
     * Original return result
     */
    private Object rawData;

    /**
     * Error code for calling the channel
     */
    private String channelErrorCode;
    /**
     * When calling a channel, an error is reported，Error message
     */
    private String channelErrorMsg;

    /**
     * Create【WAITING】Status of transfer return
     */
    public static PayTransferRespDTO waitingOf(String channelTransferNo,
                                             String outTransferNo, Object rawData) {
        PayTransferRespDTO respDTO = new PayTransferRespDTO();
        respDTO.status = PayTransferStatusRespEnum.WAITING.getStatus();
        respDTO.channelTransferNo = channelTransferNo;
        respDTO.outTransferNo = outTransferNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

    /**
     * Create【IN_PROGRESS】Status of transfer return
     */
    public static PayTransferRespDTO dealingOf(String channelTransferNo,
                                               String outTransferNo, Object rawData) {
        PayTransferRespDTO respDTO = new PayTransferRespDTO();
        respDTO.status = PayTransferStatusRespEnum.IN_PROGRESS.getStatus();
        respDTO.channelTransferNo = channelTransferNo;
        respDTO.outTransferNo = outTransferNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

    /**
     * Create【CLOSED】Status of transfer return
     */
    public static PayTransferRespDTO closedOf(String channelErrorCode, String channelErrorMsg,
                                              String outTransferNo, Object rawData) {
        PayTransferRespDTO respDTO = new PayTransferRespDTO();
        respDTO.status = PayTransferStatusRespEnum.CLOSED.getStatus();
        respDTO.channelErrorCode = channelErrorCode;
        respDTO.channelErrorMsg = channelErrorMsg;
        // Relatively common fields
        respDTO.outTransferNo = outTransferNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

    /**
     * Create【SUCCESS】Status of transfer return
     */
    public static PayTransferRespDTO successOf(String channelTransferNo, LocalDateTime successTime,
                                             String outTransferNo, Object rawData) {
        PayTransferRespDTO respDTO = new PayTransferRespDTO();
        respDTO.status = PayTransferStatusRespEnum.SUCCESS.getStatus();
        respDTO.channelTransferNo = channelTransferNo;
        respDTO.successTime = successTime;
        // Relatively common fields
        respDTO.outTransferNo = outTransferNo;
        respDTO.rawData = rawData;
        return respDTO;
    }

}
