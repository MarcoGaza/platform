package cn.econets.blossom.module.promotion.dal.dataobject.bargain;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.promotion.enums.bargain.BargainRecordStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Bargaining Record DO TODO
 *
 */
@TableName("promotion_bargain_record")
@KeySequence("promotion_bargain_record_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BargainRecordDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * User Number
     */
    private Long userId;

    /**
     * Bargaining activity number
     *
     * Relationship {@link BargainActivityDO#getId()} Field
     */
    private Long activityId;
    /**
     * Products SPU Number
     */
    private Long spuId;
    /**
     * Products SKU Number
     */
    private Long skuId;

    /**
     * Bargaining starting price，Unit：Points
     */
    private Integer bargainFirstPrice;
    /**
     * Current bargaining price，Unit：Points
     */
    private Integer bargainPrice;

    /**
     * Bargaining status
     *
     * The conditions for successful bargaining are：（2 Select 1）
     *  1. Bargain to the lowest price {@link BargainActivityDO#getBargainMinPrice()} Reserve Price
     *  2. The number of people helping has arrived {@link BargainActivityDO#getHelpMaxCount()} People
     *
     * Enumeration {@link BargainRecordStatusEnum}
     */
    private Integer status;
    /**
     * End time
     */
    private LocalDateTime endTime;

    /**
     * Order Number
     */
    private Long orderId;

}
