package cn.econets.blossom.module.bpm.dal.dataobject.definition;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.JsonLongSetTypeHandler;
import cn.econets.blossom.module.bpm.enums.definition.BpmTaskAssignRuleTypeEnum;
import cn.econets.blossom.module.bpm.enums.definition.BpmTaskRuleScriptEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Set;

/**
 * Bpm Task allocation rules table，Used to customize the person in charge of each task、Candidate allocation rules。
 * That is to say，Abandoned BPMN Original UserTask Set assignee、candidateUsers Equal configuration，Instead, the corresponding person in charge is calculated by using this rule。
 *
 * 1. By default，{@link #processDefinitionId} for {@link #PROCESS_DEFINITION_ID_NULL} Value，Indicates that your rule is associated with the process model
 * 2. After the process model is deployed，Will record all his rules，Copy a newly deployed process definition，By setting {@link #processDefinitionId} Associate the number of the new process definition
 *
 */
@TableName(value = "bpm_task_assign_rule", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmTaskAssignRuleDO extends BaseDO {

    /**
     * {@link #processDefinitionId} Empty string，Used to identify the process model，Not part of the process definition
     */
    public static final String PROCESS_DEFINITION_ID_NULL = "";

    /**
     * Number
     */
    @TableId
    private Long id;

    /**
     * Process model number
     *
     * Relationship Model of id Properties
     */
    private String modelId;
    /**
     * Process definition number
     *
     * Relationship ProcessDefinition of id Properties
     */
    private String processDefinitionId;
    /**
     * Definition of process tasks Key
     *
     * Relationship Task of taskDefinitionKey Properties
     */
    private String taskDefinitionKey;

    /**
     * Rule Type
     *
     * Enumeration {@link BpmTaskAssignRuleTypeEnum}
     */
    @TableField("`type`")
    private Integer type;
    /**
     * Rule value array，General association specifies the table number
     * According to type Different，The corresponding values ​​are different：
     *
     * 1. {@link BpmTaskAssignRuleTypeEnum#ROLE} Time：Character number
     * 2. {@link BpmTaskAssignRuleTypeEnum#DEPT_MEMBER} Time：Department Number
     * 3. {@link BpmTaskAssignRuleTypeEnum#DEPT_LEADER} Time：Department Number
     * 4. {@link BpmTaskAssignRuleTypeEnum#USER} Time：User ID
     * 5. {@link BpmTaskAssignRuleTypeEnum#USER_GROUP} Time：User group number
     * 6. {@link BpmTaskAssignRuleTypeEnum#SCRIPT} Time：Script number，Currently passed {@link BpmTaskRuleScriptEnum#getId()} Logo
     */
    @TableField(typeHandler = JsonLongSetTypeHandler.class)
    private Set<Long> options;

}
