package cn.econets.blossom.module.system.controller.admin.sensitiveword.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - Sensitive word creation/Modify Request VO")
@Data
public class SensitiveWordSaveVO {

    @Schema(description = "Number", example = "1")
    private Long id;

    @Schema(description = "Sensitive words", requiredMode = Schema.RequiredMode.REQUIRED, example = "Sensitive words")
    @NotNull(message = "Sensitive words cannot be empty")
    private String name;

    @Schema(description = "Tag", requiredMode = Schema.RequiredMode.REQUIRED, example = "SMS,Comments")
    @NotNull(message = "Tag cannot be empty")
    private List<String> tags;

    @Schema(description = "Status，See CommonStatusEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    private Integer status;

    @Schema(description = "Description", example = "Dirty words")
    private String description;

}
