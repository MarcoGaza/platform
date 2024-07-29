package cn.econets.blossom.module.mp.controller.admin.message.vo.autoreply;

import cn.hutool.core.util.ObjectUtil;
import cn.econets.blossom.module.mp.dal.dataobject.message.MpMessageDO;
import cn.econets.blossom.module.mp.enums.message.MpAutoReplyTypeEnum;
import cn.econets.blossom.module.mp.framework.mp.core.util.MpUtils.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;
import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Automatic reply to public account  Base VO，Provide for adding、Modify、Detailed sub VO Use
 * If VO Fields with differences，Please do not add here，Influence Swagger Document Generation
 */
@Data
public class MpAutoReplyBaseVO {

    @Schema(description = "Reply type See MpAutoReplyTypeEnum Enumeration", example = "1")
    @NotNull(message = "Reply type cannot be empty")
    private Integer type;

    // ==================== Request message ====================

    @Schema(description = "Requested keywords When type for MpAutoReplyTypeEnum#KEYWORD Time，Required", example = "Keywords")
    private String requestKeyword;
    @Schema(description = "Request matching method When type for MpAutoReplyTypeEnum#KEYWORD Time，Required", example = "1")
    private Integer requestMatch;

    @Schema(description = "Requested message type When type for MpAutoReplyTypeEnum#MESSAGE Time，Required", example = "text")
    private String requestMessageType;

    // ==================== Response message ====================

    @Schema(description = "Reply message type Enumeration TEXT、IMAGE、VOICE、VIDEO、NEWS、MUSIC", example = "text")
    @NotEmpty(message = "The reply message type cannot be empty")
    private String responseMessageType;

    @Schema(description = "Reply message content", example = "Welcome to follow")
    @NotEmpty(message = "The reply message content cannot be empty", groups = TextMessageGroup.class)
    private String responseContent;

    @Schema(description = "Replied media id", example = "123456")
    @NotEmpty(message = "Reply message mediaId Cannot be empty",
            groups = {ImageMessageGroup.class, VoiceMessageGroup.class, VideoMessageGroup.class})
    private String responseMediaId;
    @Schema(description = "Replied media URL", example = "https://www.econets.cn/xxx.jpg")
    @NotEmpty(message = "Reply message mediaId Cannot be empty",
            groups = {ImageMessageGroup.class, VoiceMessageGroup.class, VideoMessageGroup.class})
    private String responseMediaUrl;

    @Schema(description = "Thumbnail media id", example = "123456")
    @NotEmpty(message = "Reply message thumbMediaId Cannot be empty", groups = {MusicMessageGroup.class})
    private String responseThumbMediaId;
    @Schema(description = "Thumbnail media URL",example = "https://www.econets.cn/xxx.jpg")
    @NotEmpty(message = "Reply message thumbMedia Address cannot be empty", groups = {MusicMessageGroup.class})
    private String responseThumbMediaUrl;

    @Schema(description = "Reply title", example = "Video title")
    @NotEmpty(message = "The reply message title cannot be empty", groups = VideoMessageGroup.class)
    private String responseTitle;
    @Schema(description = "Reply description", example = "Video description")
    @NotEmpty(message = "Message description cannot be empty", groups = VideoMessageGroup.class)
    private String responseDescription;

    /**
     * Reply to the picture and text message
     *
     * The message type is {@link WxConsts.XmlMsgType} of NEWS
     */
    @NotNull(message = "The reply message cannot be empty", groups = {NewsMessageGroup.class, ViewLimitedButtonGroup.class})
    @Valid
    private List<MpMessageDO.Article> responseArticles;

    @Schema(description = "Reply music link", example = "https://www.econets.cn/xxx.mp3")
    @NotEmpty(message = "The music link in reply cannot be empty", groups = MusicMessageGroup.class)
    @URL(message = "The format of the high-quality music link in the reply is incorrect", groups = MusicMessageGroup.class)
    private String responseMusicUrl;
    @Schema(description = "High quality music links", example = "https://www.econets.cn/xxx.mp3")
    @NotEmpty(message = "The high-quality music link in the reply cannot be empty", groups = MusicMessageGroup.class)
    @URL(message = "The format of the high-quality music link in the reply is incorrect", groups = MusicMessageGroup.class)
    private String responseHqMusicUrl;

    @AssertTrue(message = "The requested keyword cannot be empty")
    public boolean isRequestKeywordValid() {
        return ObjectUtil.notEqual(type, MpAutoReplyTypeEnum.KEYWORD)
                || requestKeyword != null;
    }

    @AssertTrue(message = "The requested keyword match cannot be empty")
    public boolean isRequestMatchValid() {
        return ObjectUtil.notEqual(type, MpAutoReplyTypeEnum.KEYWORD)
                || requestMatch != null;
    }

    @AssertTrue(message = "The requested message type cannot be empty")
    public boolean isRequestMessageTypeValid() {
        return ObjectUtil.notEqual(type, MpAutoReplyTypeEnum.MESSAGE)
                || requestMessageType != null;
    }


}
