package cn.econets.blossom.module.pay.dal.dataobject.wallet;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.pay.enums.wallet.PayWalletBizTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Member wallet flow DO
 *
 *
 */
@TableName(value ="pay_wallet_transaction")
@KeySequence("pay_wallet_transaction_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
public class PayWalletTransactionDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;

    /**
     * Serial number
     */
    private String no;

    /**
     * Wallet Number
     *
     * Relationship {@link PayWalletDO#getId()}
     */
    private Long walletId;

    /**
     * Related business categories
     *
     * Enumeration {@link PayWalletBizTypeEnum#getType()}
     */
    private Integer bizType;

    /**
     * Related business number
     */
    private String bizId;

    /**
     * Flow Description
     */
    private String title;

    /**
     * Transaction amount，Unit points
     *
     * A positive value indicates an increase in balance，Negative value means the balance decreases
     */
    private Integer price;

    /**
     * Balance after transaction，Unit points
     */
    private Integer balance;
}
