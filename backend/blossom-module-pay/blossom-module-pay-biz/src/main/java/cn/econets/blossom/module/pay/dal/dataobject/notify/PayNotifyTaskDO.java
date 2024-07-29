package cn.econets.blossom.module.pay.dal.dataobject.notify;

import cn.econets.blossom.framework.tenant.core.db.TenantBaseDO;
import cn.econets.blossom.module.pay.dal.dataobject.app.PayAppDO;
import cn.econets.blossom.module.pay.dal.dataobject.order.PayOrderDO;
import cn.econets.blossom.module.pay.dal.dataobject.refund.PayRefundDO;
import cn.econets.blossom.module.pay.enums.notify.PayNotifyStatusEnum;
import cn.econets.blossom.module.pay.enums.notify.PayNotifyTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * Payment Notice
 * Receive payment from payment channel in payment system、After the refund result，Need to continuously notify the business system，Until success。
 *
 *
 */
@TableName("pay_notify_task")
@KeySequence("pay_notify_task_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class PayNotifyTaskDO extends TenantBaseDO {

    /**
     * Notification frequency，Unit is seconds。
     *
     * Including the first notification，Actually a total of 1 + 8 = 9 times。
     */
    public static final Integer[] NOTIFY_FREQUENCY = new Integer[]{
            15, 15, 30, 180,
            1800, 1800, 1800, 3600
    };

    /**
     * Number，Self-increment
     */
    @TableId
    private Long id;
    /**
     * Application Number
     *
     * Relationship {@link PayAppDO#getId()}
     */
    private Long appId;
    /**
     * Notification type
     *
     * Foreign key {@link PayNotifyTypeEnum}
     */
    private Integer type;
    /**
     * Data number，Depending on the different type Associate：
     *
     * 1. {@link PayNotifyTypeEnum#ORDER} Time，Relationship {@link PayOrderDO#getId()}
     * 2. {@link PayNotifyTypeEnum#REFUND} Time，Relationship {@link PayRefundDO#getId()}
     */
    private Long dataId;
    /**
     * Merchant order number
     */
    private String merchantOrderId;
    /**
     * Merchant transfer order number
     */
    private String merchantTransferId;
    /**
     * Notification status
     *
     * Foreign key {@link PayNotifyStatusEnum}
     */
    private Integer status;
    /**
     * Next notification time
     */
    private LocalDateTime nextNotifyTime;
    /**
     * Last execution time
     */
    private LocalDateTime lastExecuteTime;
    /**
     * Current notification count
     */
    private Integer notifyTimes;
    /**
     * Maximum number of notifications
     */
    private Integer maxNotifyTimes;
    /**
     * Notification address
     */
    private String notifyUrl;

}
