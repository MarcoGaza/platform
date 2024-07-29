package cn.econets.blossom.module.pay.controller.admin.app.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import javax.validation.constraints.*;

@Schema(description = "Management Backend - Payment application information update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PayAppUpdateReqVO extends PayAppBaseVO {

    @Schema(description = "Application number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Application number cannot be empty")
    private Long id;

}
