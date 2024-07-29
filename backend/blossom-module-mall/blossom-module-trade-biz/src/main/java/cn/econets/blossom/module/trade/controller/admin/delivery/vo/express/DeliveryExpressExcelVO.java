package cn.econets.blossom.module.trade.controller.admin.delivery.vo.express;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.module.system.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Express Delivery Company Excel VO
 */
@Data
public class DeliveryExpressExcelVO {

    @ExcelProperty("Number")
    private Long id;

    @ExcelProperty("Courier company code")
    private String code;

    @ExcelProperty("Express delivery company name")
    private String name;

    @ExcelProperty("Express Delivery Company logo")
    private String logo;

    @ExcelProperty("Sort")
    private Integer sort;

    @ExcelProperty(value = "Status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.COMMON_STATUS)
    private Integer status;

    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

}
