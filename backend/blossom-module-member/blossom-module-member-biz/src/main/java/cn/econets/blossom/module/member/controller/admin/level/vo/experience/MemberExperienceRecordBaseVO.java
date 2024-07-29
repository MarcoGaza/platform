package cn.econets.blossom.module.member.controller.admin.level.vo.experience;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Member Experience Record Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class MemberExperienceRecordBaseVO {

    @Schema(description = "User Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "3638")
    @NotNull(message = "User ID cannot be empty")
    private Long userId;

    @Schema(description = "Business Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "12164")
    @NotNull(message = "Business number cannot be empty")
    private String bizId;

    @Schema(description = "Business Type", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Business type cannot be empty")
    private Integer bizType;

    @Schema(description = "Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "Increase experience")
    @NotNull(message = "Title cannot be empty")
    private String title;

    @Schema(description = "Experience", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
    @NotNull(message = "Experience cannot be empty")
    private Integer experience;

    @Schema(description = "Experience after change", requiredMode = Schema.RequiredMode.REQUIRED, example = "200")
    @NotNull(message = "The changed experience cannot be empty")
    private Integer totalExperience;

    @Schema(description = "Description", requiredMode = Schema.RequiredMode.REQUIRED, example = "Orders increased 100 Experience")
    @NotNull(message = "Description cannot be empty")
    private String description;

}
