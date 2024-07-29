package cn.econets.blossom.module.crm.service.customer.bo;

import cn.econets.blossom.framework.common.validation.Mobile;
import cn.econets.blossom.framework.common.validation.Telephone;
import cn.econets.blossom.module.crm.enums.DictTypeConstants;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Customer Creation Create Req BO
 *
 */
@Data
public class CrmCustomerCreateReqBO {

    /**
     * Customer Name
     */
    @NotEmpty(message = "Customer name cannot be empty")
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
     * Transaction status
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
    @Mobile
    private String mobile;
    /**
     * Phone
     */
    @Telephone
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
     * Mailbox
     */
    @Email(message = "The email format is incorrect")
    private String email;

    /**
     * Customer Description
     */
    @Size(max = 4096, message = "The length of the customer description cannot exceed 4096 Characters")
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
