package cn.econets.blossom.module.promotion.dal.dataobject.seckill;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.LongListTypeHandler;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Second-sale event DO
 *
 */
@TableName(value = "promotion_seckill_activity", autoResultMap = true)
@KeySequence("promotion_seckill_activity_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SeckillActivityDO extends BaseDO {

    /**
     * Second sale activity number
     */
    @TableId
    private Long id;
    /**
     * Second-sale products
     */
    private Long spuId;
    /**
     * Name of flash sale event
     */
    private String name;
    /**
     * Activity Status
     *
     * Enumeration {@link CommonStatusEnum Corresponding class}
     */
    private Integer status;
    /**
     * Remarks
     */
    private String remark;
    /**
     * Activity start time
     */
    private LocalDateTime startTime;
    /**
     * Event end time
     */
    private LocalDateTime endTime;
    /**
     * Sort
     */
    private Integer sort;
    /**
     * Second sale period id
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> configIds;

    /**
     * Total purchase limit quantity
     */
    private Integer totalLimitCount;
    /**
     * Limited quantity per time
     */
    private Integer singleLimitCount;

    /**
     * Second kill inventory(Remaining stock will be deducted during flash sales)
     */
    private Integer stock;
    /**
     * Total inventory of flash sales
     */
    private Integer totalStock;

}
