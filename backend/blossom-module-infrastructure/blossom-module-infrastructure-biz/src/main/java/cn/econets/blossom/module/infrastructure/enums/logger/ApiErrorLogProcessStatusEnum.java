package cn.econets.blossom.module.infrastructure.enums.logger;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API Processing status of abnormal data
 *
 */
@AllArgsConstructor
@Getter
public enum ApiErrorLogProcessStatusEnum {

    INIT(0, "Unprocessed"),
    DONE(1, "Processed"),
    IGNORE(2, "Ignored");

    /**
     * Status
     */
    private final Integer status;
    /**
     * Resource type name
     */
    private final String name;

}
