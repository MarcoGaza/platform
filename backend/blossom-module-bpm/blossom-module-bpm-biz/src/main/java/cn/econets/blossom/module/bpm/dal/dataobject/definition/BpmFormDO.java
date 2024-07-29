package cn.econets.blossom.module.bpm.dal.dataobject.definition;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.util.List;

/**
 * Workflow form definition
 * Application form for workflow，Scenario requiring dynamic configuration
 *
 */
@TableName(value = "bpm_form", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BpmFormDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Form Name
     */
    private String name;
    /**
     * Status
     */
    private Integer status;
    /**
     * Form configuration
     */
    private String conf;
    /**
     * Array of form items
     *
     * Currently directly https://github.com/JakHuang/form-generator Generated JSON String，Save directly
     * Definition：https://github.com/JakHuang/form-generator/issues/46
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> fields;
    /**
     * Remarks
     */
    private String remark;

}
