package cn.econets.blossom.module.system.dal.dataobject.mail;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Email Account DO
 *
 * Purpose：Configure the account for sending emails
 *
 */
@TableName(value = "system_mail_account", autoResultMap = true)
@Data
@EqualsAndHashCode(callSuper = true)
public class MailAccountDO extends BaseDO {

    /**
     * Primary key
     */
    @TableId
    private Long id;
    /**
     * Mailbox
     */
    private String mail;

    /**
     * Username
     */
    private String username;
    /**
     * Password
     */
    private String password;
    /**
     * SMTP Server domain name
     */
    private String host;
    /**
     * SMTP Server port
     */
    private Integer port;
    /**
     * Is it enabled? SSL
     */
    private Boolean sslEnable;

}
