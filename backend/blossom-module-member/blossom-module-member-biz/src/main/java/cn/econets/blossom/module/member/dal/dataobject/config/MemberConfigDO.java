package cn.econets.blossom.module.member.dal.dataobject.config;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Member Configuration DO
 *
 * 
 */
@TableName(value = "member_config", autoResultMap = true)
@KeySequence("member_config_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberConfigDO extends BaseDO {

    /**
     * Auto-increment primary key
     */
    @TableId
    private Long id;
    /**
     * Points deduction switch
     */
    private Boolean pointTradeDeductEnable;
    /**
     * Points deduction，Unit：Points
     *
     * 1 How many points can be deducted from the points
     */
    private Integer pointTradeDeductUnitPrice;
    /**
     * Maximum Points Deduction
     */
    private Integer pointTradeDeductMaxPrice;
    /**
     * 1 How many points are given for each yuan
     */
    private Integer pointTradeGivePoint;

}
