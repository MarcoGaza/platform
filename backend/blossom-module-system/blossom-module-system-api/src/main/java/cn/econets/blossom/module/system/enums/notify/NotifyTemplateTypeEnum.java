package cn.econets.blossom.module.system.enums.notify;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Notification template type enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum NotifyTemplateTypeEnum {

    /**
     * System message
     */
    SYSTEM_MESSAGE(2),
    /**
     * Notification message
     */
    NOTIFICATION_MESSAGE(1);

    private final Integer type;

}
