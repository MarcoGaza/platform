package cn.econets.blossom.module.pay.controller.admin.refund.vo;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.framework.excel.core.convert.MoneyConvert;
import cn.econets.blossom.module.pay.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Refund order Excel VO
 *
 *
 */
@Data
public class PayRefundExcelVO {

    @ExcelProperty("Payment refund number")
    private Long id;

    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

    @ExcelProperty(value = "Payment amount", converter = MoneyConvert.class)
    private Integer payPrice;

    @ExcelProperty(value = "Refund amount", converter = MoneyConvert.class)
    private Integer refundPrice;

    @ExcelProperty("Merchant refund order number")
    private String merchantRefundId;
    @ExcelProperty("Refund order number")
    private String no;
    @ExcelProperty("Channel refund order number")
    private String channelRefundNo;

    @ExcelProperty("Merchant payment order number")
    private String merchantOrderId;
    @ExcelProperty("Channel payment order number")
    private String channelOrderNo;

    @ExcelProperty(value = "Refund Status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.REFUND_STATUS)
    private Integer status;

    @ExcelProperty(value = "Refund channels", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.CHANNEL_CODE)
    private String channelCode;

    @ExcelProperty("Success time")
    private LocalDateTime successTime;

    @ExcelProperty(value = "Payment Application")
    private String appName;

    @ExcelProperty("Refund reason")
    private String reason;

}
