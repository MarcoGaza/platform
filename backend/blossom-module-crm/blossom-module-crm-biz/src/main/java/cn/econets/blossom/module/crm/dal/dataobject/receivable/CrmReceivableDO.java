package cn.econets.blossom.module.crm.dal.dataobject.receivable;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.crm.dal.dataobject.contract.CrmContractDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Payback DO
 *
 */
@TableName("crm_receivable")
@KeySequence("crm_receivable_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmReceivableDO extends BaseDO {

    /**
     * ID
     */
    @TableId
    private Long id;
    /**
     * Payment number
     */
    private String no;
    // TODO @liuhongfeng：“Corresponding entity”，Refer to other modules，Relationship {@link TableField.MetaInfo#getJdbcType()}
    /**
     * Payment Refund Plan
     *
     * TODO @liuhongfeng：When will this field be updated?，You can also write it down
     *
     * Corresponding entity {@link CrmReceivablePlanDO}
     */
    private Long planId;
    /**
     * Customer ID
     *
     * Corresponding entity {@link cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO}
     */
    private Long customerId;
    /**
     * Contract ID
     *
     * Corresponding entity {@link CrmContractDO}
     */
    private Long contractId;
    /**
     * Workflow number
     *
     * TODO @liuhongfeng：This field，Will write down the associated entities later
     */
    private Long processInstanceId;
    /**
     * Payment Receipt Date
     */
    private LocalDateTime returnTime;
    // TODO @liuhongfeng：One less enumeration
    /**
     * Payment method
     */
    private Integer returnType;
    /**
     * Amount of payment received
     */
    private Integer price;
    // TODO @liuhongfeng：Fewer associated entities；
    /**
     * Person in charge
     */
    private Long ownerUserId;
    /**
     * Display order
     */
    private Integer sort;
    /**
     * Review Status
     *
     * Enumeration {@link cn.econets.blossom.module.crm.enums.common.CrmAuditStatusEnum}
     */
    private Integer auditStatus;
    /**
     * Remarks
     */
    private String remark;

}
