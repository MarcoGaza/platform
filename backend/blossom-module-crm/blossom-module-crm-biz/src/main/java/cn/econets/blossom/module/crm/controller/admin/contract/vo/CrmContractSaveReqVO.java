package cn.econets.blossom.module.crm.controller.admin.contract.vo;

import cn.econets.blossom.module.crm.framework.operatelog.core.CrmBusinessParseFunction;
import cn.econets.blossom.module.crm.framework.operatelog.core.CrmContactParseFunction;
import cn.econets.blossom.module.crm.framework.operatelog.core.CrmCustomerParseFunction;
import cn.econets.blossom.module.crm.framework.operatelog.core.SysAdminUserParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - CRM Contract Creation/Update Request VO")
@Data
public class CrmContractSaveReqVO {

    @Schema(description = "Contract Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10430")
    private Long id;

    @Schema(description = "Contract Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Wang Wu")
    @DiffLogField(name = "Contract Name")
    @NotNull(message = "Contract name cannot be empty")
    private String name;

    @Schema(description = "Customer Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "18336")
    @DiffLogField(name = "Customer", function = CrmCustomerParseFunction.NAME)
    @NotNull(message = "Customer number cannot be empty")
    private Long customerId;

    @Schema(description = "Opportunity Number", example = "10864")
    @DiffLogField(name = "Business Opportunities", function = CrmBusinessParseFunction.NAME)
    private Long businessId;

    @Schema(description = "Order date", requiredMode = Schema.RequiredMode.REQUIRED)
    @DiffLogField(name = "Order date")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @NotNull(message = "Order date cannot be empty")
    private LocalDateTime orderDate;

    @Schema(description = "User ID of the person in charge", requiredMode = Schema.RequiredMode.REQUIRED, example = "17144")
    @DiffLogField(name = "Person in charge", function = SysAdminUserParseFunction.NAME)
    @NotNull(message = "The person in charge cannot be empty")
    private Long ownerUserId;

    // TODO @Automatic generation should be supported in the future；
    @Schema(description = "Contract Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "20230101")
    @DiffLogField(name = "Contract Number")
    @NotNull(message = "Contract number cannot be empty")
    private String no;

    @Schema(description = "Start time")
    @DiffLogField(name = "Start time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "End time")
    @DiffLogField(name = "End time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

    @Schema(description = "Contract Amount", example = "5617")
    @DiffLogField(name = "Contract Amount")
    private Integer price;

    @Schema(description = "Entire order discount")
    @DiffLogField(name = "Entire order discount")
    private Integer discountPercent;

    @Schema(description = "Total amount of product", example = "19510")
    @DiffLogField(name = "Total amount of product")
    private Integer productPrice;

    @Schema(description = "Contact Number", example = "18546")
    @DiffLogField(name = "Contact Person", function = CrmContactParseFunction.NAME)
    private Long contactId;

    @Schema(description = "Company Signatory", example = "14036")
    @DiffLogField(name = "Company Signatory", function = SysAdminUserParseFunction.NAME)
    private Long signUserId;

    @Schema(description = "Remarks", example = "Guess")
    @DiffLogField(name = "Remarks")
    private String remark;


    @Schema(description = "Product List")
    private List<CrmContractProductItem> productItems;

    @Schema(description = "Product List")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CrmContractProductItem {

        @Schema(description = "Product Number", example = "20529")
        @NotNull(message = "Product number cannot be empty")
        private Long id;

        @Schema(description = "Product quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "8911")
        @NotNull(message = "Product quantity cannot be empty")
        private Integer count;

        @Schema(description = "Product discount")
        private Integer discountPercent;

    }

}
