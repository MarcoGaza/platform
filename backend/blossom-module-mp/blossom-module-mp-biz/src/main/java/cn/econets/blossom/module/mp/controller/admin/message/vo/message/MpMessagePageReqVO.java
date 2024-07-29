package cn.econets.blossom.module.mp.controller.admin.message.vo.message;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Paging of public account messages Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MpMessagePageReqVO extends PageParam {

    @Schema(description = "The public account number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The public account number cannot be empty")
    private Long accountId;

    @Schema(description = "Message Type See WxConsts.XmlMsgType Enumeration", example = "text")
    private String type;

    @Schema(description = "Public account fan logo", example = "o6_bmjrPTlm6_2sgVt7hMZOPfL2M")
    private String openid;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "Creation time")
    private LocalDateTime[] createTime;

}
