package cn.econets.blossom.module.mp.controller.admin.message.vo.autoreply;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Updates to automatic replies on public accounts Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MpAutoReplyUpdateReqVO extends MpAutoReplyBaseVO {

    @Schema(description = "Primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The primary key cannot be empty")
    private Long id;

}
