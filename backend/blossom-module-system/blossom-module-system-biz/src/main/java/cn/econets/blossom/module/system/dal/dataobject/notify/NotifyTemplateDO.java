package cn.econets.blossom.module.system.dal.dataobject.notify;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

import java.util.List;

/**
 * Internal message template DO
 *
 */
@TableName(value = "system_notify_template", autoResultMap = true)
@KeySequence("system_notify_template_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotifyTemplateDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * Template name
     */
    private String name;
    /**
     * Template code
     */
    private String code;
    /**
     * Template type
     *
     * Corresponding system_notify_template_type Dictionary
     */
    private Integer type;
    /**
     * Sender's name
     */
    private String nickname;
    /**
     * Template content
     */
    private String content;
    /**
     * Parameter array
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> params;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Remarks
     */
    private String remark;

}
