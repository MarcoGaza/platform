package cn.econets.blossom.module.trade.dal.dataobject.delivery;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.IntegerListTypeHandler;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;

/**
 * Express freight template billing configuration DO
 *
 */
@TableName(value ="trade_delivery_express_template_charge", autoResultMap = true)
@KeySequence("trade_delivery_express_template_charge_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
public class DeliveryExpressTemplateChargeDO extends BaseDO {

    /**
     * Number，Self-increment
     */
    @TableId
    private Long id;

    /**
     * Delivery template number
     *
     * Relationship {@link DeliveryExpressTemplateDO#getId()}
     */
    private Long templateId;

    /**
     * Delivery area number list
     */
    @TableField(typeHandler = IntegerListTypeHandler.class)
    private List<Integer> areaIds;

    /**
     * Delivery billing method
     *
     * Redundant {@link DeliveryExpressTemplateDO#getChargeMode()}
     */
    private Integer chargeMode;

    /**
     * First Quantity(Number of items,Weight，or volume)
     */
    private Double startCount;
    /**
     * Starting price，Unit：Points
     */
    private Integer startPrice;

    /**
     * Number of follow-up items(Item, Weight，or volume)
     */
    private Double extraCount;
    /**
     * Additional price，Unit：Points
     */
    private Integer extraPrice;

}
