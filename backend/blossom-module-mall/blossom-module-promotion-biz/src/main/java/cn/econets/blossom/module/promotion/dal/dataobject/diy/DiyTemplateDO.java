package cn.econets.blossom.module.promotion.dal.dataobject.diy;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.StringListTypeHandler;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Decoration template DO
 *
 * 1. Create a new template，The following can contain multiple {@link DiyPageDO} Page，For example, the home page、My
 * 2. If you need to use a template，Then {@link #used} Set to true，Indicates that it has been used，There is only one
 *
 */
@TableName(value = "promotion_diy_template", autoResultMap = true)
@KeySequence("promotion_diy_template_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiyTemplateDO extends BaseDO {

    /**
     * Decoration template number
     */
    @TableId
    private Long id;
    /**
     * Template name
     */
    private String name;
    /**
     * Whether to use
     */
    private Boolean used;
    /**
     * Usage time
     */
    private LocalDateTime usedTime;
    /**
     * Remarks
     */
    private String remark;

    /**
     * Preview image
     */
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> previewPicUrls;
    /**
     * uni-app Bottom navigation properties，JSON Format
     */
    private String property;

}
