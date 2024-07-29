package cn.econets.blossom.module.mp.dal.dataobject.message;

import cn.econets.blossom.framework.common.util.collection.SetUtils;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import cn.econets.blossom.module.mp.enums.message.MpAutoReplyMatchEnum;
import cn.econets.blossom.module.mp.enums.message.MpAutoReplyTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

import java.util.List;
import java.util.Set;

/**
 * Automatically reply to public account messages DO
 *
 *
 */
@TableName(value = "mp_auto_reply", autoResultMap = true)
@KeySequence("mp_auto_reply_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 Database primary key auto-increment。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MpAutoReplyDO extends BaseDO {

    public static Set<String> REQUEST_MESSAGE_TYPE = SetUtils.asSet(WxConsts.XmlMsgType.TEXT, WxConsts.XmlMsgType.IMAGE,
            WxConsts.XmlMsgType.VOICE, WxConsts.XmlMsgType.VIDEO, WxConsts.XmlMsgType.SHORTVIDEO,
            WxConsts.XmlMsgType.LOCATION, WxConsts.XmlMsgType.LINK);

    /**
     * Primary key
     */
    @TableId
    private Long id;
    /**
     * The public account number
     *
     * Relationship {@link MpAccountDO#getId()}
     */
    private Long accountId;
    /**
     * Public Account appId
     *
     * Redundant {@link MpAccountDO#getAppId()}
     */
    private String appId;

    /**
     * Reply type
     *
     * Enumeration {@link MpAutoReplyTypeEnum}
     */
    private Integer type;

    // ==================== Request message ====================

    /**
     * Requested keywords
     *
     * When {@link #type} for {@link MpAutoReplyTypeEnum#KEYWORD}
     */
    private String requestKeyword;
    /**
     * Match of requested keywords
     *
     * When {@link #type} for {@link MpAutoReplyTypeEnum#KEYWORD}
     *
     * Enumeration {@link MpAutoReplyMatchEnum}
     */
    private Integer requestMatch;

    /**
     * Requested message type
     *
     * When {@link #type} for {@link MpAutoReplyTypeEnum#MESSAGE}
     *
     * Enumeration {@link XmlMsgType} In {@link #REQUEST_MESSAGE_TYPE}
     */
    private String requestMessageType;

    // ==================== Response message ====================

    /**
     * Reply message type
     *
     * Enumeration {@link XmlMsgType} In TEXT、IMAGE、VOICE、VIDEO、NEWS
     */
    private String responseMessageType;

    /**
     * Content of the reply message
     *
     * The message type is {@link WxConsts.XmlMsgType} of TEXT
     */
    private String responseContent;

    /**
     * Replied media id
     *
     * The message type is {@link WxConsts.XmlMsgType} of IMAGE、VOICE、VIDEO
     */
    private String responseMediaId;
    /**
     * Replied media URL
     */
    private String responseMediaUrl;

    /**
     * Reply title
     *
     * The message type is {@link WxConsts.XmlMsgType} of VIDEO
     */
    private String responseTitle;
    /**
     * Reply description
     *
     * The message type is {@link WxConsts.XmlMsgType} of VIDEO
     */
    private String responseDescription;

    /**
     * Reply thumbnail media id，Upload multimedia files through the interface in the material management，Obtained id
     *
     * The message type is {@link WxConsts.XmlMsgType} of MUSIC、VIDEO
     */
    private String responseThumbMediaId;
    /**
     * Reply thumbnail media URL
     *
     * The message type is {@link WxConsts.XmlMsgType} of MUSIC、VIDEO
     */
    private String responseThumbMediaUrl;

    /**
     * Reply to the picture and text message
     *
     * The message type is {@link WxConsts.XmlMsgType} of NEWS
     */
    @TableField(typeHandler = MpMessageDO.ArticleTypeHandler.class)
    private List<MpMessageDO.Article> responseArticles;

    /**
     * Reply music link
     *
     * The message type is {@link WxConsts.XmlMsgType} of MUSIC
     */
    private String responseMusicUrl;
    /**
     * High quality music links in reply
     *
     * WIFI Environmental priority use this link to play music
     *
     * The message type is {@link WxConsts.XmlMsgType} of MUSIC
     */
    private String responseHqMusicUrl;

}
