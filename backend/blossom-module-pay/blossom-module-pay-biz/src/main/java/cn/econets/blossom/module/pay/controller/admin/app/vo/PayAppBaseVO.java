package cn.econets.blossom.module.pay.controller.admin.app.vo;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
* Payment application information Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class PayAppBaseVO {

    @Schema(description = "Application name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Xiaodou")
    @NotNull(message = "Application name cannot be empty")
    private String name;

    @Schema(description = "Open status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @NotNull(message = "Open status cannot be empty")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

    @Schema(description = "Remarks", example = "I am a test application")
    private String remark;

    @Schema(description = "Payment result callback address", requiredMode = Schema.RequiredMode.REQUIRED, example = "http://127.0.0.1:48080/pay-callback")
    @NotNull(message = "The callback address of payment result cannot be empty")
    @URL(message = "The callback address of the payment result must be URL Format")
    private String orderNotifyUrl;

    @Schema(description = "Refund result callback address", requiredMode = Schema.RequiredMode.REQUIRED, example = "http://127.0.0.1:48080/refund-callback")
    @NotNull(message = "The callback address of the refund result cannot be empty")
    @URL(message = "The callback address of the refund result must be URL Format")
    private String refundNotifyUrl;

}
