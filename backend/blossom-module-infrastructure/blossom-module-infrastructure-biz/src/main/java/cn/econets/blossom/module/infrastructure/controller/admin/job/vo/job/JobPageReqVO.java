package cn.econets.blossom.module.infrastructure.controller.admin.job.vo.job;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "Management Backend - Scheduled task paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class JobPageReqVO extends PageParam {

    @Schema(description = "Task Name，Fuzzy matching", example = "Test task")
    private String name;

    @Schema(description = "Task Status，See JobStatusEnum Enumeration", example = "1")
    private Integer status;

    @Schema(description = "Processor name，Fuzzy matching", example = "sysUserSessionTimeoutJob")
    private String handlerName;

}
