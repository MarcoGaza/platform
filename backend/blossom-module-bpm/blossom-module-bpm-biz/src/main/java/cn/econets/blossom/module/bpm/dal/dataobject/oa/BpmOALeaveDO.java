package cn.econets.blossom.module.bpm.dal.dataobject.oa;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * OA Leave Application DO
 *
 * {@link #day} Number of days of leave，Just keep it simple for now。Generally, leave is divided into morning and afternoon，It can be 1 All day，It can be 0.5 Half day
 *
 */
@TableName("bpm_oa_leave")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmOALeaveDO extends BaseDO {

    /**
     * Leave form primary key
     */
    @TableId
    private Long id;
    /**
     * Applicant's user number
     *
     * Relationship AdminUserDO of id Properties
     */
    private Long userId;
    /**
     * Leave Type
     */
    private String type;
    /**
     * Reason
     */
    private String reason;
    /**
     * Start time
     */
    private LocalDateTime startTime;
    /**
     * End time
     */
    private LocalDateTime endTime;
    /**
     * Number of days of leave
     */
    private Long day;
    /**
     * Result of requesting leave
     *
     * Enumeration {@link BpmProcessInstanceResultEnum}
     * Considering simplicity，So it is reused directly BpmProcessInstanceResultEnum Enumeration，You can also define an enumeration yourself
     */
    private Integer result;

    /**
     * Corresponding process number
     *
     * Relationship ProcessInstance of id Properties
     */
    private String processInstanceId;

}
