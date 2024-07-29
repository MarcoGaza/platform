package cn.econets.blossom.module.system.controller.admin.dict.vo.type;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(description = "Management Backend - Dictionary type creation/Modify Request VO")
@Data
public class DictTypeSaveReqVO {

    @Schema(description = "Dictionary type number", example = "1024")
    private Long id;

    @Schema(description = "Dictionary name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Gender")
    @NotBlank(message = "Dictionary name cannot be empty")
    @Size(max = 100, message = "The length of the dictionary type name cannot exceed100Characters")
    private String name;

    @Schema(description = "Dictionary type", requiredMode = Schema.RequiredMode.REQUIRED, example = "sys_common_sex")
    @NotNull(message = "Dictionary type cannot be empty")
    @Size(max = 100, message = "The length of dictionary type cannot exceed 100 Characters")
    private String type;

    @Schema(description = "Status，See CommonStatusEnum Enumeration class", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    private Integer status;

    @Schema(description = "Remarks", example = "Happy Notes")
    private String remark;

}
