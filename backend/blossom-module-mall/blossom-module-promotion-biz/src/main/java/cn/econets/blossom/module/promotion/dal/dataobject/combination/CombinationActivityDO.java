package cn.econets.blossom.module.promotion.dal.dataobject.combination;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Group buying activity DO
 *
 */
@TableName("promotion_combination_activity")
@KeySequence("promotion_combination_activity_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 Database primary key auto-increment。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CombinationActivityDO extends BaseDO {

    /**
     * Activity number
     */
    @TableId
    private Long id;
    /**
     * Group name
     */
    private String name;
    /**
     * Products SPU Number
     *
     * Relationship ProductSpuDO of id
     */
    private Long spuId;
    /**
     * Total purchase limit quantity
     */
    private Integer totalLimitCount;
    /**
     * Single purchase limit quantity
     */
    private Integer singleLimitCount;
    /**
     * Start time
     */
    private LocalDateTime startTime;
    /**
     * End time
     */
    private LocalDateTime endTime;
    /**
     * Group of several people
     */
    private Integer userSize;
    /**
     * Virtual group formation
     */
    private Boolean virtualGroup;
    /**
     * Activity Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Limit duration（Hours）
     */
    private Integer limitDuration;

}
