package cn.econets.blossom.module.crm.controller.admin.business.vo.business;

import cn.econets.blossom.framework.common.validation.InEnum;
import cn.econets.blossom.module.crm.enums.business.CrmBizEndStatus;
import cn.econets.blossom.module.crm.framework.operatelog.core.CrmCustomerParseFunction;
import com.mzt.logapi.starter.annotation.DiffLogField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static cn.econets.blossom.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "Management Backend - CRM Business opportunity creation/Update Request VO")
@Data
public class CrmBusinessSaveReqVO {

    @Schema(description = "Primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "32129")
    private Long id;

    @Schema(description = "Opportunity Name", requiredMode = Schema.RequiredMode.REQUIRED, example = "Li Si")
    @DiffLogField(name = "Opportunity Name")
    @NotNull(message = "Opportunity name cannot be empty")
    private String name;

    @Schema(description = "Opportunity status type number", requiredMode = Schema.RequiredMode.REQUIRED, example = "25714")
    @DiffLogField(name = "Opportunity Status")
    @NotNull(message = "Opportunity status type cannot be empty")
    private Long statusTypeId;

    @Schema(description = "Opportunity status number", requiredMode = Schema.RequiredMode.REQUIRED, example = "30320")
    @DiffLogField(name = "Opportunity Status")
    @NotNull(message = "Opportunity status cannot be empty")
    private Long statusId;

    @Schema(description = "Next contact time")
    @DiffLogField(name = "Next contact time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime contactNextTime;

    @Schema(description = "Customer Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "10299")
    @DiffLogField(name = "Customer", function = CrmCustomerParseFunction.NAME)
    @NotNull(message = "Customer cannot be empty")
    private Long customerId;

    @Schema(description = "Expected transaction date")
    @DiffLogField(name = "Expected transaction date")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime dealTime;

    @Schema(description = "Opportunity Amount", example = "12371")
    @DiffLogField(name = "Opportunity Amount")
    private Integer price;

    @Schema(description = "Entire order discount")
    @DiffLogField(name = "Entire order discount")
    private Integer discountPercent;

    @Schema(description = "Total amount of product", example = "12025")
    @DiffLogField(name = "Total amount of product")
    private BigDecimal productPrice;

    @Schema(description = "Remarks", example = "Whatever")
    @DiffLogField(name = "Remarks")
    private String remark;

    @Schema(description = "End Status", example = "1")
    @InEnum(CrmBizEndStatus.class)
    private Integer endStatus;

    @Schema(description = "Contact Number", example = "110")
    private Long contactId; // Usage scenario，In【Contact details】When adding a business opportunity，If you need to link the two，Need to be transferred contactId Field

    // TODO @puhui999：Transfer items That's it；
    @Schema(description = "Product List")
    private List<CrmBusinessProductItem> productItems;

    @Schema(description = "Product List")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CrmBusinessProductItem {

        @Schema(description = "Product Number", requiredMode = Schema.RequiredMode.REQUIRED, example = "20529")
        @NotNull(message = "Product number cannot be empty")
        private Long id;

        @Schema(description = "Product quantity", requiredMode = Schema.RequiredMode.REQUIRED, example = "8911")
        @NotNull(message = "Product quantity cannot be empty")
        private Integer count;

        @Schema(description = "Product discount")
        private Integer discountPercent;

    }

}
