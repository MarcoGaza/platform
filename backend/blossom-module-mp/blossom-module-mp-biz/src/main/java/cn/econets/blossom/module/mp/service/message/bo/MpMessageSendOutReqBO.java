package cn.econets.blossom.module.mp.service.message.bo;

import cn.econets.blossom.module.mp.dal.dataobject.message.MpMessageDO;
import cn.econets.blossom.module.mp.framework.mp.core.util.MpUtils.*;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Send public account message Request BO
 *
 * Why do we need this? BO What？In automatic reply、Customer Service Message、Menu reply message and other scenarios，All involved MP Send a message to fans，So use this BO Unified acceptance
 *
 *
 */
@Data
public class MpMessageSendOutReqBO {

    /**
     * Public Account appId
     */
    @NotEmpty(message = "Public Account appId Cannot be empty")
    private String appId;
    /**
     * Public Account Fans openid
     */
    @NotEmpty(message = "Public Account Fans openid Cannot be empty")
    private String openid;

    // ========== Message content ==========
    /**
     * Message type
     *
     * Enumeration {@link WxConsts.XmlMsgType} In TEXT、IMAGE、VOICE、VIDEO、NEWS、MUSIC
     */
    @NotEmpty(message = "Message type cannot be empty")
    public String type;

    /**
     * Message content
     *
     * The message type is {@link WxConsts.XmlMsgType} of TEXT
     */
    @NotEmpty(message = "Message content cannot be empty", groups = TextMessageGroup.class)
    private String content;

    /**
     * Media id
     *
     * The message type is {@link WxConsts.XmlMsgType} of IMAGE、VOICE、VIDEO
     */
    @NotEmpty(message = "Message mediaId Cannot be empty", groups = {ImageMessageGroup.class, VoiceMessageGroup.class, VideoMessageGroup.class})
    private String mediaId;

    /**
     * Thumbnail media id
     *
     * The message type is {@link WxConsts.XmlMsgType} of VIDEO、MUSIC
     */
    @NotEmpty(message = "Message thumbMediaId Cannot be empty", groups = {MusicMessageGroup.class})
    private String thumbMediaId;

    /**
     * Title
     *
     * The message type is {@link WxConsts.XmlMsgType} of VIDEO
     */
    @NotEmpty(message = "The message title cannot be empty", groups = VideoMessageGroup.class)
    private String title;
    /**
     * Description
     *
     * The message type is {@link WxConsts.XmlMsgType} of VIDEO
     */
    @NotEmpty(message = "Message description cannot be empty", groups = VideoMessageGroup.class)
    private String description;

    /**
     * Image and text message
     *
     * The message type is {@link WxConsts.XmlMsgType} of NEWS
     */
    @Valid
    @NotNull(message = "The picture and text message cannot be empty", groups = NewsMessageGroup.class)
    private List<MpMessageDO.Article> articles;

    /**
     * Music Link
     *
     * The message type is {@link WxConsts.XmlMsgType} of MUSIC
     */
    @NotEmpty(message = "Music link cannot be empty", groups = MusicMessageGroup.class)
    @URL(message = "High quality music link format is incorrect", groups = MusicMessageGroup.class)
    private String musicUrl;

    /**
     * High quality music links
     *
     * The message type is {@link WxConsts.XmlMsgType} of MUSIC
     */
    @NotEmpty(message = "High-quality music links cannot be empty", groups = MusicMessageGroup.class)
    @URL(message = "The format of the high-quality music link is incorrect", groups = MusicMessageGroup.class)
    private String hqMusicUrl;

}
