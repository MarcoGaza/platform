package cn.econets.blossom.module.trade.dal.dataobject.delivery;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.trade.enums.delivery.DeliveryExpressChargeModeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Express delivery fee template DO
 *
 */
@TableName("trade_delivery_express_template")
@KeySequence("trade_delivery_express_template_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
public class DeliveryExpressTemplateDO extends BaseDO {

    /**
     * Number，Self-increment
     */
    @TableId
    private Long id;

    /**
     * Template name
     */
    private String name;

    /**
     * Delivery billing method
     *
     * Enumeration {@link DeliveryExpressChargeModeEnum}
     */
    private Integer chargeMode;

    /**
     * Sort
     */
    private Integer sort;

}
