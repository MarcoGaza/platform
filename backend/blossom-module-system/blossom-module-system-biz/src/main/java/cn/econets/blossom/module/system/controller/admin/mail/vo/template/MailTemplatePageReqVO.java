package cn.econets.blossom.module.system.controller.admin.mail.vo.template;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Mail template paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MailTemplatePageReqVO extends PageParam {

    @Schema(description = "Status，See CommonStatusEnum Enumeration", example = "1")
    private Integer status;

    @Schema(description = "Logo，Fuzzy matching", example = "code_1024")
    private String code;

    @Schema(description = "Name，Fuzzy matching", example = "Taro")
    private String name;

    @Schema(description = "Account Number", example = "2048")
    private Long accountId;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}
