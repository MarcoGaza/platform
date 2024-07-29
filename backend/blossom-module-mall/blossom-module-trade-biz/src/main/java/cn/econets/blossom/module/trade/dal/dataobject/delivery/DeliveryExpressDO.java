package cn.econets.blossom.module.trade.dal.dataobject.delivery;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * Express Delivery Company DO
 *
 */
@TableName(value ="trade_delivery_express")
@KeySequence("trade_delivery_express_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
public class DeliveryExpressDO extends BaseDO {

    /**
     * Number，Self-increment
     */
    @TableId
    private Long id;

    /**
     * Express Delivery Company code
     */
    private String code;

    /**
     * Express delivery company name
     */
    private String name;

    /**
     * Express Delivery Company logo
     */
    private String logo;

    /**
     * Sort
     */
    private Integer sort;

    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

    // TODO c Fields related to settlement，See you later
    //    partnerId	Do you need a monthly settlement account?
    //    partnerKey	Do you need a monthly settlement password?
    //    net	Do you need to pick up the online store?
    //    account	Account
    //    password	Network Name
    //    isShow	Whether to display
}
