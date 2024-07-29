package cn.econets.blossom.module.pay.dal.dataobject.wallet;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Member Wallet Recharge Package DO
 *
 * Through recharge package，You can give a certain amount of money；
 *
 *
 */
@TableName(value ="pay_wallet_recharge_package")
@KeySequence("pay_wallet_recharge_package_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
public class PayWalletRechargePackageDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Package Name
     */
    private String name;

    /**
     * Payment amount
     */
    private Integer payPrice;
    /**
     * Gift amount
     */
    private Integer bonusPrice;

    /**
     * Status
     *
     * Enumeration {@link cn.econets.blossom.framework.common.enums.CommonStatusEnum}
     */
    private Integer status;

}
