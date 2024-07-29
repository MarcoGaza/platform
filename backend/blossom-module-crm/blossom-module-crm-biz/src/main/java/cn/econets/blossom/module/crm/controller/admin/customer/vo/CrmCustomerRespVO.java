package cn.econets.blossom.module.crm.controller.admin.customer.vo;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.infrastructure.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - CRM Customer Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmCustomerRespVO {

    @Schema(description = "Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    @ExcelProperty("Number")
    private Long id;

    @Schema(description = "Customer Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    @ExcelProperty("Customer Name")
    private String name;

    @Schema(description = "Follow-up status", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    @ExcelProperty(value = "Follow-up status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.BOOLEAN_STRING)
    private Boolean followUpStatus;

    @Schema(description = "Locked status", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    @ExcelProperty(value = "Locked status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.BOOLEAN_STRING)
    private Boolean lockStatus;

    @Schema(description = "Transaction status", requiredMode = Schema.RequiredMode.REQUIRED, example = "13563")
    @ExcelProperty(value = "Transaction status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.BOOLEAN_STRING)
    private Boolean dealStatus;

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

    @Schema(description = "User ID of the person in charge", example = "25682")
    @ExcelProperty("Mobile phone")
    private String mobile;

    @Schema(description = "User ID of the person in charge", example = "25682")
    @ExcelProperty("Phone")
    private String telephone;

    @Schema(description = "User ID of the person in charge", example = "25682")
    @ExcelProperty("Website")
    private String website;

    @Schema(description = "User ID of the person in charge", example = "25682")
    @ExcelProperty("QQ")
    private String qq;

    @Schema(description = "User ID of the person in charge", example = "25682")
    @ExcelProperty("wechat")
    private String wechat;

    @Schema(description = "User ID of the person in charge", example = "25682")
    @ExcelProperty("email")
    private String email;

    @Schema(description = "User ID of the person in charge", example = "25682")
    @ExcelProperty("Customer Description")
    private String description;

    @Schema(description = "User ID of the person in charge", example = "25682")
    @ExcelProperty("Remarks")
    private String remark;

    @Schema(description = "User ID of the person in charge", example = "25682")
    @ExcelProperty("User ID of the person in charge")
    private Long ownerUserId;
    @Schema(description = "Name of person in charge", example = "25682")
    @ExcelProperty("Name of person in charge")
    private String ownerUserName;
    @Schema(description = "Responsible department")
    @ExcelProperty("Responsible department")
    private String ownerUserDeptName;

    @Schema(description = "Region Code", example = "1024")
    @ExcelProperty("Region Code")
    private Integer areaId;
    @Schema(description = "Region Name", example = "Beijing City")
    @ExcelProperty("Region Name")
    private String areaName;
    @Schema(description = "Detailed address", example = "Chenghua Avenue, Beijing")
    @ExcelProperty("Detailed address")
    private String detailAddress;

    @Schema(description = "Last follow-up time")
    @ExcelProperty("Last follow-up time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime contactLastTime;

    @Schema(description = "Next contact time")
    @ExcelProperty("Next contact time")
    private LocalDateTime contactNextTime;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

    @Schema(description = "Update time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Update time")
    private LocalDateTime updateTime;

    @Schema(description = "Creator", example = "1024")
    @ExcelProperty("Creator")
    private String creator;
    @Schema(description = "Creator's name", example = "Yudao source code")
    @ExcelProperty("Creator's name")
    private String creatorName;

    @Schema(description = "Time until joining the high seas", example = "1")
    private Long poolDay;

}
