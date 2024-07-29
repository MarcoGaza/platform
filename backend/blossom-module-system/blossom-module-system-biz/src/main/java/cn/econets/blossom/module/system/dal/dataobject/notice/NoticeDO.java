package cn.econets.blossom.module.system.dal.dataobject.notice;

import cn.econets.blossom.framework.common.enums.CommonStatusEnum;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.system.enums.notice.NoticeTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Notice table
 *
 */
@TableName("system_notice")
@KeySequence("system_notice_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Waiting for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
public class NoticeDO extends BaseDO {

    /**
     * AnnouncementID
     */
    private Long id;
    /**
     * Announcement Title
     */
    private String title;
    /**
     * Announcement Type
     *
     * Enumeration {@link NoticeTypeEnum}
     */
    private Integer type;
    /**
     * Announcement content
     */
    private String content;
    /**
     * Announcement status
     *
     * Enumeration {@link CommonStatusEnum}
     */
    private Integer status;

}
