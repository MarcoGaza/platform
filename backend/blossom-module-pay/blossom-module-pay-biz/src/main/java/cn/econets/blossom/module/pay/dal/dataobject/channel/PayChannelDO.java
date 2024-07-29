package cn.econets.blossom.module.pay.dal.dataobject.channel;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.pay.core.client.PayClientConfig;
import cn.econets.blossom.framework.pay.core.enums.channel.PayChannelEnum;
import cn.econets.blossom.framework.tenant.core.db.TenantBaseDO;
import cn.econets.blossom.module.pay.dal.dataobject.app.PayAppDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.*;

/**
 * Payment channel DO
 * Under one application，There will be multiple payment channels，For example, WeChat Pay、Alipay payment, etc.
 *
 * That is PayAppDO : PayChannelDO = 1 : n
 *
 *
 */
@TableName(value = "pay_channel", autoResultMap = true)
@KeySequence("pay_channel_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayChannelDO extends TenantBaseDO {

    /**
     * Channel Number，Database auto-increment
     */
    private Long id;
    /**
     * Channel Code
     *
     * Enumeration {@link PayChannelEnum}
     */
    private String code;
    /**
     * Status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * Channel Rate，Unit：Percentage
     */
    private Double feeRate;
    /**
     * Remarks
     */
    private String remark;

    /**
     * Application number
     *
     * Relationship {@link PayAppDO#getId()}
     */
    private Long appId;
    /**
     * Payment channel configuration
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private PayClientConfig config;

}
