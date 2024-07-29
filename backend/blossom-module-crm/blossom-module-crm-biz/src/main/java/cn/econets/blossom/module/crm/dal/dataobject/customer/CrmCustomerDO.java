package cn.econets.blossom.module.crm.dal.dataobject.customer;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.crm.enums.DictTypeConstants;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

// TODO Adjust the following fields

/**
 * CRM Customer DO
 *
 */
@TableName(value = "crm_customer")
@KeySequence("crm_customer_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmCustomerDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Customer Name
     */
    private String name;
    /**
     * Follow-up status
     */
    private Boolean followUpStatus;
    /**
     * Locked status
     */
    private Boolean lockStatus;
    /**
     * Transaction Status
     */
    private Boolean dealStatus;
    /**
     * Industry
     *
     * Corresponding dictionary {@link DictTypeConstants#CRM_CUSTOMER_INDUSTRY}
     */
    private Integer industryId;
    /**
     * Customer Level
     *
     * Corresponding dictionary {@link DictTypeConstants#CRM_CUSTOMER_LEVEL}
     */
    private Integer level;
    /**
     * Customer Source
     *
     * Corresponding dictionary {@link DictTypeConstants#CRM_CUSTOMER_SOURCE}
     */
    private Integer source;
    /**
     * Mobile phone
     */
    private String mobile;
    /**
     * Phone
     */
    private String telephone;
    /**
     * Website
     */
    private String website;
    /**
     * QQ
     */
    private String qq;
    /**
     * wechat
     */
    private String wechat;
    /**
     * email
     */
    private String email;
    /**
     * Customer Description
     */
    private String description;
    /**
     * Remarks
     */
    private String remark;
    /**
     * User ID of the person in charge
     *
     * Relationship AdminUserDO of id Field
     */
    private Long ownerUserId;
    /**
     * Location
     *
     * Relationship {@link cn.econets.blossom.framework.ip.core.Area#getId()} Field
     */
    private Integer areaId;
    /**
     * Detailed address
     */
    private String detailAddress;

    /**
     * Last received time
     */
    private LocalDateTime receiveTime;
    /**
     * Last follow-up time
     */
    private LocalDateTime contactLastTime;

    /**
     * Latest follow-up content
     */
    private String contactLastContent;
    /**
     * Next contact time
     */
    private LocalDateTime contactNextTime;

}
