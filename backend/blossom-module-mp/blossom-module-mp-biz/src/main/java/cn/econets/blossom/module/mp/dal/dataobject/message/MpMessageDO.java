package cn.econets.blossom.module.mp.dal.dataobject.message;

import cn.econets.blossom.framework.common.util.json.JsonUtils;
import cn.econets.blossom.framework.mybatis.core.dataobject.BaseDO;
import cn.econets.blossom.module.mp.dal.dataobject.account.MpAccountDO;
import cn.econets.blossom.module.mp.dal.dataobject.user.MpUserDO;
import cn.econets.blossom.module.mp.enums.message.MpMessageSendFromEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import lombok.*;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.builder.kefu.NewsBuilder;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * Public account news DO
 *
 *
 */
@TableName(value = "mp_message", autoResultMap = true)
@KeySequence("mp_message_seq") // Used for Oracle、PostgreSQL、Kingbase、DB2、H2 The primary key of the database is automatically increased。If yes MySQL Wait for database，Optional。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MpMessageDO extends BaseDO {

    /**
     * Primary key
     */
    @TableId
    private Long id;
    /**
     * WeChat public account message id
     */
    private Long msgId;
    /**
     * Official account ID
     *
     * Relationship {@link MpAccountDO#getId()}
     */
    private Long accountId;
    /**
     * Public Account appid
     *
     * Redundant {@link MpAccountDO#getAppId()}
     */
    private String appId;
    /**
     * The public account fans' number
     *
     * Relationship {@link MpUserDO#getId()}
     */
    private Long userId;
    /**
     * Official account fan logo
     *
     * Redundant {@link MpUserDO#getOpenid()}
     */
    private String openid;

    /**
     * Message type
     *
     * Enumeration {@link WxConsts.XmlMsgType}
     */
    private String type;
    /**
     * Source
     *
     * Enumeration {@link MpMessageSendFromEnum}
     */
    private Integer sendFrom;

    // ========= Normal message content https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_standard_messages.html

    /**
     * Message content
     *
     * The message type is {@link WxConsts.XmlMsgType} of TEXT
     */
    private String content;

    /**
     * The ID of the media file
     *
     * The message type is {@link WxConsts.XmlMsgType} of IMAGE、VOICE、VIDEO
     */
    private String mediaId;
    /**
     * Media files URL
     */
    private String mediaUrl;
    /**
     * Text after speech recognition
     *
     * The message type is {@link WxConsts.XmlMsgType} of VOICE
     */
    private String recognition;
    /**
     * Voice format，As amr，speex Wait
     *
     * The message type is {@link WxConsts.XmlMsgType} of VOICE
     */
    private String format;
    /**
     * Title
     *
     * The message type is {@link WxConsts.XmlMsgType} of VIDEO、MUSIC、LINK
     */
    private String title;
    /**
     * Description
     *
     * The message type is {@link WxConsts.XmlMsgType} of VIDEO、MUSIC
     */
    private String description;

    /**
     * Thumbnail media id，Upload multimedia files through the interface in the material management，Obtained id
     *
     * The message type is {@link WxConsts.XmlMsgType} of MUSIC、VIDEO
     */
    private String thumbMediaId;
    /**
     * Thumbnail media URL
     *
     * The message type is {@link WxConsts.XmlMsgType} of MUSIC、VIDEO
     */
    private String thumbMediaUrl;

    /**
     * Click on the picture and text message to jump to the link
     *
     * The message type is {@link WxConsts.XmlMsgType} of LINK
     */
    private String url;

    /**
     * Geographic location dimension
     *
     * The message type is {@link WxConsts.XmlMsgType} of LOCATION
     */
    private Double locationX;
    /**
     * Geographic location longitude
     *
     * The message type is {@link WxConsts.XmlMsgType} of LOCATION
     */
    private Double locationY;
    /**
     * Map zoom size
     *
     * The message type is {@link WxConsts.XmlMsgType} of LOCATION
     */
    private Double scale;
    /**
     * Detailed address
     *
     * The message type is {@link WxConsts.XmlMsgType} of LOCATION
     *
     * For example, Huangxing Road, Yangpu District 221-4 Hao Lin
     */
    private String label;

    /**
     * Image and text message array
     *
     * The message type is {@link WxConsts.XmlMsgType} of NEWS
     */
    @TableField(typeHandler = ArticleTypeHandler.class)
    private List<Article> articles;

    /**
     * Music Link
     *
     * The message type is {@link WxConsts.XmlMsgType} of MUSIC
     */
    private String musicUrl;
    /**
     * High quality music links
     *
     * WIFI Environmental priority use this link to play music
     *
     * The message type is {@link WxConsts.XmlMsgType} of MUSIC
     */
    private String hqMusicUrl;

    // ========= Event push https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_event_pushes.html

    /**
     * Event Type
     *
     * Enumeration {@link WxConsts.EventType}
     */
    private String event;
    /**
     * Event Key
     *
     * 1. {@link WxConsts.EventType} of SCAN：qrscene_ prefix，The parameter value behind is the QR code
     * 2. {@link WxConsts.EventType} of CLICK：Interface with custom menu KEY Value correspondence
     */
    private String eventKey;

    /**
     * Article
     */
    @Data
    public static class Article implements Serializable {

        /**
         * Title of picture and text message
         */
        @NotEmpty(message = "The title of the picture and text message cannot be empty", groups = NewsBuilder.class)
        private String title;
        /**
         * Image and text message description
         */
        @NotEmpty(message = "The description of the picture and text message cannot be empty", groups = NewsBuilder.class)
        private String description;
        /**
         * Image link
         *
         * Support JPG、PNG Format，Large images are better 360*200，Small picture 200*200
         */
        @NotEmpty(message = "Image link cannot be empty", groups = NewsBuilder.class)
        private String picUrl;
        /**
         * Click on the picture and text message to jump to the link
         */
        @NotEmpty(message = "Click on the picture and text message to jump to the link cannot be empty", groups = NewsBuilder.class)
        private String url;

    }

    // TODO ：You can find some new ideas
    public static class ArticleTypeHandler extends AbstractJsonTypeHandler<List<Article>> {

        @Override
        protected List<Article> parse(String json) {
            return JsonUtils.parseArray(json, Article.class);
        }

        @Override
        protected String toJson(List<Article> obj) {
            return JsonUtils.toJsonString(obj);
        }

    }
}
