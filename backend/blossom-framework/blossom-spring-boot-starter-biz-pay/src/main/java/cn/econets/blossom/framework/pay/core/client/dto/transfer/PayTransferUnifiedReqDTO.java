package cn.econets.blossom.framework.pay.core.client.dto.transfer;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.framework.pay.core.enums.transfer.PayTransferTypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

import static cn.econets.blossom.framework.pay.core.enums.transfer.PayTransferTypeEnum.*;

/**
 * Uniform transfer Request DTO
 *
 */
@Data
public class PayTransferUnifiedReqDTO {

    /**
     * Transfer Type
     *
     * Relationship {@link PayTransferTypeEnum#getType()}
     */
    @NotNull(message = "Transfer type cannot be empty")
    @InEnum(PayTransferTypeEnum.class)
    private Integer type;

    /**
     * User IP
     */
    @NotEmpty(message = "User IP Cannot be empty")
    private String userIp;

    @NotEmpty(message = "External transfer order number cannot be empty")
    private String outTransferNo;

    /**
     * Transfer amount，Unit：Points
     */
    @NotNull(message = "The transfer amount cannot be empty")
    @Min(value = 1, message = "The transfer amount must be greater than zero")
    private Integer price;

    /**
     * Transfer Title
     */
    @NotEmpty(message = "The transfer title cannot be empty")
    @Length(max = 128, message = "The transfer title cannot exceed 128")
    private String subject;

    /**
     * Payee's name
     */
    @NotBlank(message = "The payee's name cannot be empty", groups = {Alipay.class})
    private String userName;

    /**
     * Alipay login number
     */
    @NotBlank(message = "Alipay login number cannot be empty", groups = {Alipay.class})
    private String alipayLogonId;

    /**
     * WeChat openId
     */
    @NotBlank(message = "WeChat openId Cannot be empty", groups = {WxPay.class})
    private String openid;

    /**
     * Additional parameters for payment channels
     */
    private Map<String, String> channelExtras;
}
