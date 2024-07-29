package cn.econets.blossom.module.infrastructure.enums.job;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Task log status enumeration
 *
 */
@Getter
@AllArgsConstructor
public enum JobLogStatusEnum {

    RUNNING(0), // Running
    SUCCESS(1), // Success
    FAILURE(2); // Failed

    /**
     * Status
     */
    private final Integer status;

}
