package cn.econets.blossom.module.infrastructure.enums.codegen;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Field filter condition enumeration of code generator
 */
@AllArgsConstructor
@Getter
public enum CodegenColumnListConditionEnum {

    EQ("="),
    NE("!="),
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<="),
    LIKE("LIKE"),
    BETWEEN("BETWEEN");

    /**
     * Conditions
     */
    private final String condition;

}
