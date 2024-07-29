package cn.econets.blossom.module.pay.controller.admin.channel.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description = "Management Backend - Payment channel Update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayChannelUpdateReqVO extends PayChannelBaseVO {

    @Schema(description = "Merchant Number", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Merchant number cannot be empty")
    private Long id;

    @Schema(description = "Channel configurationjsonString")
    @NotBlank(message = "Channel configuration cannot be empty")
    private String config;

}
