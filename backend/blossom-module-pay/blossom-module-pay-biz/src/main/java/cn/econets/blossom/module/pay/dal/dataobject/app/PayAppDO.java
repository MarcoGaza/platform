package cn.econets.blossom.module.pay.dal.dataobject.app;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Payment Application DO
 * Under one merchant，There may be multiple payment applications。For example，JD has JD Mall、Jingdong to home delivery, etc.
 * But generally speaking，A merchant，There is only one application~
 *
 * That is PayMerchantDO : PayAppDO = 1 : n
 *
 *
 */
@TableName("pay_app")
@KeySequence("pay_app_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayAppDO extends BaseDO {

    /**
     * Application Number，Database auto-increment
     */
    @TableId
    private Long id;
    /**
     * Application Name
     */
    private String name;
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
    /**
     * Payment result callback address
     */
    private String orderNotifyUrl;
    /**
     * Refund result callback address
     */
    private String refundNotifyUrl;

    /**
     * Callback address of transfer result
     */
    private String transferNotifyUrl;

}
