package cn.econets.blossom.module.trade.controller.admin.brokerage.vo.user;

import cn.econets.blossom.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Distribution user paging Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BrokerageUserPageReqVO extends PageParam {

    @Schema(description = "Promoter Number", example = "4587")
    private Long bindUserId;

    @Schema(description = "Promotion Qualification", example = "true")
    private Boolean brokerageEnabled;

    @Schema(description = "Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "User Level", example = "1") // Attention，This is not the user's membership level，It is the level of filtering promotion
    private Integer level;

    @Schema(description = "Binding time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] bindUserTime;

}
