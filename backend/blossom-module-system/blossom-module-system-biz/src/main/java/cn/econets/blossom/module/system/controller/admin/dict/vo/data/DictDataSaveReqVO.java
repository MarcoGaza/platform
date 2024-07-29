package cn.econets.blossom.module.system.controller.admin.dict.vo.data;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(description = "Management Backend - Dictionary data creation/Modify Request VO")
@Data
public class DictDataSaveReqVO {

    @Schema(description = "Dictionary data number", example = "1024")
    private Long id;

    @Schema(description = "The display order cannot be empty", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The display order cannot be empty")
    private Integer sort;

    @Schema(description = "Dictionary tags", requiredMode = Schema.RequiredMode.REQUIRED, example = "blossom")
    @NotBlank(message = "Dictionary tag cannot be empty")
    @Size(max = 100, message = "Dictionary tag length cannot exceed100Characters")
    private String label;

    @Schema(description = "Dictionary value", requiredMode = Schema.RequiredMode.REQUIRED, example = "econets")
    @NotBlank(message = "Dictionary key value cannot be empty")
    @Size(max = 100, message = "The length of dictionary key value cannot exceed100Characters")
    private String value;

    @Schema(description = "Dictionary type", requiredMode = Schema.RequiredMode.REQUIRED, example = "sys_common_sex")
    @NotBlank(message = "Dictionary type cannot be empty")
    @Size(max = 100, message = "The length of dictionary type cannot exceed100Characters")
    private String dictType;

    @Schema(description = "Status,See you CommonStatusEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "Status cannot be empty")
    @InEnum(value = CommonStatusEnum.class, message = "The modification status must be {value}")
    private Integer status;

    @Schema(description = "Color type,default、primary、success、info、warning、danger", example = "default")
    private String colorType;

    @Schema(description = "css Style", example = "btn-visible")
    private String cssClass;

    @Schema(description = "Remarks", example = "I am a character")
    private String remark;

}
