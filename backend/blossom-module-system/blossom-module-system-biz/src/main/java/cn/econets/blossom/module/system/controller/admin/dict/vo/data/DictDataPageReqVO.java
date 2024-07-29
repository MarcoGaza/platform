package cn.econets.blossom.module.system.controller.admin.dict.vo.data;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.validation.InEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Size;

@Schema(description = "Management Backend - Dictionary type pagination list Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
public class DictDataPageReqVO extends PageParam {

    @Schema(description = "Dictionary tags", example = "blossom")
    @Size(max = 100, message = "Dictionary tag length cannot exceed100Characters")
    private String label;

    @Schema(description = "Dictionary type，Fuzzy matching", example = "sys_common_sex")
    @Size(max = 100, message = "The length of dictionary type cannot exceed100Characters")
    private String dictType;

    @Schema(description = "Display status，See CommonStatusEnum Enumeration class", example = "1")
    @InEnum(value = CommonStatusEnum.class, message = "The modification status must be {value}")
    private Integer status;

}
