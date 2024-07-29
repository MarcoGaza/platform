package cn.econets.blossom.module.infrastructure.dal.dataobject.demo.demo03;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * Student Courses DO
 *
 */
@TableName("infra_demo03_course")
@KeySequence("infra_demo03_course_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Demo03CourseDO extends BaseDO {

    /**
     * Number
     */
    @TableId
    private Long id;
    /**
     * Student Number
     */
    private Long studentId;
    /**
     * Name
     */
    private String name;
    /**
     * Score
     */
    private Integer score;

}
