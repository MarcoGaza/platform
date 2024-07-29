package cn.econets.blossom.module.pay.controller.admin.app.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Application update status Request VO")
@Data
public class PayAppUpdateStatusReqVO {

    @Schema(description = "Application Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Application number cannot be empty")
    private Long id;

    @Schema(description = "Status，See you SysCommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    private Integer status;

}
