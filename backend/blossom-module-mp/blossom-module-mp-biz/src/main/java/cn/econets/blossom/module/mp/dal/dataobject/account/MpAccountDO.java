package cn.econets.blossom.module.mp.dal.dataobject.account;

import cn.econets.blossom.framework.tenant.core.db.TenantBaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Public Account DO
 *
 *
 */
@TableName("mp_account")
@KeySequence("mp_account_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MpAccountDO extends TenantBaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Public Account Name
     */
    private String name;
    /**
     * Public Account
     */
    private String account;
    /**
     * Public Account appid
     */
    private String appId;
    /**
     * Public account key
     */
    private String appSecret;
    /**
     * Public Accounttoken
     */
    private String token;
    /**
     * Message encryption and decryption key
     */
    private String aesKey;
    /**
     * QR code image URL
     */
    private String qrCodeUrl;
    /**
     * Remarks
     */
    private String remark;

}
