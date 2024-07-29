package cn.econets.blossom.module.system.enums.permission;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Menu type enumeration class
 *
 */
@Getter
@AllArgsConstructor
public enum MenuTypeEnum {

    DIR(1), // Directory
    MENU(2), // Menu
    BUTTON(3) // Button
    ;

    /**
     * Type
     */
    private final Integer type;

}
