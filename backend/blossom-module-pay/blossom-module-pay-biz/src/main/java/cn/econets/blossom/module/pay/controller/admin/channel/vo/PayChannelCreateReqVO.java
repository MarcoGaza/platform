package cn.econets.blossom.module.pay.controller.admin.channel.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Payment channel Create Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayChannelCreateReqVO extends PayChannelBaseVO {

    @Schema(description = "Channel Code", requiredMode = Schema.RequiredMode.REQUIRED, example = "alipay_pc")
    @NotNull(message = "Channel code cannot be empty")
    private String code;

    @Schema(description = "Channel configuration json String")
    @NotBlank(message = "Channel configuration cannot be empty")
    private String config;

}
