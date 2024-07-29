package cn.econets.blossom.module.trade.dal.dataobject.brokerage;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawStatusEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageWithdrawTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Commission withdrawal DO
 *
 */
@TableName("trade_brokerage_withdraw")
@KeySequence("trade_brokerage_withdraw_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrokerageWithdrawDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * User Number
     *
     * Relationship MemberUserDO of id Field
     */
    private Long userId;

    /**
     * Withdrawal amount，Unit：Points
     */
    private Integer price;
    /**
     * Withdrawal fee，Unit：Points
     */
    private Integer feePrice;
    /**
     * Current total commission，Unit：Points
     */
    private Integer totalPrice;
    /**
     * Withdrawal type
     * <p>
     * Enumeration {@link BrokerageWithdrawTypeEnum}
     */
    private Integer type;

    /**
     * Real name
     */
    private String name;
    /**
     * Account
     */
    private String accountNo;
    /**
     * Bank Name
     */
    private String bankName;
    /**
     * Account opening address
     */
    private String bankAddress;
    /**
     * Payment code
     */
    private String accountQrCodeUrl;
    /**
     * Status
     * <p>
     * Enumeration {@link BrokerageWithdrawStatusEnum}
     */
    private Integer status;
    /**
     * Reason for rejection of review
     */
    private String auditReason;
    /**
     * Audit time
     */
    private LocalDateTime auditTime;
    /**
     * Remarks
     */
    private String remark;

}
