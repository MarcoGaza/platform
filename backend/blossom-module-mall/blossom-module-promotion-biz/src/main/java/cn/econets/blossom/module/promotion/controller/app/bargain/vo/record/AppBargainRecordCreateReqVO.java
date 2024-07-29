package cn.econets.blossom.module.promotion.controller.app.bargain.vo.record;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "User App - Creation of bargaining record Request VO")
@Data
public class AppBargainRecordCreateReqVO {

    @Schema(description = "Bargaining activity number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The bargaining activity number cannot be empty")
    private Long activityId;

}
