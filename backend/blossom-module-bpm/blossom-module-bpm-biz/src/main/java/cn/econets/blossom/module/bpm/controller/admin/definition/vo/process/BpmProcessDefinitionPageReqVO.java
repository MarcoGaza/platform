package cn.econets.blossom.module.bpm.controller.admin.definition.vo.process;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Process definition paging Request VO")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BpmProcessDefinitionPageReqVO extends PageParam {

    @Schema(description = "Logo-Exact match", example = "process1641042089407")
    private String key;

}
