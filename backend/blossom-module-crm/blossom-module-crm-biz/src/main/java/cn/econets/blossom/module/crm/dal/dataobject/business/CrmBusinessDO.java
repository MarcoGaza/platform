package cn.econets.blossom.module.crm.dal.dataobject.business;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.crm.dal.dataobject.customer.CrmCustomerDO;
import cn.econets.blossom.module.crm.enums.business.CrmBizEndStatus;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Business Opportunities DO
 *
 */
@TableName("crm_business")
@KeySequence("crm_business_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmBusinessDO extends BaseDO {

    /**
     * Primary key
     */
    @TableId
    private Long id;
    /**
     * Opportunity Name
     */
    private String name;
    /**
     * Opportunity status type number
     *
     *  Relationship {@link CrmBusinessStatusTypeDO#getId()}
     */
    private Long statusTypeId;
    /**
     * Opportunity status number
     *
     * Relationship {@link CrmBusinessStatusDO#getId()}
     */
    private Long statusId;
    /**
     * Next contact time
     */
    private LocalDateTime contactNextTime;
    /**
     * Customer Number
     *
     * TODO @ljileo：This field，Will write down the associated entities later
     * Relationship {@link CrmCustomerDO#getId()}
     */
    private Long customerId;
    /**
     * Expected transaction date
     */
    private LocalDateTime dealTime;
    /**
     * Opportunity Amount
     *
     */
    private Integer price;
    /**
     * Entire order discount
     *
     */
    private Integer discountPercent;
    /**
     * Total amount of product，Unit：Points
     */
    private Integer productPrice;
    /**
     * Remarks
     */
    private String remark;
    /**
     * End status
     *
     * Enumeration {@link CrmBizEndStatus}
     */
    private Integer endStatus;
    /**
     * Closing notes
     */
    private String endRemark;
    /**
     * Last follow-up time
     */
    private LocalDateTime contactLastTime;
    /**
     * Follow-up status
     */
    private Boolean followUpStatus;

    /**
     * User ID of the person in charge
     *
     * Relationship AdminUserDO of id Field
     */
    private Long ownerUserId;

}
