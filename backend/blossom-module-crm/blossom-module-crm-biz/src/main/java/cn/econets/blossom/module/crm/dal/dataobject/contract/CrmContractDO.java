package cn.econets.blossom.module.crm.dal.dataobject.contract;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessDO;
import cn.econets.blossom.module.crm.dal.dataobject.contact.CrmContactDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.enums.common.CrmAuditStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

// TODO Entity combing
/**
 * CRM Contract DO
 *
 */
@TableName("crm_contract")
@KeySequence("crm_contract_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmContractDO extends BaseDO {

    /**
     * Contract Number
     */
    @TableId
    private Long id;
    /**
     * Contract Number
     */
    private String no;
    /**
     * Contract Name
     */
    private String name;
    /**
     * Customer Number
     *
     * Relationship {@link CrmCustomerDO#getId()}
     */
    private Long customerId;
    /**
     * Opportunity Number
     *
     * Relationship {@link CrmBusinessDO#getId()}
     */
    private Long businessId;
    /**
     * Workflow number
     *
     * Relationship ProcessInstance of id Properties
     */
    private String processInstanceId;
    /**
     * Order date
     */
    private LocalDateTime orderDate;
    /**
     * Start time
     */
    private LocalDateTime startTime;
    /**
     * End time
     */
    private LocalDateTime endTime;
    /**
     * Contract Amount，Unit：Points
     */
    private Integer price;
    /**
     * Entire order discount
     */
    private Integer discountPercent;
    /**
     * Total amount of product，Unit：Points
     */
    private Integer productPrice;
    /**
     * Customer Signatory
     *
     * Relationship {@link CrmContactDO#getId()}
     */
    private Long contactId;
    /**
     * Company Signatory
     *
     * Relationship AdminUserDO of id Field
     */
    private Long signUserId;
    /**
     * Last follow-up time
     */
    private LocalDateTime contactLastTime;
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
     * Approval Status
     *
     * Enumeration {@link CrmAuditStatusEnum}
     */
    private Integer auditStatus;

}
