package cn.econets.blossom.module.crm.controller.admin.contact.vo;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.infrastructure.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "Management Backend - CRM Contact Person Response VO")
@Data
@ToString(callSuper = true)
@ExcelIgnoreUnannotated
public class CrmContactRespVO {

    @Schema(description = "Primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "3167")
    private Long id;

    @Schema(description = "Name", example = "econets")
    @ExcelProperty(value = "Name", order = 1)
    private String name;

    @Schema(description = "Customer Number", example = "10795")
    private Long customerId;

    @Schema(description = "Gender")
    @ExcelProperty(value = "Gender", converter = DictConvert.class, order = 3)
    @DictFormat(cn.econets.blossom.module.system.enums.DictTypeConstants.USER_SEX)
    private Integer sex;

    @Schema(description = "Position")
    @ExcelProperty(value = "Position", order = 3)
    private String post;

    @Schema(description = "Is he a key decision maker?")
    @ExcelProperty(value = "Is he a key decision maker?", converter = DictConvert.class, order = 3)
    @DictFormat(DictTypeConstants.BOOLEAN_STRING)
    private Boolean master;

    @Schema(description = "Direct superior", example = "23457")
    private Long parentId;

    @Schema(description = "Mobile phone number", example = "1387171766")
    @ExcelProperty(value = "Mobile phone number", order = 4)
    private String mobile;

    @Schema(description = "Phone", example = "021-0029922")
    @ExcelProperty(value = "Phone", order = 4)
    private String telephone;

    @Schema(description = "QQ", example = "197272662")
    @ExcelProperty(value = "QQ", order = 4)
    private Long qq;

    @Schema(description = "WeChat", example = "zzz3883")
    @ExcelProperty(value = "WeChat", order = 4)
    private String wechat;

    @Schema(description = "Email", example = "1111@22.com")
    @ExcelProperty(value = "Email", order = 4)
    private String email;

    @Schema(description = "Region Code", example = "20158")
    private Integer areaId;

    @Schema(description = "Address")
    @ExcelProperty(value = "Address", order = 5)
    private String detailAddress;

    @Schema(description = "Remarks", example = "You are right")
    @ExcelProperty(value = "Remarks", order = 6)
    private String remark;

    @Schema(description = "User ID of the person in charge", example = "14334")
    private Long ownerUserId;

    @Schema(description = "Last follow-up time")
    @ExcelProperty(value = "Last follow-up time", order = 6)
    private LocalDateTime contactLastTime;

    @Schema(description = "Next contact time")
    @ExcelProperty(value = "Next contact time", order = 6)
    private LocalDateTime contactNextTime;

    @Schema(description = "Creator", example = "25682")
    private String creator;

    @Schema(description = "Creator's name", example = "test")
    @ExcelProperty(value = "Creator", order = 8)
    private String creatorName;

    @ExcelProperty(value = "Customer Name", order = 2)
    @Schema(description = "Customer Name", example = "test")
    private String customerName;

    @Schema(description = "Person in charge", example = "test")
    @ExcelProperty(value = "Person in charge", order = 7)
    private String ownerUserName;

    @Schema(description = "Name of immediate superior", example = "Taro")
    @ExcelProperty(value = "Direct superior", order = 4)
    private String parentName;

    @Schema(description = "Region name", example = "Shanghai Pudong New Area")
    @ExcelProperty(value = "Region", order = 5)
    private String areaName;

}
