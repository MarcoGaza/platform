package cn.econets.blossom.module.member.controller.app.address.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

// TODO example Missing
/**
* User's mailing address Base VO，Provide for adding、Modify、Detailed sub VO Use
* If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
*/
@Data
public class AppAddressBaseVO {

    @Schema(description = "Recipient Name", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The recipient name cannot be empty")
    private String name;

    @Schema(description = "Mobile phone number", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Mobile number cannot be empty")
    private String mobile;

    @Schema(description = "Region Code", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Region code cannot be empty")
    private Long areaId;

    @Schema(description = "Detailed address of delivery", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The detailed address of the recipient cannot be empty")
    private String detailAddress;

    @Schema(description = "Whether the default address is", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Whether the default address cannot be empty")
    private Boolean defaultStatus;

}
