package cn.econets.blossom.module.crm.controller.admin.contact.vo;

import cn.econets.blossom.framework.common.validation.Mobile;
import cn.econets.blossom.framework.common.validation.Telephone;
import cn.econets.blossom.module.crm.framework.operatelog.core.*;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - CRM Contact Creation/Update Request VO")
@Data
public class CrmContactSaveReqVO {

    @Schema(description = "Primary key", example = "3167")
    private Long id;

    @Schema(description = "Name", example = "econets")
    @NotNull(message = "Name cannot be empty")
    @DiffLogField(name = "Name")
    private String name;

    @Schema(description = "Customer Number", example = "10795")
    @DiffLogField(name = "Customer", function = CrmCustomerParseFunction.NAME)
    private Long customerId;

    @Schema(description = "Gender")
    @DiffLogField(name = "Gender", function = SysSexParseFunction.NAME)
    private Integer sex;

    @Schema(description = "Position")
    @DiffLogField(name = "Position")
    private String post;

    @Schema(description = "Is he a key decision maker?")
    @DiffLogField(name = "Key decision maker", function = SysBooleanParseFunction.NAME)
    private Boolean master;

    @Schema(description = "Direct superior", example = "23457")
    @DiffLogField(name = "Direct superior", function = CrmContactParseFunction.NAME)
    private Long parentId;

    @Schema(description = "Mobile phone number", example = "1387171766")
    @Mobile
    @DiffLogField(name = "Mobile phone number")
    private String mobile;

    @Schema(description = "Phone", example = "021-0029922")
    @Telephone
    @DiffLogField(name = "Phone")
    private String telephone;

    @Schema(description = "QQ", example = "197272662")
    @DiffLogField(name = "QQ")
    private Long qq;

    @Schema(description = "WeChat", example = "zzz3883")
    @DiffLogField(name = "WeChat")
    private String wechat;

    @Schema(description = "Email", example = "1111@22.com")
    @DiffLogField(name = "Mailbox")
    @Email
    private String email;

    @Schema(description = "Region Code", example = "20158")
    @DiffLogField(name = "Location", function = SysAreaParseFunction.NAME)
    private Integer areaId;

    @Schema(description = "Address")
    @DiffLogField(name = "Address")
    private String detailAddress;

    @Schema(description = "Remarks", example = "You are right")
    @DiffLogField(name = "Remarks")
    private String remark;

    @Schema(description = "User ID of the person in charge", example = "14334")
    @NotNull(message = "The person in charge cannot be empty")
    @DiffLogField(name = "Person in charge", function = SysAdminUserParseFunction.NAME)
    private Long ownerUserId;

    @Schema(description = "Last follow-up time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @DiffLogField(name = "Last follow-up time")
    private LocalDateTime contactLastTime;

    @Schema(description = "Next contact time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    @DiffLogField(name = "Next contact time")
    private LocalDateTime contactNextTime;

    @Schema(description = "Related business opportunities ID", example = "122233")
    private Long businessId; // Attention：This field is used in【Business Opportunities】Details interface「Create a new contact」Time，Automatically associate

}
