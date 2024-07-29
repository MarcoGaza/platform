package cn.econets.blossom.module.infrastructure.dal.dataobject.codegen;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.infrastructure.dal.dataobject.db.DataSourceConfigDO;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenFrontTypeEnum;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenSceneEnum;
import cn.econets.blossom.module.infrastructure.enums.codegen.CodegenTemplateTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * Code Generation table Table definition
 *
 */
@TableName(value = "infra_codegen_table", autoResultMap = true)
@KeySequence("infra_codegen_table_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CodegenTableDO extends BaseDO {

    /**
     * ID Number
     */
    @TableId
    private Long id;

    /**
     * Data source number
     *
     * Relationship {@link DataSourceConfigDO#getId()}
     */
    private Long dataSourceConfigId;
    /**
     * Generate scene
     *
     * Enumeration {@link CodegenSceneEnum}
     */
    private Integer scene;

    // ========== Table related fields ==========

    /**
     * Table name
     *
     * Relationship {@link TableInfo#getName()}
     */
    private String tableName;
    /**
     * Table description
     *
     * Relationship {@link TableInfo#getComment()}
     */
    private String tableComment;
    /**
     * Remarks
     */
    private String remark;

    // ========== Class related fields ==========

    /**
     * Module name，That is, the first-level directory
     *
     * For example，system、infra、tool Wait
     */
    private String moduleName;
    /**
     * Business Name，The second level directory
     *
     * For example，user、permission、dict Wait
     */
    private String businessName;
    /**
     * Class Name（Capitalize the first letter）
     *
     * For example，SysUser、SysMenu、SysDictData Wait
     */
    private String className;
    /**
     * Class description
     */
    private String classComment;
    /**
     * Author
     */
    private String author;

    // ========== Generate related fields ==========

    /**
     * Template type
     *
     * Enumeration {@link CodegenTemplateTypeEnum}
     */
    private Integer templateType;
    /**
     * Front-end type of code generation
     *
     * Enumeration {@link CodegenFrontTypeEnum}
     */
    private Integer frontType;

    // ========== Menu related fields ==========

    /**
     * Parent menu number
     *
     * Relationship MenuDO of id Attributes
     */
    private Long parentMenuId;

    // ========== Main and sub-table related fields ==========

    /**
     * The number of the main table
     *
     * Relationship {@link CodegenTableDO#getId()}
     */
    private Long masterTableId;
    /**
     * 【Myself】The field number of the sub-table associated with the main table
     *
     * Relationship {@link CodegenColumnDO#getId()}
     */
    private Long subJoinColumnId;
    /**
     * Are the main table and the sub-table one-to-many?
     *
     * true：One to many
     * false：One-to-one
     */
    private Boolean subJoinMany;

    // ========== Tree table related fields ==========

    /**
     * Parent field number of the tree table
     *
     * Relationship {@link CodegenColumnDO#getId()}
     */
    private Long treeParentColumnId;
    /**
     * The name field number of the tree table
     *
     * The purpose of the name：When adding or modifying，select Fields displayed in the box
     *
     * Relationship {@link CodegenColumnDO#getId()}
     */
    private Long treeNameColumnId;

}
