package cn.econets.blossom.module.system.controller.admin.sms.vo.template;

import cn.econets.blossom.framework.common.pojo.PageParam;
import cn.econets.blossom.framework.common.util.date.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Schema(description = "Management Backend - SMS template paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SmsTemplatePageReqVO extends PageParam {

    @Schema(description = "SMS signature", example = "1")
    private Integer type;

    @Schema(description = "Open status", example = "1")
    private Integer status;

    @Schema(description = "Template encoding，Fuzzy matching", example = "test_01")
    private String code;

    @Schema(description = "Template content，Fuzzy matching", example = "Hello，{name}。You are too long{like}La！")
    private String content;

    @Schema(description = "SMS API Template number，Fuzzy matching", example = "4383920")
    private String apiTemplateId;

    @Schema(description = "SMS channel number", example = "10")
    private Long channelId;

    @DateTimeFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Creation time")
    private LocalDateTime[] createTime;

}
