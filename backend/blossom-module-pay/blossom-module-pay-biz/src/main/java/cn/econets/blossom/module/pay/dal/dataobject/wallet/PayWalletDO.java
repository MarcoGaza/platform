package cn.econets.blossom.module.pay.dal.dataobject.wallet;

import cn.econets.blossom.framework.common.enums.UserTypeEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Member Wallet DO
 *
 *
 */
@TableName(value ="pay_wallet")
@KeySequence("pay_wallet_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
public class PayWalletDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;

    /**
     * User id
     *
     * Relationship MemberUserDO of id Number
     * Relationship AdminUserDO of id Number
     */
    private Long userId;
    /**
     * User Type, Reserved Multiple merchant transfers may require this
     *
     * Relationship {@link UserTypeEnum}
     */
    private Integer userType;

    /**
     * Balance，Unit points
     */
    private Integer balance;

    /**
     * Frozen amount，Unit points
     */
    private Integer freezePrice;

    /**
     * Cumulative expenditure，Unit points
     */
    private Integer totalExpense;
    /**
     * Accumulated recharge，Unit points
     */
    private Integer totalRecharge;

}
