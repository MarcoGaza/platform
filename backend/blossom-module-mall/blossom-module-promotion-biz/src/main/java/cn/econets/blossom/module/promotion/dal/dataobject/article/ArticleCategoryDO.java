package cn.econets.blossom.module.promotion.dal.dataobject.article;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Article Category DO
 *
 */
@TableName("promotion_article_category")
@KeySequence("promotion_article_category_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCategoryDO extends BaseDO {

    /**
     * Article Category Number
     */
    @TableId
    private Long id;
    /**
     * Article Category Name
     */
    private String name;
    /**
     * Icon address
     */
    private String picUrl;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Sort
     */
    private Integer sort;

}
