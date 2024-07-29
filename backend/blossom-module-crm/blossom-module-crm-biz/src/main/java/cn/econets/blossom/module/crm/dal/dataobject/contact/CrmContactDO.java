package cn.econets.blossom.module.crm.dal.dataobject.contact;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * CRM Contact Person DO
 *
 */
@TableName("crm_contact")
@KeySequence("crm_contact_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmContactDO extends BaseDO {

    /**
     * Primary key
     */
    @TableId
    private Long id;
    /**
     * Customer Number
     *
     * Relationship {@link CrmCustomerDO#getId()}
     */
    private Long customerId;
    /**
     * Mobile phone number
     */
    private String mobile;
    /**
     * Phone
     */
    private String telephone;
    /**
     * Email
     */
    private String email;
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
     * Remarks
     */
    private String remark;

    /**
     * Direct superior
     *
     * Relationship {@link CrmContactDO#id}
     */
    private Long parentId;
    /**
     * Name
     */
    private String name;
    /**
     * Position
     */
    private String post;
    /**
     * QQ
     */
    private Long qq;
    /**
     * WeChat
     */
    private String wechat;
    /**
     * Gender
     *
     * Enumeration {@link cn.econets.blossom.module.system.enums.common.SexEnum}
     */
    private Integer sex;
    /**
     * Is he a key decision maker?
     */
    private Boolean master;
    /**
     * User ID of the person in charge
     *
     * Relationship AdminUserDO of id Field
     */
    private Long ownerUserId;

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
