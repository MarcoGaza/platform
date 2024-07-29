package cn.econets.blossom.module.mp.dal.dataobject.menu;

import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import cn.econets.blossom.module.mp.dal.dataobject.message.MpMessageDO;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;

import java.util.List;

/**
 * Official Account Menu DO
 *
 *
 */
@TableName(value = "mp_menu", autoResultMap = true)
@KeySequence("mp_menu_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically incremented。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MpMenuDO extends BaseDO {

    /**
     * Number - Top Menu
     */
    public static final Long ID_ROOT = 0L;

    /**
     * Number
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
     * Menu Name
     */
    private String name;
    /**
     * Menu logo
     *
     * Support multiple DB Type，Cannot be used directly key + @TableField("menuKey") To achieve conversion，The reason is "menuKey" AS key An error occurs
     */
    private String menuKey;
    /**
     * Parent menu number
     */
    private Long parentId;

    // ========== Button operation ==========

    /**
     * Button Type
     *
     * Enumeration {@link MenuButtonType}
     */
    private String type;

    /**
     * Web link
     *
     * Fans can click the menu to open the link，No more than 1024 Bytes
     *
     * Type is {@link WxConsts.XmlMsgType} of VIEW、MINIPROGRAM
     */
    private String url;

    /**
     * Mini program appId
     *
     * Type is {@link MenuButtonType} of MINIPROGRAM
     */
    private String miniProgramAppId;
    /**
     * Page path of the mini program
     *
     * Type is {@link MenuButtonType} of MINIPROGRAM
     */
    private String miniProgramPagePath;

    /**
     * Media number for jumping to pictures and texts
     */
    private String articleId;

    // ========== Message content ==========

    /**
     * Message type
     *
     * When {@link #type} for CLICK、SCANCODE_WAITMSG
     *
     * Enumeration {@link WxConsts.XmlMsgType} In TEXT、IMAGE、VOICE、VIDEO、NEWS、MUSIC
     */
    private String replyMessageType;

    /**
     * Content of the reply message
     *
     * The message type is {@link WxConsts.XmlMsgType} of TEXT
     */
    private String replyContent;

    /**
     * Replied media id
     *
     * The message type is {@link WxConsts.XmlMsgType} of IMAGE、VOICE、VIDEO
     */
    private String replyMediaId;
    /**
     * Replied media URL
     *
     * The message type is {@link WxConsts.XmlMsgType} of IMAGE、VOICE、VIDEO
     */
    private String replyMediaUrl;

    /**
     * Reply title
     *
     * The message type is {@link WxConsts.XmlMsgType} of VIDEO
     */
    private String replyTitle;
    /**
     * Reply description
     *
     * The message type is {@link WxConsts.XmlMsgType} of VIDEO
     */
    private String replyDescription;

    /**
     * Reply thumbnail media id，Upload multimedia files through the interface in the material management，Obtained id
     *
     * The message type is {@link WxConsts.XmlMsgType} of MUSIC、VIDEO
     */
    private String replyThumbMediaId;
    /**
     * Reply thumbnail media URL
     *
     * The message type is {@link WxConsts.XmlMsgType} of MUSIC、VIDEO
     */
    private String replyThumbMediaUrl;

    /**
     * An array of text and image messages to be replied to
     *
     * The message type is {@link WxConsts.XmlMsgType} of NEWS
     */
    @TableField(typeHandler = MpMessageDO.ArticleTypeHandler.class)
    private List<MpMessageDO.Article> replyArticles;

    /**
     * Music link in reply
     *
     * The message type is {@link WxConsts.XmlMsgType} of MUSIC
     */
    private String replyMusicUrl;
    /**
     * High quality music links in reply
     *
     * WIFI Environmental priority use this link to play music
     *
     * The message type is {@link WxConsts.XmlMsgType} of MUSIC
     */
    private String replyHqMusicUrl;

}
