package cn.econets.blossom.module.trade.controller.app.cart.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Schema(description = "User App - Is shopping cart update selected? Request VO")
@Data
public class AppCartUpdateSelectedReqVO {

    @Schema(description = "Numbered list", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024,2048")
    @NotNull(message = "Number list cannot be empty")
    private Collection<Long> ids;

    @Schema(description = "Is it selected?", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @NotNull(message = "Whether the selection cannot be empty")
    private Boolean selected;

}
