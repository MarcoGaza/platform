package cn.econets.blossom.module.promotion.dal.dataobject.seckill;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.util.List;

/**
 * Second sale period DO
 *
 */
@TableName(value = "promotion_seckill_config", autoResultMap = true)
@KeySequence("promotion_seckill_config_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeckillConfigDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Name of flash sale period
     */
    private String name;
    /**
     * Start time
     */
    private String startTime;
    /**
     * End time
     */
    private String endTime;
    /**
     * Second-selling carousel
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> sliderPicUrls;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum Corresponding class}
     */
    private Integer status;

}
