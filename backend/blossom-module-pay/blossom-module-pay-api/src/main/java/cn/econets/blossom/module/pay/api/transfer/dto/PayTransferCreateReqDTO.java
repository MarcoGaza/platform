package cn.econets.blossom.module.pay.api.transfer.dto;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.pay.enums.transfer.PayTransferTypeEnum;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * Create a transfer order Request DTO
 *
 *
 */
@Data
public class PayTransferCreateReqDTO {

    /**
     * Application number
     */
    @NotNull(message = "Application number cannot be empty")
    private Long appId;

    @NotEmpty(message = "The transfer channel cannot be empty")
    private String channelCode;

    /**
     * Additional parameters for transfer channels
     */
    private Map<String, String> channelExtras;

    @NotEmpty(message = "User IP Cannot be empty")
    private String userIp;

    /**
     * Type
     */
    @NotNull(message = "Transfer type cannot be empty")
    @InEnum(PayTransferTypeEnum.class)
    private Integer type;


    /**
     * Merchant transfer order number
     */
    @NotEmpty(message = "Merchant transfer order number can be empty")
    private String merchantTransferId;

    /**
     * Transfer amount，Unit：Points
     */
    @Min(value = 1, message = "The transfer amount must be greater than zero")
    @NotNull(message = "The transfer amount cannot be empty")
    private Integer price;

    /**
     * Transfer Title
     */
    @NotEmpty(message = "The transfer title cannot be empty")
    private String subject;

    /**
     * Payee's name
     */
    @NotBlank(message = "The payee's name cannot be empty", groups = {PayTransferTypeEnum.Alipay.class})
    private String userName;

    @NotBlank(message = "Alipay login number cannot be empty", groups = {PayTransferTypeEnum.Alipay.class})
    private String alipayLogonId;

    // ========== WeChat transfer related fields ==========
    @NotBlank(message = "WeChat openId Cannot be empty", groups = {PayTransferTypeEnum.WxPay.class})
    private String openid;
}
