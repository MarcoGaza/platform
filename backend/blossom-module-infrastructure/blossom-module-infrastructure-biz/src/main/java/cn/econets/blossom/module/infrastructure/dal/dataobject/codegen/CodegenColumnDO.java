package cn.econets.blossom.module.infrastructure.dal.dataobject.codegen;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenColumnHtmlTypeEnum;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenColumnListConditionEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Code Generation column Field definition
 *
 */
@TableName(value = "infra_codegen_column", autoResultMap = true)
@KeySequence("infra_codegen_column_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CodegenColumnDO extends BaseDO {

    /**
     * ID Number
     */
    @TableId
    private Long id;
    /**
     * Table number
     * <p>
     * Relationship {@link CodegenTableDO#getId()}
     */
    private Long tableId;

    // ========== Table related fields ==========

    /**
     * Field name
     *
     * Relationship {@link TableField#getName()}
     */
    private String columnName;
    /**
     * Database field type
     *
     * Relationship {@link TableField.MetaInfo#getJdbcType()}
     */
    private String dataType;
    /**
     * Field description
     *
     * Relationship {@link TableField#getComment()}
     */
    private String columnComment;
    /**
     * Is it allowed to be empty?
     *
     * Relationship {@link TableField.MetaInfo#isNullable()}
     */
    private Boolean nullable;
    /**
     * Is it the primary key?
     *
     * Relationship {@link TableField#isKeyFlag()}
     */
    private Boolean primaryKey;
    /**
     * Whether to auto-increment
     *
     * Relationship {@link TableField#isKeyIdentityFlag()}
     */
    private Boolean autoIncrement;
    /**
     * Sort
     */
    private Integer ordinalPosition;

    // ========== Java Related fields ==========

    /**
     * Java Attribute type
     *
     * For example String、Boolean Wait
     *
     * Relationship {@link TableField#getColumnType()}
     */
    private String javaType;
    /**
     * Java Attribute name
     *
     * Relationship {@link TableField#getPropertyName()}
     */
    private String javaField;
    /**
     * Dictionary type
     * <p>
     * Relationship DictTypeDO of type Attributes
     */
    private String dictType;
    /**
     * Data Example，Mainly used for generation Swagger Annotated example Field
     */
    private String example;

    // ========== CRUD Related fields ==========

    /**
     * Whether Create Create operation fields
     */
    private Boolean createOperation;
    /**
     * Whether Update Update operation fields
     */
    private Boolean updateOperation;
    /**
     * Whether List Fields for query operations
     */
    private Boolean listOperation;
    /**
     * List Condition type of query operation
     * <p>
     * Enumeration {@link CodegenColumnListConditionEnum}
     */
    private String listOperationCondition;
    /**
     * Whether List Return fields of query operation
     */
    private Boolean listOperationResult;

    // ========== UI Related fields ==========

    /**
     * Display type
     * <p>
     * Enumeration {@link CodegenColumnHtmlTypeEnum}
     */
    private String htmlType;

}
