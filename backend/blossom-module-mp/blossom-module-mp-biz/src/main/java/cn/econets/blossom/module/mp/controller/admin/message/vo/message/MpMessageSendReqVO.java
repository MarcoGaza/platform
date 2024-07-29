package cn.econets.blossom.module.mp.controller.admin.message.vo.message;

import cn.econets.blossom.module.mp.dal.dataobject.message.MpMessageDO;
import cn.econets.blossom.module.mp.framework.mp.core.util.MpUtils.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Management Backend - Send public account message Request VO")
@Data
public class MpMessageSendReqVO {

    @Schema(description = "The public account fans' number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    @NotNull(message = "The public account fan number cannot be empty")
    private Long userId;

    // ========== Message content ==========

    @Schema(description = "Message type TEXT/IMAGE/VOICE/VIDEO/NEWS", requiredMode = Schema.RequiredMode.REQUIRED, example = "text")
    @NotEmpty(message = "Message type cannot be empty")
    public String type;

    @Schema(description = "Message content", requiredMode = Schema.RequiredMode.REQUIRED, example = "Hello")
    @NotEmpty(message = "Message content cannot be empty", groups = TextMessageGroup.class)
    private String content;

    @Schema(description = "Media ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "qqc_2Fot30Jse-HDoZmo5RrUDijz2nGUkP")
    @NotEmpty(message = "Message content cannot be empty", groups = {ImageMessageGroup.class, VoiceMessageGroup.class, VideoMessageGroup.class})
    private String mediaId;

    @Schema(description = "Title", requiredMode = Schema.RequiredMode.REQUIRED, example = "No title")
    @NotEmpty(message = "Message content cannot be empty", groups = VideoMessageGroup.class)
    private String title;

    @Schema(description = "Description", requiredMode = Schema.RequiredMode.REQUIRED, example = "Guess")
    @NotEmpty(message = "Message description cannot be empty", groups = VideoMessageGroup.class)
    private String description;

    @Schema(description = "Thumbnail media id", requiredMode = Schema.RequiredMode.REQUIRED, example = "qqc_2Fot30Jse-HDoZmo5RrUDijz2nGUkP")
    @NotEmpty(message = "Thumbnail media id Cannot be empty", groups = MusicMessageGroup.class)
    private String thumbMediaId;

    @Schema(description = "Image and text message", requiredMode = Schema.RequiredMode.REQUIRED)
    @Valid
    @NotNull(message = "The picture and text message cannot be empty", groups = NewsMessageGroup.class)
    private List<MpMessageDO.Article> articles;

    @Schema(description = "Music Link The message type is MUSIC Time", example = "https://www.econets.cn/music.mp3")
    private String musicUrl;

    @Schema(description = "High quality music links The message type is MUSIC Time", example = "https://www.econets.cn/music.mp3")
    private String hqMusicUrl;

}
