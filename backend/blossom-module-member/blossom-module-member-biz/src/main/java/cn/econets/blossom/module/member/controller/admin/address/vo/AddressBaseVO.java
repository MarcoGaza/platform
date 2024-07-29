package cn.econets.blossom.module.member.controller.admin.address.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.*;
import javax.validation.constraints.*;

/**
 * User's mailing address Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class AddressBaseVO {

    @Schema(description = "Recipient Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhang San")
    @NotNull(message = "The recipient name cannot be empty")
    private String name;

    @Schema(description = "Mobile phone number", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Mobile number cannot be empty")
    private String mobile;

    @Schema(description = "Region Code", requiredMode = Schema.RequiredMode.REQUIRED, example = "15716")
    @NotNull(message = "Region code cannot be empty")
    private Long areaId;

    @Schema(description = "Detailed address of delivery", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The detailed address of the recipient cannot be empty")
    private String detailAddress;

    @Schema(description = "Whether default", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "Should it not be empty by default?")
    private Boolean defaultStatus;

}
