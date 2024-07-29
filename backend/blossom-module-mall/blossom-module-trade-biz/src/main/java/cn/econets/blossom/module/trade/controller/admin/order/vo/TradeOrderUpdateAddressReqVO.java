package cn.econets.blossom.module.trade.controller.admin.order.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Order modification address Request VO")
@Data
public class TradeOrderUpdateAddressReqVO {

    @Schema(description = "Order number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Order number cannot be empty")
    private Long id;

    @Schema(description = "Recipient Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "zZhang San")
    @NotEmpty(message = "The recipient name cannot be empty")
    private String receiverName;

    @Schema(description = "Recipient's mobile phone number", requiredMode = Schema.RequiredMode.REQUIRED, example = "19988188888")
    @NotEmpty(message = "The recipient's mobile phone number cannot be empty")
    private String receiverMobile;

    @Schema(description = "Recipient's area code", requiredMode = Schema.RequiredMode.REQUIRED, example = "7310")
    @NotNull(message = "The recipient's region code cannot be empty")
    private Integer receiverAreaId;

    @Schema(description = "Detailed address of recipient", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wuhua District, Kunming CityxxxCommunityxxx")
    @NotEmpty(message = "The recipient's detailed address cannot be empty")
    private String receiverDetailAddress;

}
