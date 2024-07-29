package cn.econets.blossom.module.crm.controller.admin.business.vo.business;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Business Opportunities Excel VO
 *
 */
@Data
public class CrmBusinessExcelVO {

    @ExcelProperty("Primary key")
    private Long id;

    @ExcelProperty("Opportunity Name")
    private String name;

    @ExcelProperty("Opportunity status type number")
    private Long statusTypeId;

    @ExcelProperty("Opportunity status number")
    private Long statusId;

    @ExcelProperty("Next contact time")
    private LocalDateTime contactNextTime;

    @ExcelProperty("Customer Number")
    private Long customerId;

    @ExcelProperty("Expected transaction date")
    private LocalDateTime dealTime;

    @ExcelProperty("Opportunity Amount")
    private BigDecimal price;

    @ExcelProperty("Entire order discount")
    private BigDecimal discountPercent;

    @ExcelProperty("Total amount of product")
    private BigDecimal productPrice;

    @ExcelProperty("Remarks")
    private String remark;

    @ExcelProperty("User ID of the person in charge")
    private Long ownerUserId;

    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

    @ExcelProperty("Read-only user ID array")
    private Set<Long> roUserIds;

    @ExcelProperty("Array of user IDs with read and write permissions")
    private Set<Long> rwUserIds;

    @ExcelProperty("1Win the order2Enter order3Invalid")
    private Integer endStatus;

    @ExcelProperty("Closing notes")
    private String endRemark;

    @ExcelProperty("Last follow-up time")
    private LocalDateTime contactLastTime;

    @ExcelProperty("Follow-up status")
    private Integer followUpStatus;

}
