package cn.econets.blossom.module.promotion.dal.dataobject.bargain;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Bargaining activity DO
 *
 */
@TableName("promotion_bargain_activity")
@KeySequence("promotion_bargain_activity_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BargainActivityDO extends BaseDO {

    /**
     * Bargaining activity number
     */
    @TableId
    private Long id;

    /**
     * Bargaining activity name
     */
    private String name;

    /**
     * Activity start time
     */
    private LocalDateTime startTime;
    /**
     * Event end time
     */
    private LocalDateTime endTime;

    /**
     * Activity Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

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
     * Bargaining price，Unit：Points
     */
    private Integer bargainMinPrice;

    /**
     * Bargaining inventory(Deduction for remaining inventory when bargaining)
     */
    private Integer stock;
    /**
     * Bargaining total inventory
     */
    private Integer totalStock;

    /**
     * Number of people bargaining
     *
     * How many people are needed，Bargaining is the key to success，That is {@link BargainRecordDO#getStatus()} Update to {@link BargainRecordDO#getStatus()} Success status
     */
    private Integer helpMaxCount;
    /**
     * Number of times of helping to cut
     *
     * Single activity，The number of times a user can help hack。
     * For example：The number of times of helping to chop is 1 Time，A Japanese B Also send the activity link to C，C Can only bargain for one of them。
     */
    private Integer bargainCount;

    /**
     * Total purchase limit quantity
     */
    private Integer totalLimitCount;
    /**
     * The minimum amount a user can bargain for each time，Unit：Points
     */
    private Integer randomMinPrice;
    /**
     * The maximum amount a user can bargain for each time，Unit：Points
     */
    private Integer randomMaxPrice;

}
