package cn.econets.blossom.module.mp.controller.admin.tag.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "Management Backend - Public account tag update Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MpTagUpdateReqVO extends MpTagBaseVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "The number cannot be empty")
    private Long id;

}
