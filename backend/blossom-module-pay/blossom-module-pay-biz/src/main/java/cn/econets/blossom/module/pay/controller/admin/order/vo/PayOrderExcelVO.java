package cn.econets.blossom.module.pay.controller.admin.order.vo;

import cn.econets.blossom.framework.excel.core.annotations.DictFormat;
import cn.econets.blossom.framework.excel.core.convert.DictConvert;
import cn.econets.blossom.framework.excel.core.convert.MoneyConvert;
import cn.econets.blossom.module.pay.enums.DictTypeConstants;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Payment order Excel VO
 *
 *
 */
@Data
public class PayOrderExcelVO {

    @ExcelProperty("Number")
    private Long id;

    @ExcelProperty("Creation time")
    private LocalDateTime createTime;

    @ExcelProperty(value = "Payment amount", converter = MoneyConvert.class)
    private Integer price;

    @ExcelProperty(value = "Refund amount", converter = MoneyConvert.class)
    private Integer refundPrice;

    @ExcelProperty(value = "Transaction amount", converter = MoneyConvert.class)
    private Integer channelFeePrice;

    @ExcelProperty("Merchant Order Number")
    private String merchantOrderId;

    @ExcelProperty(value = "Payment order number")
    private String no;

    @ExcelProperty("Channel Order Number")
    private String channelOrderNo;

    @ExcelProperty(value = "Payment Status", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.ORDER_STATUS)
    private Integer status;

    @ExcelProperty(value = "Channel ID Name", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.CHANNEL_CODE)
    private String channelCode;

    @ExcelProperty("Time when order payment is successful")
    private LocalDateTime successTime;

    @ExcelProperty("Order expiration time")
    private LocalDateTime expireTime;

    @ExcelProperty(value = "Application Name")
    private String appName;

    @ExcelProperty("Product Title")
    private String subject;

    @ExcelProperty("Product Description")
    private String body;

}
