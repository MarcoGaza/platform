package cn.econets.blossom.module.crm.controller.admin.clue.vo;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.infrastructure.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - Clues Response VO")
@Data
@ToString(callSuper = true)
@ExcelIgnoreUnannotated
public class CrmClueRespVO {

    @Schema(description = "Number，Primary key auto-increment", requiredMode = Schema.RequiredMode.REQUIRED, example = "10969")
    @ExcelProperty("Number")
    private Long id;

    @Schema(description = "Conversion status", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @ExcelProperty(value = "Conversion status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.BOOLEAN_STRING)
    private Boolean transformStatus;

    @Schema(description = "Follow-up status", requiredMode = Schema.RequiredMode.REQUIRED, example = "true")
    @ExcelProperty(value = "Follow-up status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.BOOLEAN_STRING)
    private Boolean followUpStatus;

    @Schema(description = "Clue Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Cluesxxx")
    @ExcelProperty("Clue Name")
    private String name;

    @Schema(description = "Customer id", requiredMode = Schema.RequiredMode.REQUIRED, example = "520")
    // TODO This needs to be exported as customer name
    @ExcelProperty("Customerid")
    private Long customerId;

    @Schema(description = "Next contact time", example = "2023-10-18 01:00:00")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ExcelProperty("Next contact time")
    private LocalDateTime contactNextTime;

    @Schema(description = "Phone", example = "18000000000")
    @ExcelProperty("Phone")
    private String telephone;

    @Schema(description = "Mobile phone number", example = "18000000000")
    @ExcelProperty("Mobile phone number")
    private String mobile;

    @Schema(description = "Address", example = "Haidian District, Beijing")
    @ExcelProperty("Address")
    private String address;

    @Schema(description = "Person in charge number")
    @ExcelProperty("User ID of the person in charge")
    private Long ownerUserId;

    @Schema(description = "Last follow-up time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @ExcelProperty("Last follow-up time")
    private LocalDateTime contactLastTime;

    @Schema(description = "Remarks", example = "Whatever")
    @ExcelProperty("Remarks")
    private String remark;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

    @Schema(description = "Industry", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    @ExcelProperty(value = "Industry", converter = DictConvert.class)
    @DictFormat(cn.econets.blossom.module.crm.enums.DictTypeConstants.CRM_CUSTOMER_INDUSTRY)
    private Integer industryId;

    @Schema(description = "Customer Level", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    @ExcelProperty(value = "Customer Level", converter = DictConvert.class)
    @DictFormat(cn.econets.blossom.module.crm.enums.DictTypeConstants.CRM_CUSTOMER_LEVEL)
    private Integer level;

    @Schema(description = "Customer Source", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    @ExcelProperty(value = "Customer Source", converter = DictConvert.class)
    @DictFormat(cn.econets.blossom.module.crm.enums.DictTypeConstants.CRM_CUSTOMER_SOURCE)
    private Integer source;

    @Schema(description = "Website", example = "25682")
    @ExcelProperty("Website")
    private String website;

    @Schema(description = "QQ", example = "25682")
    @ExcelProperty("QQ")
    private String qq;

    @Schema(description = "wechat", example = "25682")
    @ExcelProperty("wechat")
    private String wechat;

    @Schema(description = "email", example = "25682")
    @ExcelProperty("email")
    private String email;

    @Schema(description = "Customer Description", example = "25682")
    @ExcelProperty("Customer Description")
    private String description;

}
