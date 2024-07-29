package cn.econets.blossom.module.crm.dal.dataobject.product;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Product Category DO
 *
 */
@TableName("crm_product_category")
@KeySequence("crm_product_category_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmProductCategoryDO extends BaseDO {

    /**
     * Parent category number - Root category
     */
    public static final Long PARENT_ID_NULL = 0L;
    /**
     * Limit classification level
     */
    public static final int CATEGORY_LEVEL = 2;

    /**
     * Classification Number
     */
    @TableId
    private Long id;
    /**
     * Category Name
     */
    private String name;
    /**
     * Parent number
     *
     * Relationship {@link CrmProductCategoryDO#getId()}
     */
    private Long parentId;

}
