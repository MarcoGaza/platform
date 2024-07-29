package cn.econets.blossom.module.trade.dal.dataobject.brokerage;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordBizTypeEnum;
import cn.econets.blossom.module.trade.enums.brokerage.BrokerageRecordStatusEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Commission Record DO
 *
 */
@TableName("trade_brokerage_record")
@KeySequence("trade_brokerage_record_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrokerageRecordDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Integer id;
    /**
     * User Number
     * <p>
     * Relationship MemberUserDO.id
     */
    private Long userId;
    /**
     * Business Number
     */
    private String bizId;
    /**
     * Business Type
     * <p>
     * Enumeration {@link BrokerageRecordBizTypeEnum}
     */
    private Integer bizType;

    /**
     * Title
     */
    private String title;
    /**
     * Description
     */
    private String description;

    /**
     * Amount
     */
    private Integer price;
    /**
     * Current total commission
     */
    private Integer totalPrice;

    /**
     * Status
     * <p>
     * Enumeration {@link BrokerageRecordStatusEnum}
     */
    private Integer status;

    /**
     * Freeze time（Sky）
     */
    private Integer frozenDays;
    /**
     * Thaw time
     */
    private LocalDateTime unfreezeTime;

    /**
     * Source user level
     * <p>
     * Promoted users and {@link #userId} Promotion level relationship
     */
    private Integer sourceUserLevel;
    /**
     * Source user number
     * <p>
     * Relationship MemberUserDO.id Field，Number of the promoted user
     */
    private Long sourceUserId;

}
