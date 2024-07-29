package cn.econets.blossom.module.infrastructure.controller.admin.demo.demo01.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "Management Backend - Add new sample contact/Modify Request VO")
@Data
public class Demo01ContactSaveReqVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "21555")
    private Long id;

    @Schema(description = "Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Zhang San")
    @NotEmpty(message = "The name cannot be empty")
    private String name;

    @Schema(description = "Gender", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Gender cannot be empty")
    private Integer sex;

    @Schema(description = "Year of Birth", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Birth year cannot be empty")
    private LocalDateTime birthday;

    @Schema(description = "Introduction", requiredMode = Schema.RequiredMode.REQUIRED, example = "You are right")
    @NotEmpty(message = "Introduction cannot be empty")
    private String description;

    @Schema(description = "Avatar")
    private String avatar;

}
