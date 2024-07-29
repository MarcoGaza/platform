package cn.econets.blossom.framework.operatelog.core.enums;

import cn.econets.blossom.framework.operatelog.core.annotations.OperateLog;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Operation type of operation log
 *
 */
@Getter
@AllArgsConstructor
public enum OperateTypeEnum {

    /**
     * Query
     *
     * In most cases，Query actions will not be recorded，Because it is too large, it seems meaningless。
     * When needed，Through statement {@link OperateLog} Record with annotations
     */
    GET(1),
    /**
     * Newly added
     */
    CREATE(2),
    /**
     * Modify
     */
    UPDATE(3),
    /**
     * Delete
     */
    DELETE(4),
    /**
     * Export
     */
    EXPORT(5),
    /**
     * Import
     */
    IMPORT(6),
    /**
     * Other
     *
     * When it cannot be classified，You can choose to use other。Because there are operation names that can be further identified
     */
    OTHER(0);

    /**
     * Type
     */
    private final Integer type;

}
