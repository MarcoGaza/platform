package cn.econets.blossom.module.bpm.enums.definition;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * BPM Enumeration of model form types
 *
 */
@Getter
@AllArgsConstructor
public enum BpmModelFormTypeEnum {

    NORMAL(10, "Process Form"), // Corresponding BpmFormDO
    CUSTOM(20, "Business Form") // Business-defined forms，Store data yourself
    ;

    private final Integer type;
    private final String desc;
}
