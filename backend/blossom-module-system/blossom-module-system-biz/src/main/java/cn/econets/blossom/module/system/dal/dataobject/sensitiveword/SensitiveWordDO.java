package cn.econets.blossom.module.system.dal.dataobject.sensitiveword;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.StringListTypeHandler;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.List;

/**
 * Sensitive words DO
 *
 */
@TableName(value = "system_sensitive_word", autoResultMap = true)
@KeySequence("system_sensitive_word_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensitiveWordDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Sensitive words
     */
    private String name;
    /**
     * Description
     */
    private String description;
    /**
     * Tag array
     *
     * Used to implement different business scenarios，Sensitive words that require different labels。
     * For example，tag There is a text message、Two types of forums，Sensitive words "Promotion" This is a sensitive word in the text message，It is not a sensitive word in the forum。
     * At this time，We will store a sensitive word record，Its name for"Promotion"，tag For SMS。
     */
    @TableField(typeHandler = StringListTypeHandler.class)
    private List<String> tags;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

}
