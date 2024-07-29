package cn.econets.blossom.module.bpm.dal.dataobject.definition;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.bpm.enums.definition.BpmModelFormTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.util.List;

/**
 * Bpm Extension table of process definition
 * Main solution Activiti {@link ProcessDefinition} Extended fields are not supported，So create a new extension table
 *
 */
@TableName(value = "bpm_process_definition_ext", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmProcessDefinitionExtDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Process definition number
     *
     * Relationship ProcessDefinition of id Properties
     */
    private String processDefinitionId;
    /**
     * Process model number
     *
     * Relationship Model of id Properties
     */
    private String modelId;
    /**
     * Description
     */
    private String description;

    /**
     * Form type
     *
     * Relationship {@link BpmModelFormTypeEnum}
     */
    private Integer formType;
    /**
     * Dynamic form number
     * In the form type {@link BpmModelFormTypeEnum#NORMAL} Time
     *
     * Relationship {@link BpmFormDO#getId()}
     */
    private Long formId;
    /**
     * Form configuration
     * In the form type {@link BpmModelFormTypeEnum#NORMAL} Time
     *
     * Redundant {@link BpmFormDO#getConf()}
     */
    private String formConf;
    /**
     * Array of form items
     * In the form type {@link BpmModelFormTypeEnum#NORMAL} Time
     *
     * Redundant {@link BpmFormDO#getFields()} ()}
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> formFields;
    /**
     * Custom form submission path，Use Vue The routing address
     * In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time
     */
    private String formCustomCreatePath;
    /**
     * Customize the viewing path of the form，Use Vue The routing address
     * In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time
     */
    private String formCustomViewPath;


}
