package cn.econets.blossom.module.bpm.service.definition.dto;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.module.bpm.enums.definition.BpmModelFormTypeEnum;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

/**
 * Process definition creation Request DTO
 */
@Data
public class BpmProcessDefinitionCreateReqDTO {

    // ========== Model related ==========

    /**
     * Process model number
     */
    @NotEmpty(message = "The process model number cannot be empty")
    private String modelId;
    /**
     * Process ID
     */
    @NotEmpty(message = "Process ID cannot be empty")
    private String key;
    /**
     * Process Name
     */
    @NotEmpty(message = "Process name cannot be empty")
    private String name;
    /**
     * Process description
     */
    private String description;
    /**
     * Process Classification
     * See also bpm_model_category Data dictionary
     */
    @NotEmpty(message = "Process category cannot be empty")
    private String category;
    /**
     * BPMN XML
     */
    @NotEmpty(message = "BPMN XML Cannot be empty")
    private byte[] bpmnBytes;

    // ========== Form related ==========

    /**
     * Form Type
     */
    @NotNull(message = "The form type cannot be empty")
    private Integer formType;
    /**
     * Dynamic form number
     * In the form type {@link BpmModelFormTypeEnum#NORMAL} Time
     */
    private Long formId;
    /**
     * Form configuration
     * In the form type {@link BpmModelFormTypeEnum#NORMAL} Time
     */
    private String formConf;
    /**
     * Array of form items
     * In the form type {@link BpmModelFormTypeEnum#NORMAL} Time
     */
    private List<String> formFields;
    /**
     * Customize the submission path of the form，Use Vue The routing address
     * In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time
     */
    private String formCustomCreatePath;
    /**
     * Customize the viewing path of the form，Use Vue The routing address
     * In the form type {@link BpmModelFormTypeEnum#CUSTOM} Time
     */
    private String formCustomViewPath;

    @AssertTrue(message = "Process form information is incomplete")
    public boolean isNormalFormTypeValid() {
        // If it is not a business form，Pass directly
        if (!Objects.equals(formType, BpmModelFormTypeEnum.NORMAL.getType())) {
            return true;
        }
        return formId != null && StrUtil.isNotEmpty(formConf) && CollUtil.isNotEmpty(formFields);
    }

    @AssertTrue(message = "Business form information is incomplete")
    public boolean isNormalCustomTypeValid() {
        // If it is not a business form，Pass directly
        if (!Objects.equals(formType, BpmModelFormTypeEnum.CUSTOM.getType())) {
            return true;
        }
        return StrUtil.isNotEmpty(formCustomCreatePath) && StrUtil.isNotEmpty(formCustomViewPath);
    }

}
