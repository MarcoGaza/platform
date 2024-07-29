package cn.econets.blossom.module.pay.dal.dataobject.demo;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.pay.core.enums.transfer.PayTransferTypeEnum;
import cn.econets.blossom.module.pay.dal.dataobject.app.PayAppDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

// TODO Details required review
/**
 * Sample transfer order
 *
 * Demonstration of the transfer business of the business system
 */
@TableName(value ="pay_demo_transfer", autoResultMap = true)
@KeySequence("pay_demo_transfer_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Wait for database，Optional。
@Data
public class PayDemoTransferDO extends BaseDO {

    /**
     * Order Number
     */
    @TableId
    private Long id;

    /**
     * Application Number
     *
     * Relationship {@link PayAppDO#getId()}
     */
    private Long appId;

    /**
     * Transfer Type
     * <p>
     * Enumeration {@link PayTransferTypeEnum}
     */
    private Integer type;

    /**
     * Transfer amount，Unit：Points
     */
    private Integer price;

    /**
     * Payee's name
     */
    private String userName;

    /**
     * Alipay login number
     */
    private String alipayLogonId;

    /**
     * WeChat openId
     */
    private String openid;

    /**
     * Transfer Status
     */
    private Integer transferStatus;

    /**
     * Transfer order number
     */
    private Long payTransferId;

    /**
     * Transfer payment success channel
     */
    private String payChannelCode;

    /**
     * Transfer payment time
     */
    private LocalDateTime transferTime;

}
