package cn.econets.blossom.module.crm.controller.admin.clue.vo;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.framework.common.validation.Mobile;
import cn.econets.blossom.framework.common.validation.Telephone;
import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.module.crm.enums.customer.CrmCustomerLevelEnum;
import cn.econets.blossom.module.crm.framework.operatelog.core.CrmCustomerIndustryParseFunction;
import cn.econets.blossom.module.crm.framework.operatelog.core.CrmCustomerLevelParseFunction;
import cn.econets.blossom.module.crm.framework.operatelog.core.CrmCustomerSourceParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;
import static cn.econets.blossom.module.crm.enums.DictTypeConstants.CRM_CUSTOMER_INDUSTRY;

@Schema(description = "Management Backend - CRM Clues Create/Update Request VO")
@Data
public class CrmClueSaveReqVO {

    @Schema(description = "Number", example = "10969")
    private Long id;

    @Schema(description = "Clue Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Cluesxxx")
    @DiffLogField(name = "Clue Name")
    @NotEmpty(message = "The clue name cannot be empty")
    private String name;

    @Schema(description = "Next contact time", example = "2023-10-18 01:00:00")
    @DiffLogField(name = "Next contact time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime contactNextTime;

    @Schema(description = "Phone", example = "18000000000")
    @DiffLogField(name = "Phone")
    @Telephone
    private String telephone;

    @Schema(description = "Mobile phone number", example = "18000000000")
    @DiffLogField(name = "Mobile phone number")
    @Mobile
    private String mobile;

    @Schema(description = "Address", example = "Haidian District, Beijing")
    @DiffLogField(name = "Address")
    private String address;

    @Schema(description = "Last follow-up time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @DiffLogField(name = "Last follow-up time")
    private LocalDateTime contactLastTime;

    @Schema(description = "Person in charge number", example = "2048")
    private Long ownerUserId;

    @Schema(description = "Remarks", example = "Whatever")
    @DiffLogField(name = "Remarks")
    private String remark;

    @Schema(description = "Industry", example = "1")
    @DiffLogField(name = "Industry", function = CrmCustomerIndustryParseFunction.NAME)
    @DictFormat(CRM_CUSTOMER_INDUSTRY)
    private Integer industryId;

    @Schema(description = "Customer Level", example = "2")
    @DiffLogField(name = "Customer Level", function = CrmCustomerLevelParseFunction.NAME)
    @InEnum(CrmCustomerLevelEnum.class)
    private Integer level;

    @Schema(description = "Customer Source", example = "3")
    @DiffLogField(name = "Customer Source", function = CrmCustomerSourceParseFunction.NAME)
    private Integer source;

    @Schema(description = "Website", example = "https://www.baidu.com")
    @DiffLogField(name = "Website")
    private String website;

    @Schema(description = "QQ", example = "123456789")
    @DiffLogField(name = "QQ")
    @Size(max = 20, message = "QQThe length cannot exceed 20 Characters")
    private String qq;

    @Schema(description = "WeChat", example = "123456789")
    @DiffLogField(name = "WeChat")
    @Size(max = 255, message = "The length of WeChat cannot exceed 255 Characters")
    private String wechat;

    @Schema(description = "Email", example = "123456789@qq.com")
    @DiffLogField(name = "Email")
    @Email(message = "The email format is incorrect")
    @Size(max = 255, message = "The length of the email address cannot exceed 255 Characters")
    private String email;

    @Schema(description = "Customer Description", example = "Any text")
    @DiffLogField(name = "Customer Description")
    @Size(max = 4096, message = "The length of the customer description cannot exceed 4096 Characters")
    private String description;
}
