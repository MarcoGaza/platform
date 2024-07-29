package cn.econets.blossom.module.bpm.dal.dataobject.task;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Bpm Extension table of process tasks
 * Main solution Flowable Task Japanese HistoricTaskInstance Extended fields are not supported，So create a new extension table
 *
 */
@TableName(value = "bpm_task_ext", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmTaskExtDO extends BaseDO {

    /**
     * Number，Self-increment
     */
    @TableId
    private Long id;

    /**
     * Task approver
     *
     * Redundant Task of assignee Properties
     */
    private Long assigneeUserId;
    /**
     * Task name
     *
     * Redundant Task of name Properties，For screening
     */
    private String name;
    /**
     * Task number
     *
     * Relationship Task of id Properties
     */
    private String taskId;
//    /**
//     * Task ID
//     *
//     * Relationship {@link Task#getTaskDefinitionKey()}
//     */
//    private String definitionKey;
    /**
     * The result of the task
     *
     * Enumeration {@link BpmProcessInstanceResultEnum}
     */
    private Integer result;
    /**
     * Approval Suggestions
     */
    private String reason;
    /**
     * The end time of the task
     *
     * Redundant HistoricTaskInstance of endTime  Properties
     */
    private LocalDateTime endTime;

    /**
     * Process instance number
     *
     * Relationship ProcessInstance of id Properties
     */
    private String processInstanceId;
    /**
     * Process definition number
     *
     * Relationship ProcessDefinition of id Properties
     */
    private String processDefinitionId;

}
