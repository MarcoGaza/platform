package cn.econets.blossom.module.mp.controller.admin.message.vo.message;

import cn.econets.blossom.module.mp.dal.dataobject.message.MpMessageDO;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import me.chanjar.weixin.common.api.WxConsts;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Schema(description = "Management Backend - Public account news Response VO")
@Data
public class MpMessageRespVO {

    @Schema(description = "Primary key", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Integer id;

    @Schema(description = "WeChat public account message id", requiredMode = Schema.RequiredMode.REQUIRED, example = "23953173569869169")
    private Long msgId;

    @Schema(description = "The public account number", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long accountId;
    @Schema(description = "Official account appid", requiredMode = Schema.RequiredMode.REQUIRED, example = "wx1234567890")
    private String appId;

    @Schema(description = "Public account fan number", requiredMode = Schema.RequiredMode.REQUIRED, example = "2048")
    private Long userId;
    @Schema(description = "Official account fan logo", requiredMode = Schema.RequiredMode.REQUIRED, example = "o6_bmjrPTlm6_2sgVt7hMZOPfL2M")
    private String openid;

    @Schema(description = "Message type See WxConsts.XmlMsgType Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "text")
    private String type;
    @Schema(description = "Source See MpMessageSendFromEnum Enumeration", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer sendFrom;

    // ========= Normal message content https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_standard_messages.html

    @Schema(description = "Message content The message type is text Time，Only then is it valuable", example = "Hello")
    private String content;

    @Schema(description = "Media material number The message type is image、voice、video Time，Only then is it valuable", example = "1234567890")
    private String mediaId;
    @Schema(description = "Media files URL The message type is image、voice、video Time，Only then is it valuable", example = "https://www.econets.cn/xxx.png")
    private String mediaUrl;

    @Schema(description = "Text after speech recognition The message type is voice Time，Only then is it valuable", example = "Text after speech recognition")
    private String recognition;
    @Schema(description = "Voice format The message type is voice Time，Only then is it valuable", example = "amr")
    private String format;

    @Schema(description = "Title The message type is video、music、link Time，Only then is it valuable", example = "I am the title")
    private String title;

    @Schema(description = "Description The message type is video、music Time，Only then is it valuable", example = "I am describing")
    private String description;

    @Schema(description = "Thumbnail media id The message type is video、music Time，Only then is it valuable", example = "1234567890")
    private String thumbMediaId;
    @Schema(description = "Thumbnail media URL The message type is video、music Time，Only then is it valuable", example = "https://www.econets.cn/xxx.png")
    private String thumbMediaUrl;

    @Schema(description = "Click on the picture and text message to jump to the link The message type is link Time，Only then is it valuable", example = "https://www.econets.cn")
    private String url;

    @Schema(description = "Geographic location dimension The message type is location Time，Only then is it valuable", example = "23.137466")
    private Double locationX;

    @Schema(description = "Geographic location longitude The message type is location Time，Only then is it valuable", example = "113.352425")
    private Double locationY;

    @Schema(description = "Map zoom size The message type is location Time，Only then is it valuable", example = "13")
    private Double scale;

    @Schema(description = "Detailed address The message type is location Time，Only then is it valuable", example = "Huangxing Road, Yangpu District 221-4 Hao Lin")
    private String label;

    /**
     * Image and text message array
     *
     * The message type is {@link WxConsts.XmlMsgType} of NEWS
     */
    @TableField(typeHandler = MpMessageDO.ArticleTypeHandler.class)
    private List<MpMessageDO.Article> articles;

    @Schema(description = "Music Link The message type is music Time，Only then is it valuable", example = "https://www.econets.cn/xxx.mp3")
    private String musicUrl;
    @Schema(description = "High quality music links The message type is music Time，Only then is it valuable", example = "https://www.econets.cn/xxx.mp3")
    private String hqMusicUrl;

    // ========= Event push https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Receiving_event_pushes.html

    @Schema(description = "Event Type See WxConsts.EventType Enumeration", example = "subscribe")
    private String event;
    @Schema(description = "Event Key See WxConsts.EventType Enumeration", example = "qrscene_123456")
    private String eventKey;

    @Schema(description = "Creation time", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

}
