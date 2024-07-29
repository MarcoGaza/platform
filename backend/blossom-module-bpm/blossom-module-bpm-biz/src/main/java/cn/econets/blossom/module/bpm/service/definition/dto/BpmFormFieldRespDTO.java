package cn.econets.blossom.module.bpm.service.definition.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Bpm Form Field Form item Response DTO
 * Field definition，Visible https://github.com/JakHuang/form-generator/issues/46 Document
 *
 * 
 */
@Data
public class BpmFormFieldRespDTO {

    /**
     * Form title
     */
    private String label;
    /**
     * The attribute name of the form field，Customizable
     */
    @JsonProperty(value = "vModel")
    private String vModel;

}
