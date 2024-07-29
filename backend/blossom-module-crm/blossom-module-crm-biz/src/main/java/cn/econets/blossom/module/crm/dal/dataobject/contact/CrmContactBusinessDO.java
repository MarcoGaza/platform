package cn.econets.blossom.module.crm.dal.dataobject.contact;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.crm.dal.dataobject.business.CrmBusinessDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * CRM The relationship between contacts and opportunities DO
 *
 */
@TableName("crm_contact_business")
@KeySequence("crm_contact_business_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmContactBusinessDO extends BaseDO {

    /**
     * Primary key
     */
    @TableId
    private Long id;
    /**
     * Contact Number
     *
     * Relationship {@link CrmContactDO#getId()} Field
     */
    private Long contactId;
    /**
     * Opportunity Number
     *
     * Relationship {@link CrmBusinessDO#getId()} Field
     */
    private Long businessId;

}
