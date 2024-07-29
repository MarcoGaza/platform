package cn.econets.blossom.module.pay.controller.admin.demo.vo.order;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Sample order creation Request VO")
@Data
public class PayDemoOrderCreateReqVO {

    @Schema(description = "Product Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "17682")
    @NotNull(message = "Product number cannot be empty")
    private Long spuId;

}
