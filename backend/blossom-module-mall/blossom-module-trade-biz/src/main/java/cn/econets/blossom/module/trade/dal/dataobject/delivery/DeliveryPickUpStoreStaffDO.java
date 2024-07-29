package cn.econets.blossom.module.trade.dal.dataobject.delivery;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

// TODO ：More details later review One round
// TODO ：May be changed to DeliveryPickUpStoreUserDO
/**
 * Self-pickup store clerk DO
 *
 */
@TableName(value ="trade_delivery_pick_up_store_staff")
@KeySequence("trade_delivery_pick_up_store_staff_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
public class DeliveryPickUpStoreStaffDO extends BaseDO {

    /**
     * Number，Self-increment
     */
    @TableId
    private Long id;

    /**
     * Self-pickup store number
     *
     * Relationship {@link DeliveryPickUpStoreDO#getId()}
     */
    private Long storeId;

    /**
     * Administrator Userid
     *
     * Relationship {AdminUserDO#getId()}
     */
    private Long adminUserId;

    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

}
