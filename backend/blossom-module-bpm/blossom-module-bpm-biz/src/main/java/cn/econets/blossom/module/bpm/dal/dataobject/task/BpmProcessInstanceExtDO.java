package cn.econets.blossom.module.bpm.dal.dataobject.task;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.econets.blossom.module.bpm.enums.task.BpmProcessInstanceStatusEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * Bpm Extension table of process instance
 * Main solution Activiti ProcessInstance Japanese HistoricProcessInstance Extended fields are not supported，So create a new extension table
 *
 */
@TableName(value = "bpm_process_instance_ext", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmProcessInstanceExtDO extends BaseDO {

    /**
     * Number，Self-increment
     */
    @TableId
    private Long id;
    /**
     * The user ID that initiated the process
     *
     * Redundant HistoricProcessInstance of startUserId Properties
     */
    private Long startUserId;
    /**
     * Name of the process instance
     *
     * Redundant ProcessInstance of name Properties，For filtering
     */
    private String name;
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
    /**
     * Process Classification
     *
     * Redundant ProcessDefinition of category Properties
     * Data dictionary bpm_model_category
     */
    private String category;
    /**
     * The status of the process instance
     *
     * Enumeration {@link BpmProcessInstanceStatusEnum}
     */
    private Integer status;
    /**
     * Results of process instance
     *
     * Enumeration {@link BpmProcessInstanceResultEnum}
     */
    private Integer result;
    /**
     * End time
     *
     * Redundant HistoricProcessInstance of endTime Properties
     */
    private LocalDateTime endTime;

    /**
     * Submitted form value
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Map<String, Object> formVariables;

    // TODO assignees plural
    /**
     * Pre-set approver
     */
    @TableField(typeHandler = JacksonTypeHandler.class, exist = false) // TODO Temporary exist = false，Avoid db Error；
    private Map<String, List<Long>> assignee;

}
