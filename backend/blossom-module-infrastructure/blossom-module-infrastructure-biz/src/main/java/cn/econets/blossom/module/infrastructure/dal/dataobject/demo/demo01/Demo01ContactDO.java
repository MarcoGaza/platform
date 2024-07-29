package cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo01;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Sample Contact DO
 *
 */
@TableName("infra_demo01_contact")
@KeySequence("infra_demo01_contact_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Demo01ContactDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Name
     */
    private String name;
    /**
     * Gender
     *
     * Enumeration {@link TODO system_user_sex Corresponding class}
     */
    private Integer sex;
    /**
     * Year of Birth
     */
    private LocalDateTime birthday;
    /**
     * Introduction
     */
    private String description;
    /**
     * Avatar
     */
    private String avatar;

}
