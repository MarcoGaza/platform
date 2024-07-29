package cn.econets.blossom.module.crm.dal.dataobject.business;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.framework.mybatis.core.type.LongListTypeHandler;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.List;

/**
 * Opportunity status type DO
 *
 */
@TableName(value = "crm_business_status_type", autoResultMap = true)
@KeySequence("crm_business_status_type_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrmBusinessStatusTypeDO extends BaseDO {

    /**
     * Primary key
     */
    @TableId
    private Long id;
    /**
     * Status type name
     */
    private String name;
    /**
     * Department number used
     */
    @TableField(typeHandler = LongListTypeHandler.class)
    private List<Long> deptIds;
    /**
     * Open status
     *
     * TODO Change to Integer，Relationship CommonStatus
     * Enumeration {@link CommonStatusEnum}
     */
    private Boolean status;

}
