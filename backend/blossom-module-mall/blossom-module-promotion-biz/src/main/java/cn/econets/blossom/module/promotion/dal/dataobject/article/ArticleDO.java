package cn.econets.blossom.module.promotion.dal.dataobject.article;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Article Management DO
 *
 */
@TableName("promotion_article")
@KeySequence("promotion_article_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDO extends BaseDO {

    /**
     * Article management number
     */
    @TableId
    private Long id;
    /**
     * Classification Number ArticleCategoryDO#id
     */
    private Long categoryId;
    /**
     * Related product number ProductSpuDO#id
     */
    private Long spuId;
    /**
     * Article Title
     */
    private String title;
    /**
     * Article author
     */
    private String author;
    /**
     * Article cover image address
     */
    private String picUrl;
    /**
     * Article Introduction
     */
    private String introduction;
    /**
     * Number of views
     */
    private Integer browseCount;
    /**
     * Sort
     */
    private Integer sort;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Is it popular?(Mini Program)
     */
    private Boolean recommendHot;
    /**
     * Whether to play the pictures in rotation(Mini Program)
     */
    private Boolean recommendBanner;
    /**
     * Article content
     */
    private String content;

}
