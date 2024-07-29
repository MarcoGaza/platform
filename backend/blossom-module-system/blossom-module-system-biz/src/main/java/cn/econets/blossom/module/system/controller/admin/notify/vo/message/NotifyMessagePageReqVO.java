package cn.econets.blossom.module.system.controller.admin.notify.vo.message;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Paging of internal messages Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NotifyMessagePageReqVO extends PageParam {

    @Schema(description = "User Number", example = "25025")
    private Long userId;

    @Schema(description = "User Type", example = "1")
    private Integer userType;

    @Schema(description = "Template encoding", example = "test_01")
    private String templateCode;

    @Schema(description = "Template type", example = "2")
    private Integer templateType;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
