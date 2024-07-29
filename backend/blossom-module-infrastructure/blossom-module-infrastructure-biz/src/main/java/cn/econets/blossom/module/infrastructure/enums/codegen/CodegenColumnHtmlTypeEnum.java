package cn.econets.blossom.module.infrastructure.enums.codegen;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Code generator fields HTML Show enumeration
 */
@AllArgsConstructor
@Getter
public enum CodegenColumnHtmlTypeEnum {

    INPUT("input"), // Text box
    TEXTAREA("textarea"), // Text field
    SELECT("select"), // Drop-down box
    RADIO("radio"), // Radio Box
    CHECKBOX("checkbox"), // Checkbox
    DATETIME("datetime"), // Date Control
    IMAGE_UPLOAD("imageUpload"), // Upload picture
    FILE_UPLOAD("fileUpload"), // Upload file
    EDITOR("editor"), // Rich text control
    ;

    /**
     * Conditions
     */
    private final String type;

}
