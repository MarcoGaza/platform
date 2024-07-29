package cn.econets.blossom.module.system.controller.admin.socail.vo.user;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - Social user paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SocialUserPageReqVO extends PageParam {

    @Schema(description = "Type of social platform", example = "30")
    private Integer type;

    @Schema(description = "User Nickname", example = "Li Si")
    private String nickname;

    @Schema(description = "Social openid", example = "oz-Jdt0kd_jdhUxJHQdBJMlOFN7w")
    private String openid;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
