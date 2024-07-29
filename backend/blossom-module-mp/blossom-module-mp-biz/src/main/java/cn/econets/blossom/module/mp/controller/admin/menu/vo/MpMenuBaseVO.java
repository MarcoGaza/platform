package cn.econets.blossom.module.mp.controller.admin.menu.vo;

import cn.econets.blossom.module.mp.dal.dataobject.message.MpMessageDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

import static cn.econets.blossom.module.mp.framework.mp.core.util.MpUtils.*;

/**
 * Official Account Menu Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class MpMenuBaseVO {

    /**
     * Menu name
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
     * Enumeration {@link WxConsts.MenuButtonType}
     */
    private String type;

    @Schema(description = "Web link", example = "https://www.econets.cn/")
    @NotEmpty(message = "Web link cannot be empty", groups = {ViewButtonGroup.class, MiniProgramButtonGroup.class})
    @URL(message = "Web links must be URL Format")
    private String url;

    @Schema(description = "Mini program appId", example = "wx1234567890")
    @NotEmpty(message = "Mini program appId Cannot be empty", groups = MiniProgramButtonGroup.class)
    private String miniProgramAppId;

    @Schema(description = "Page path of the mini program", example = "pages/index/index")
    @NotEmpty(message = "The page path of the mini program cannot be empty", groups = MiniProgramButtonGroup.class)
    private String miniProgramPagePath;

    @Schema(description ="Media number of the image or text to jump to", example = "jCQk93AIIgp8ixClWcW_NXXqBKInNWNmq2XnPeDZl7IMVqWiNeL4FfELtggRXd83")
    @NotEmpty(message = "The media ID of the jump image and text cannot be empty", groups = ViewLimitedButtonGroup.class)
    private String articleId;

    // ========== Message content ==========

    @Schema(description = "Reply message type Enumeration TEXT、IMAGE、VOICE、VIDEO、NEWS、MUSIC", example = "text")
    @NotEmpty(message = "The reply message type cannot be empty", groups = {ClickButtonGroup.class, ScanCodeWaitMsgButtonGroup.class})
    private String replyMessageType;

    @Schema(description = "Content of the reply message", example = "Welcome to follow")
    @NotEmpty(message = "The reply message content cannot be empty", groups = TextMessageGroup.class)
    private String replyContent;

    @Schema(description = "Replied media id", example = "123456")
    @NotEmpty(message = "Reply message mediaId Cannot be empty",
            groups = {ImageMessageGroup.class, VoiceMessageGroup.class, VideoMessageGroup.class})
    private String replyMediaId;
    @Schema(description = "Replied media URL", example = "https://www.econets.cn/xxx.jpg")
    @NotEmpty(message = "Reply message mediaId Cannot be empty",
            groups = {ImageMessageGroup.class, VoiceMessageGroup.class, VideoMessageGroup.class})
    private String replyMediaUrl;

    @Schema(description = "Thumbnail media id", example = "123456")
    @NotEmpty(message = "Reply message thumbMediaId Cannot be empty", groups = {MusicMessageGroup.class})
    private String replyThumbMediaId;
    @Schema(description = "Thumbnail media URL",example = "https://www.econets.cn/xxx.jpg")
    @NotEmpty(message = "Reply message thumbMedia Address cannot be empty", groups = {MusicMessageGroup.class})
    private String replyThumbMediaUrl;

    @Schema(description = "Reply title", example = "Video title")
    @NotEmpty(message = "The reply message title cannot be empty", groups = VideoMessageGroup.class)
    private String replyTitle;
    @Schema(description = "Reply description", example = "Video description")
    @NotEmpty(message = "Message description cannot be empty", groups = VideoMessageGroup.class)
    private String replyDescription;

    /**
     * An array of text and image messages to be replied to
     *
     * The message type is {@link WxConsts.XmlMsgType} of NEWS
     */
    @NotNull(message = "The reply message cannot be empty", groups = {NewsMessageGroup.class, ViewLimitedButtonGroup.class})
    @Valid
    private List<MpMessageDO.Article> replyArticles;

    @Schema(description = "Reply music link", example = "https://www.econets.cn/xxx.mp3")
    @NotEmpty(message = "The music link in reply cannot be empty", groups = MusicMessageGroup.class)
    @URL(message = "The format of the high-quality music link in the reply is incorrect", groups = MusicMessageGroup.class)
    private String replyMusicUrl;
    @Schema(description = "High quality music links", example = "https://www.econets.cn/xxx.mp3")
    @NotEmpty(message = "The high-quality music link in the reply cannot be empty", groups = MusicMessageGroup.class)
    @URL(message = "The format of the high-quality music link in the reply is incorrect", groups = MusicMessageGroup.class)
    private String replyHqMusicUrl;

}
