package cn.econets.blossom.module.member.controller.admin.user.vo;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Member User Paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberUserPageReqVO extends PageParam {

    @Schema(description = "Mobile phone number", example = "15601691300")
    private String mobile;

    @Schema(description = "User Nickname", example = "Li Si")
    private String nickname;

    @Schema(description = "Last login time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] loginDate;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "Member tag number list", example = "[1, 2]")
    private List<Long> tagIds;

    @Schema(description = "Member Level Number", example = "1")
    private Long levelId;

    @Schema(description = "User group number", example = "1")
    private Long groupId;

    // TODO Registered user type；

    // TODO Login user type；

}
