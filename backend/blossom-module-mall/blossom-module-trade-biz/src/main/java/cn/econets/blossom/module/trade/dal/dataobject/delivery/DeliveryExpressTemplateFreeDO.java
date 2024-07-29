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
 * Express delivery template free shipping configuration DO
 *
 */
@TableName(value ="trade_delivery_express_template_free", autoResultMap = true)
@KeySequence("trade_delivery_express_template_free_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
public class DeliveryExpressTemplateFreeDO extends BaseDO {

    /**
     * Number
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
     * Free shipping amount，Unit：Points
     *
     * Total order amount > Free shipping amount，Free shipping
     */
    private Integer freePrice;

    /**
     * Number of mails in the package
     *
     * Total number of orders > Number of mails included，Free shipping
     */
    private Integer freeCount;

}
