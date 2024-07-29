package cn.econets.blossom.module.infrastructure.enums.codegen;

import cn.econets.blossom.framework.common.util.object.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * Code generation template type
 *
 *
 */
@AllArgsConstructor
@Getter
public enum CodegenTemplateTypeEnum {

    ONE(1), // Single table（Add, delete, modify, check）
    TREE(2), // Tree table（Add, delete, modify, check）

    MASTER_NORMAL(10), // Master and Child Table - Main table - Normal Mode
    MASTER_ERP(11), // Master and Child Table - Main table - ERP Mode
    MASTER_INNER(12), // Master and Child Table - Main table - Embedded Mode
    SUB(15), // Master and Child Table - Subtable
    ;

    /**
     * Type
     */
    private final Integer type;

    /**
     * Is it the main table?
     *
     * @param type Type
     * @return Is it the main table?
     */
    public static boolean isMaster(Integer type) {
        return ObjectUtils.equalsAny(type,
                MASTER_NORMAL.type, MASTER_ERP.type, MASTER_INNER.type);
    }

    /**
     * Is it a tree table?
     *
     * @param type Type
     * @return Whether it is a tree table
     */
    public static boolean isTree(Integer type) {
        return Objects.equals(type, TREE.type);
    }

}
