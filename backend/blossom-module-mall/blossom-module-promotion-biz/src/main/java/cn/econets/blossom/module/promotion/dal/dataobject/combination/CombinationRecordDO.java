package cn.econets.blossom.module.promotion.dal.dataobject.combination;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.promotion.enums.combination.CombinationRecordStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

// TODO Change the order of fields，Japanese do Align order
/**
 * Group buying record DO
 *
 * 1. When a user participates in a group purchase，Will create a record
 * 2. Group leader's group record，Group record with participants，Passed {@link #headId} Relationship
 *
 */
@TableName("promotion_combination_record")
@KeySequence("promotion_combination_record_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CombinationRecordDO extends BaseDO {

    /**
     * Team Leader Number - Team Leader
     */
    public static final Long HEAD_ID_GROUP = 0L;

    /**
     * Number，Primary key auto-increment
     */
    @TableId
    private Long id;

    /**
     * Group buying activity number
     *
     * Relationship {@link CombinationActivityDO#getId()}
     */
    private Long activityId;
    /**
     * Unit price of group purchase product
     *
     * Redundant {@link CombinationProductDO#getCombinationPrice()}
     */
    private Integer combinationPrice;
    /**
     * SPU Number
     */
    private Long spuId;
    /**
     * Product Name
     */
    private String spuName;
    /**
     * Product images
     */
    private String picUrl;
    /**
     * SKU Number
     */
    private Long skuId;
    /**
     * Quantity of goods purchased
     */
    private Integer count;

    /**
     * User Number
     */
    private Long userId;

    /**
     * User Nickname
     */
    private String nickname;
    /**
     * User avatar
     */
    private String avatar;

    /**
     * Team Leader Number
     *
     * Relationship {@link CombinationRecordDO#getId()}
     *
     * If it is the leader，Then its value is {@link #HEAD_ID_GROUP}
     */
    private Long headId;
    /**
     * Group status
     *
     * Relationship {@link CombinationRecordStatusEnum}
     */
    private Integer status;
    /**
     * Order Number
     */
    private Long orderId;
    /**
     * Number of people needed to start a group
     *
     * Relationship {@link CombinationActivityDO#getUserSize()}
     */
    private Integer userSize;
    /**
     * Number of people who have joined the group
     */
    private Integer userCount;
    /**
     * Whether to form a virtual group
     *
     * Default is false。
     * The group purchase has expired and has not been successful，If {@link CombinationActivityDO#getVirtualGroup()} for true，Then execute the logic of virtual grouping，The field will be updated to true
     */
    private Boolean virtualGroup;

    /**
     * Expiration time
     *
     * Based on {@link CombinationRecordDO#getStartTime()} + {@link CombinationActivityDO#getLimitDuration()} Calculation
     */
    private LocalDateTime expireTime;
    /**
     * Start time (The time when the order starts after payment)
     */
    private LocalDateTime startTime;
    /**
     * End time（Group formation time/Failure time）
     */
    private LocalDateTime endTime;

}
