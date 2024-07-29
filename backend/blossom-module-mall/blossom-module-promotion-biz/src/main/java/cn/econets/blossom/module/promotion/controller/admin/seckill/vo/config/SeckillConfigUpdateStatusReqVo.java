package cn.econets.blossom.module.promotion.controller.admin.seckill.vo.config;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Modify time period configuration status Request VO")
@Data
public class SeckillConfigUpdateStatusReqVo {

    @Schema(description = "Time period configuration number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Time period configuration number cannot be empty")
    private Long id;

    @Schema(description = "Status，See you CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    @InEnum(value = CommonStatusEnum.class, message = "The modification status must be {value}")
    private Integer status;

}
