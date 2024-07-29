package cn.econets.blossom.module.crm.controller.admin.contract.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - CRM Contract Response VO")
@Data
@ExcelIgnoreUnannotated
public class CrmContractRespVO {

    @Schema(description = "Contract Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10430")
    @ExcelProperty("Contract Number")
    private Long id;

    @Schema(description = "Contract Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wang Wu")
    @ExcelProperty("Contract Name")
    private String name;

    @Schema(description = "Customer Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "18336")
    @ExcelProperty("Customer Number")
    private Long customerId;
    @Schema(description = "Customer Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "18336")
    @ExcelProperty("Customer Name")
    private String customerName;

    @Schema(description = "Opportunity Number", example = "10864")
    @ExcelProperty("Opportunity Number")
    private Long businessId;
    @Schema(description = "Opportunity Name", example = "10864")
    @ExcelProperty("Opportunity Name")
    private String businessName;

    @Schema(description = "Workflow number", example = "1043")
    @ExcelProperty("Workflow number")
    private Long processInstanceId;

    @Schema(description = "Order date", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Order date")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime orderDate;

    @Schema(description = "User ID of the person in charge", requiredMode = Schema.RequiredMode.REQUIRED, example = "17144")
    @ExcelProperty("User ID of the person in charge")
    private Long ownerUserId;

    // TODO @Automatic generation should be supported in the future；
    @Schema(description = "Contract Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "20230101")
    @ExcelProperty("Contract Number")
    private String no;

    @Schema(description = "Start time")
    @ExcelProperty("Start time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "End time")
    @ExcelProperty("End time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

    @Schema(description = "Contract Amount", example = "5617")
    @ExcelProperty("Contract Amount")
    private Integer price;

    @Schema(description = "Entire order discount")
    @ExcelProperty("Entire order discount")
    private Integer discountPercent;

    @Schema(description = "Total amount of product", example = "19510")
    @ExcelProperty("Total amount of product")
    private Integer productPrice;

    @Schema(description = "Contact Number", example = "18546")
    @ExcelProperty("Contact Number")
    private Long contactId;
    @Schema(description = "Contact number", example = "18546")
    @ExcelProperty("Contact Number")
    private String contactName;

    @Schema(description = "Company Signatory", example = "14036")
    @ExcelProperty("Company Signatory")
    private Long signUserId;
    @Schema(description = "Company Signatory", example = "14036")
    @ExcelProperty("Company Signatory")
    private String signUserName;

    @Schema(description = "Last follow-up time")
    @ExcelProperty("Last follow-up time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime contactLastTime;

    @Schema(description = "Remarks", example = "Guess")
    @ExcelProperty("Remarks")
    private String remark;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Creation time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime createTime;

    @Schema(description = "Creator", example = "25682")
    @ExcelProperty("Creator")
    private String creator;

    @Schema(description = "Creator's name", example = "test")
    @ExcelProperty("Creator's name")
    private String creatorName;

    @Schema(description = "Update time", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("Update time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime updateTime;

    @Schema(description = "Person in charge", example = "test")
    @ExcelProperty("Person in charge")
    private String ownerUserName;

    @Schema(description = "Approval Status", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    @ExcelProperty("Approval Status")
    private Integer auditStatus;

    @Schema(description = "Product List")
    private List<CrmContractProductItemRespVO> productItems;

    // TODO @puhui999：You can call directly Item
    @Schema(description = "Product List")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CrmContractProductItemRespVO {

        @Schema(description = "Product Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "20529")
        private Long id;

        @Schema(description = "Product Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "I am a product")
        private String name;

        @Schema(description = "Product Code", requiredMode = Schema.RequiredMode.REQUIRED, example = "N881")
        private String no;

        @Schema(description = "Unit", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
        private Integer unit;

        @Schema(description = "Price，Unit：Points", requiredMode = Schema.RequiredMode.REQUIRED, example = "100")
        private Integer price;

        @Schema(description = "Product quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "20")
        private Integer count;

        @Schema(description = "Product discount", example = "99")
        private Integer discountPercent;

    }

}
