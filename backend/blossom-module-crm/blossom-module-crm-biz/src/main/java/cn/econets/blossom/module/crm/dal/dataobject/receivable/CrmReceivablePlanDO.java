package cn.econets.blossom.module.crm.dal.dataobject.receivable;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Payment Refund Plan DO
 *
 */
@TableName("crm_receivable_plan")
@KeySequence("crm_receivable_plan_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmReceivablePlanDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * Issue number
     */
    private Integer period;
    /**
     * PaybackID
     *
     * TODO @liuhongfeng：Fewer associated entities；
     */
    private Long receivableId;
    /**
     * Completion status
     */
    private Boolean finishStatus;
    /**
     * Planned payment amount，Unit：Points
     */
    private Integer price;
    /**
     * Planned payment date
     */
    private LocalDateTime returnTime;
    /**
     * Remind a few days in advance
     */
    private Integer remindDays;
    /**
     * Reminder date
     */
    private LocalDateTime remindTime;
    /**
     * Customer ID
     *
     * TODO @liuhongfeng：Fewer associated entities；
     */
    private Long customerId;
    /**
     * Contract ID
     *
     * TODO @liuhongfeng：Fewer associated entities；
     */
    private Long contractId;
    /**
     * Person in charge ID
     *
     * TODO @liuhongfeng：Fewer associated entities；
     */
    private Long ownerUserId;
    /**
     * Display order
     */
    private Integer sort;
    /**
     * Remarks
     */
    private String remark;

}
