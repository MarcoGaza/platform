package cn.econets.blossom.module.crm.dal.dataobject.clue;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.enums.DictTypeConstants;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

// TODO Field order，Needs to be sorted out；
/**
 * Clues DO
 *
 */
@TableName("crm_clue")
@KeySequence("crm_clue_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmClueDO extends BaseDO {

    /**
     * Number，Primary key auto-increment
     */
    @TableId
    private Long id;
    /**
     * Conversion Status
     */
    private Boolean transformStatus;
    /**
     * Follow-up status
     */
    private Boolean followUpStatus;
    /**
     * Clue Name
     */
    private String name;
    /**
     * Customer id
     *
     * Relationship {@link CrmCustomerDO#getId()}
     */
    private Long customerId;
    /**
     * Next contact time
     */
    private LocalDateTime contactNextTime;
    /**
     * Phone
     */
    private String telephone;
    /**
     * Mobile phone number
     */
    private String mobile;
    /**
     * Address
     */
    private String address;
    /**
     * Last follow-up time TODO Update this value when adding a follow-up record
     */
    private LocalDateTime contactLastTime;
    /**
     * Remarks
     */
    private String remark;
    /**
     * User ID of the person in charge
     * Relationship AdminUserDO of id Field
     */
    private Long ownerUserId;
    /**
     * Industry
     * Corresponding dictionary {@link DictTypeConstants#CRM_CUSTOMER_INDUSTRY}
     */
    private Integer industryId;
    /**
     * Customer Level
     * Corresponding dictionary {@link DictTypeConstants#CRM_CUSTOMER_LEVEL}
     */
    private Integer level;
    /**
     * Customer Source
     * Corresponding dictionary {@link DictTypeConstants#CRM_CUSTOMER_SOURCE}
     */
    private Integer source;
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
}
