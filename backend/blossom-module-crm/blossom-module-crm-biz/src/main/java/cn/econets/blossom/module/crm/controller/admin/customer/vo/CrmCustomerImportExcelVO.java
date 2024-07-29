package cn.econets.blossom.module.crm.controller.admin.customer.vo;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import static cn.econets.blossom.module.crm.enums.DictTypeConstants.*;

/**
 * Customer Excel Import VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // Settings chain = false，Avoid user import problems
public class CrmCustomerImportExcelVO {

    @ExcelProperty("Customer Name")
    private String name;

    // TODO @puhui999：industryId、level、source Field，We can study how to make a drop-down box
    @ExcelProperty(value = "Industry", converter = DictConvert.class)
    @DictFormat(CRM_CUSTOMER_INDUSTRY)
    private Integer industryId;

    @ExcelProperty(value = "Customer Level", converter = DictConvert.class)
    @DictFormat(CRM_CUSTOMER_LEVEL)
    private Integer level;

    @ExcelProperty(value = "Customer Source", converter = DictConvert.class)
    @DictFormat(CRM_CUSTOMER_SOURCE)
    private Integer source;

    @ExcelProperty("Mobile phone")
    private String mobile;

    @ExcelProperty("Phone")
    private String telephone;

    @ExcelProperty("Website")
    private String website;

    @ExcelProperty("QQ")
    private String qq;

    @ExcelProperty("WeChat")
    private String wechat;

    @ExcelProperty("Mailbox")
    private String email;

    @ExcelProperty("Customer Description")
    private String description;

    @ExcelProperty("Remarks")
    private String remark;

    // TODO @puhui999：You need to select a province, city or district，Needs further study，How to make it more reasonable；
    @ExcelProperty("Region Code")
    private Integer areaId;

    @ExcelProperty("Detailed address")
    private String detailAddress;

}
