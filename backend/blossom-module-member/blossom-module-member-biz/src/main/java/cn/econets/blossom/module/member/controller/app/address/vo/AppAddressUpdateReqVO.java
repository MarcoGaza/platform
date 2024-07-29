package cn.econets.blossom.module.member.controller.app.address.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description = "User APP - User mailing address update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AppAddressUpdateReqVO extends AppAddressBaseVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The number cannot be empty")
    private Long id;

}
