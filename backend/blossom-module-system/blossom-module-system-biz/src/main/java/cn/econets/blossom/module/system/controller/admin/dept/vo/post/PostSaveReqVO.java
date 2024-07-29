package cn.econets.blossom.module.system.controller.admin.dept.vo.post;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(description = "Management Backend - Job Creation/Modify Request VO")
@Data
public class PostSaveReqVO {

    @Schema(description = "Position number", example = "1024")
    private Long id;

    @Schema(description = "Position Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Little Potato")
    @NotBlank(message = "Position name cannot be empty")
    @Size(max = 50, message = "The length of the job title cannot exceed 50 Characters")
    private String name;

    @Schema(description = "Position Code", requiredMode = Schema.RequiredMode.REQUIRED, example = "admin")
    @NotBlank(message = "Position code cannot be empty")
    @Size(max = 64, message = "The length of the job code cannot exceed64Characters")
    private String code;

    @Schema(description = "The display order cannot be empty", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The display order cannot be empty")
    private Integer sort;

    @Schema(description = "Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @InEnum(CommonStatusEnum.class)
    private Integer status;

    @Schema(description = "Remarks", example = "Happy Notes")
    private String remark;

}
