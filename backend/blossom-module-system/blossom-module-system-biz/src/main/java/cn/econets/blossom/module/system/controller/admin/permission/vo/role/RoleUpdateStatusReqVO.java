package cn.econets.blossom.module.system.controller.admin.permission.vo.role;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Role update status Request VO")
@Data
public class RoleUpdateStatusReqVO {

    @Schema(description = "Role number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "Role number cannot be empty")
    private Long id;

    @Schema(description = "Status,See you CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    @InEnum(value = CommonStatusEnum.class, message = "The modification status must be {value}")
    private Integer status;

}
