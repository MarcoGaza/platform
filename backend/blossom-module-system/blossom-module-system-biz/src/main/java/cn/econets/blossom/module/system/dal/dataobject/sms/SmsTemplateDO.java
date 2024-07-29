package cn.econets.blossom.module.system.dal.dataobject.sms;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.system.enums.sms.SmsTemplateTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * SMS template DO
 *
 */
@TableName(value = "system_sms_template", autoResultMap = true)
@KeySequence("system_sms_template_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SmsTemplateDO extends BaseDO {

    /**
     * Self-increment number
     */
    private Long id;

    // ========= Template related fields =========

    /**
     * SMS type
     *
     * Enumeration {@link SmsTemplateTypeEnum}
     */
    private Integer type;
    /**
     * Enabled status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Template encoding，Guaranteed uniqueness
     */
    private String code;
    /**
     * Template name
     */
    private String name;
    /**
     * Template content
     *
     * Content parameters，Use {} Include，For example {name}
     */
    private String content;
    /**
     * Parameter array(Automatically generated based on content)
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> params;
    /**
     * Remarks
     */
    private String remark;
    /**
     * SMS API Template number
     */
    private String apiTemplateId;

    // ========= Channel related fields =========

    /**
     * SMS channel number
     *
     * Relationship {@link SmsChannelDO#getId()}
     */
    private Long channelId;
    /**
     * SMS channel code
     *
     * Redundant {@link SmsChannelDO#getCode()}
     */
    private String channelCode;

}
