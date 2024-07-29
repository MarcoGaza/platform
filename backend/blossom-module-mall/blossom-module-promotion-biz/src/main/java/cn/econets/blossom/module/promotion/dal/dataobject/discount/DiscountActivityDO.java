package cn.econets.blossom.module.promotion.dal.dataobject.discount;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Limited time discount event DO
 *
 * Under one activity，Yes {@link DiscountProductDO} Products；
 * One product，Within the specified time period，Can only belong to one activity；
 *
 */
@TableName(value = "promotion_discount_activity", autoResultMap = true)
@KeySequence("promotion_discount_activity_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class DiscountActivityDO extends BaseDO {

    /**
     * Activity number，Primary key auto-increment
     */
    @TableId
    private Long id;
    /**
     * Activity Title
     */
    private String name;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     *
     * After the activity is closed，Not allowed to open again。
     */
    private Integer status;
    /**
     * Start time
     */
    private LocalDateTime startTime;
    /**
     * End time
     */
    private LocalDateTime endTime;
    /**
     * Remarks
     */
    private String remark;

}
