package cn.econets.blossom.module.promotion.dal.dataobject.diy;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.StringListTypeHandler;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.List;

/**
 * Decoration page DO
 *
 */
@TableName(value = "promotion_diy_page", autoResultMap = true)
@KeySequence("promotion_diy_page_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiyPageDO extends BaseDO {

    /**
     * Decoration page number
     */
    @TableId
    private Long id;
    /**
     * Decoration template number
     *
     * Association {@link DiyTemplateDO#getId()}
     */
    private Long templateId;
    /**
     * Page Name
     */
    private String name;
    /**
     * Remarks
     */
    private String remark;
    /**
     * Preview image，Multiple comma separated
     */
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> previewPicUrls;
    /**
     * Page properties，JSON Format
     */
    private String property;

}
