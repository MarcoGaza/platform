package cn.econets.blossom.module.bpm.service.definition.dto;

import cn.econets.blossom.module.bpm.enums.definition.BpmModelFormTypeEnum;
import lombok.Data;

/**
 * BPM Process MetaInfo Response DTO
 * Mainly used for { Model#setMetaInfo(String)} Storage
 *
 * 
 */
@Data
public class BpmModelMetaInfoRespDTO {

    /**
     * Process description
     */
    private String description;
    /**
     * Form type
     */
    private Integer formType;
    /**
     * Form number
     * In the form type {@link BpmModelFormTypeEnum#NORMAL} Time
     */
    private Long formId;
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

}
