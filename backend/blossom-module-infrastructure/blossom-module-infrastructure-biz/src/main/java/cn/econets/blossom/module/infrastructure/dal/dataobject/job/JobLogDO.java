package cn.econets.blossom.module.infrastructure.dal.dataobject.job;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.quartz.core.handler.JobHandler;
import cn.econets.blossom.module.infrastructure.enums.job.JobLogStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Execution log of scheduled tasks
 *
 */
@TableName("infra_job_log")
@KeySequence("infra_job_log_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobLogDO extends BaseDO {

    /**
     * Log number
     */
    private Long id;
    /**
     * Task number
     *
     * Relationship {@link JobDO#getId()}
     */
    private Long jobId;
    /**
     * Processor name
     *
     * Redundant fields {@link JobDO#getHandlerName()}
     */
    private String handlerName;
    /**
     * Processor parameters
     *
     * Redundant fields {@link JobDO#getHandlerParam()}
     */
    private String handlerParam;
    /**
     * Number of executions
     *
     * Used to distinguish whether to retry execution。If it is a retry execution，Then index Greater than 1
     */
    private Integer executeIndex;

    /**
     * Start execution time
     */
    private LocalDateTime beginTime;
    /**
     * End execution time
     */
    private LocalDateTime endTime;
    /**
     * Execution duration，Unit：Milliseconds
     */
    private Integer duration;
    /**
     * Status
     *
     * Enumeration {@link JobLogStatusEnum}
     */
    private Integer status;
    /**
     * Result data
     *
     * When successful，Use {@link JobHandler#execute(String)} Results
     * When failed，Use {@link JobHandler#execute(String)} Exception stack
     */
    private String result;

}
