package cn.econets.blossom.module.pay.controller.admin.channel.vo;
import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

/**
* Payment channel Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class PayChannelBaseVO {

    @Schema(description = "Open status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Open status cannot be empty")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

    @Schema(description = "Remarks", example = "I am a small note")
    private String remark;

    @Schema(description = "Channel Rate，Unit：Percentage", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "Channel Rate，Unit：The percentage cannot be empty")
    private Double feeRate;

    @Schema(description = "Application Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Application number cannot be empty")
    private Long appId;

}
