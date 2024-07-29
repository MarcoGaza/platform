package cn.econets.blossom.module.bpm.controller.admin.definition.vo.form;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Dynamic form paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmFormPageReqVO extends PageParam {

    @Schema(description = "Form Name", example = "Taro Road")
    private String name;

}
