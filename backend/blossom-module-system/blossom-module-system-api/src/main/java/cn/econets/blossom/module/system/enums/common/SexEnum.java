package cn.econets.blossom.module.system.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Gender enumeration value
 *
 */
@Getter
@AllArgsConstructor
public enum SexEnum {

    /** Male */
    MALE(1),
    /** Female */
    FEMALE(2),
    /* Unknown */
    UNKNOWN(3);

    /**
     * Gender
     */
    private final Integer sex;

}
