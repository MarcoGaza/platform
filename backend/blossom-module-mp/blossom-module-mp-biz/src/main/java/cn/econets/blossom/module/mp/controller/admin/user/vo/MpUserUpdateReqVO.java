package cn.econets.blossom.module.mp.controller.admin.user.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - Public account fans update Request VO")
@Data
public class MpUserUpdateReqVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The number cannot be empty")
    private Long id;

    @Schema(description = "Nickname", example = "Taro Road")
    private String nickname;

    @Schema(description = "Remarks", example = "You are a taro")
    private String remark;

    @Schema(description = "Tag number array", example = "1,2,3")
    private List<Long> tagIds;

}
