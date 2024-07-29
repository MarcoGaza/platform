package cn.econets.blossom.module.crm.dal.dataobject.business;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Opportunity Status DO
 *
 */
@TableName("crm_business_status")
@KeySequence("crm_business_status_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmBusinessStatusDO {

    /**
     * Primary key
     */
    @TableId
    private Long id;
    /**
     * Status type number
     *
     * Relationship {@link CrmBusinessStatusTypeDO#getId()}
     */
    private Long typeId;
    /**
     * Status name
     */
    private String name;
    /**
     * Winning rate，Percentage
     */
    private Integer percent;
    /**
     * Sort
     */
    private Integer sort;

}
