package cn.econets.blossom.module.member.controller.admin.level.vo.experience;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Member Experience Record Paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberExperienceRecordPageReqVO extends PageParam {

    @Schema(description = "User Number", example = "3638")
    private Long userId;

    @Schema(description = "Business Number", example = "12164")
    private String bizId;

    @Schema(description = "Business Type", example = "1")
    private Integer bizType;

    @Schema(description = "Title", example = "Increase experience")
    private String title;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
