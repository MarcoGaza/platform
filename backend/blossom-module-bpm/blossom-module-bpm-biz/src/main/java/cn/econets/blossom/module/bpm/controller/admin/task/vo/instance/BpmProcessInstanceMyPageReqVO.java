package cn.econets.blossom.module.bpm.controller.admin.task.vo.instance;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Process instance paging Item Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmProcessInstanceMyPageReqVO extends PageParam {

    @Schema(description = "Process name", example = "Taro Road")
    private String name;

    @Schema(description = "Process definition number", example = "2048")
    private String processDefinitionId;

    @Schema(description = "The status of the process instance-See bpm_process_instance_status", example = "1")
    private Integer status;

    @Schema(description = "Results of process instance-See bpm_process_instance_result", example = "2")
    private Integer result;

    @Schema(description = "Process Classification-See bpm_model_category Data dictionary", example = "1")
    private String category;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
