package cn.econets.blossom.module.member.controller.admin.signin.vo.record;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Sign-in record paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MemberSignInRecordPageReqVO extends PageParam {

    @Schema(description = "Sign in user", example = "Potatoes")
    private String nickname;

    @Schema(description = "Sign in on which day", example = "10")
    private Integer day;

    @Schema(description = "User Number", example = "123")
    private Long userId;

    @Schema(description = "Sign-in time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
