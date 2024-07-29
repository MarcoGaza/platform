package cn.econets.blossom.module.pay.dal.dataobject.notify;

import cn.econets.blossom.module.pay.enums.notify.PayNotifyStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Merchant Payment、Notification of refund, etc. Log
 * Every time you are notified，Will be in this table，Record once Log，Convenient for troubleshooting
 *
 *
 */
@TableName("pay_notify_log")
@KeySequence("pay_notify_log_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayNotifyLogDO extends BaseDO {

    /**
     * Log number，Self-increment
     */
    private Long id;
    /**
     * Notification task number
     *
     * Relationship {@link PayNotifyTaskDO#getId()}
     */
    private Long taskId;
    /**
     * Number of times I was notified
     *
     * Corresponding to {@link PayNotifyTaskDO#getNotifyTimes()}
     */
    private Integer notifyTimes;
    /**
     * HTTP Response result
     */
    private String response;
    /**
     * Payment notification status
     *
     * Foreign key {@link PayNotifyStatusEnum}
     */
    private Integer status;

}
