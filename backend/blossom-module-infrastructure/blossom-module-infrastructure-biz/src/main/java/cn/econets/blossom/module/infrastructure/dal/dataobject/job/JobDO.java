package cn.econets.blossom.module.infrastructure.dal.dataobject.job;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.infrastructure.enums.job.JobStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Scheduled tasks DO
 *
 */
@TableName("infra_job")
@KeySequence("infra_job_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDO extends BaseDO {

    /**
     * Task number
     */
    @TableId
    private Long id;
    /**
     * Task Name
     */
    private String name;
    /**
     * Task Status
     *
     * Enumeration {@link JobStatusEnum}
     */
    private Integer status;
    /**
     * Processor name
     */
    private String handlerName;
    /**
     * Processor parameters
     */
    private String handlerParam;
    /**
     * CRON Expression
     */
    private String cronExpression;

    // ========== Retry related fields ==========
    /**
     * Number of retries
     * If you don't retry，Then set to 0
     */
    private Integer retryCount;
    /**
     * Retry interval，Unit：Milliseconds
     * If there is no interval，Then set to 0
     */
    private Integer retryInterval;

    // ========== Monitoring related fields ==========
    /**
     * Monitoring timeout，Unit：Milliseconds
     * When empty，Indicates no monitoring
     *
     * Attention，The purpose of the timeout here，Not cancelling the task，The execution time of the alarm task is too long
     */
    private Integer monitorTimeout;

}
