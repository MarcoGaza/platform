package cn.econets.blossom.module.infrastructure.enums.codegen;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Front-end type enumeration for code generation
 *
 *
 */
@AllArgsConstructor
@Getter
public enum CodegenFrontTypeEnum {

    VUE2(10), // Vue2 Element UI Standard template
    VUE3(20), // Vue3 Element Plus Standard template
    VUE3_SCHEMA(21), // Vue3 Element Plus Schema Template
    VUE3_VBEN(30), // Vue3 VBEN Template
    ;

    /**
     * Type
     */
    private final Integer type;

}
