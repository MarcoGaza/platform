package cn.econets.blossom.module.system.dal.dataobject.mail;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Mail Template DO
 *
 */
@TableName(value = "system_mail_template", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class MailTemplateDO extends BaseDO {

    /**
     * Primary key
     */
    private Long id;
    /**
     * Template name
     */
    private String name;
    /**
     * Template number
     */
    private String code;
    /**
     * The email account number to send the message to
     *
     * Relationship {@link MailAccountDO#getId()}
     */
    private Long accountId;

    /**
     * Sender's name
     */
    private String nickname;
    /**
     * Title
     */
    private String title;
    /**
     * Content
     */
    private String content;
    /**
     * Parameter array(Automatically generated based on content)
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
