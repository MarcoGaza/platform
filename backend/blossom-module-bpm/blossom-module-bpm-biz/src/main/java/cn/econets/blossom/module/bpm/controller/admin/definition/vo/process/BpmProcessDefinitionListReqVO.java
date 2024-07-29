package cn.econets.blossom.module.bpm.controller.admin.definition.vo.process;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Process definition list Request VO")
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BpmProcessDefinitionListReqVO extends PageParam {

    @Schema(description = "Interrupt status-See SuspensionState Enumeration", example = "1")
    private Integer suspensionState;

}
