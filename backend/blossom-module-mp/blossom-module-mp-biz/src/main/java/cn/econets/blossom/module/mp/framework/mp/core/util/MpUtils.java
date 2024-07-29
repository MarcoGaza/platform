package cn.econets.blossom.module.mp.framework.mp.core.util;

import cn.hutool.core.util.StrUtil;
import cn.econets.blossom.framework.common.util.validation.ValidationUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;

import javax.validation.Validator;

/**
 * Public Account Tools
 *
 *
 */
@Slf4j
public class MpUtils {

    /**
     * Check whether the message format meets the requirements
     *
     * @param type Type
     * @param message Message
     */
    public static void validateMessage(Validator validator, String type, Object message) {
        // Get the corresponding verification group
        Class<?> group;
        switch (type) {
            case WxConsts.XmlMsgType.TEXT:
                group = TextMessageGroup.class;
                break;
            case WxConsts.XmlMsgType.IMAGE:
                group = ImageMessageGroup.class;
                break;
            case WxConsts.XmlMsgType.VOICE:
                group = VoiceMessageGroup.class;
                break;
            case WxConsts.XmlMsgType.VIDEO:
                group = VideoMessageGroup.class;
                break;
            case WxConsts.XmlMsgType.NEWS:
                group = NewsMessageGroup.class;
                break;
            case WxConsts.XmlMsgType.MUSIC:
                group = MusicMessageGroup.class;
                break;
            default:
                log.error("[validateMessage][Unknown message type({})]", message);
                throw new IllegalArgumentException("Unsupported message type：" + type);
        }
        // Execute verification
        ValidationUtils.validate(validator, message, group);
    }

    public static void validateButton(Validator validator, String type, String messageType, Object button) {
        if (StrUtil.isBlank(type)) {
            return;
        }
        // Get the corresponding verification group
        Class<?> group;
        switch (type) {
            case WxConsts.MenuButtonType.CLICK:
                group = ClickButtonGroup.class;
                validateMessage(validator, messageType, button); // Additional verification of reply message format is required
                break;
            case WxConsts.MenuButtonType.VIEW:
                group = ViewButtonGroup.class;
                break;
            case WxConsts.MenuButtonType.MINIPROGRAM:
                group = MiniProgramButtonGroup.class;
                break;
            case WxConsts.MenuButtonType.SCANCODE_WAITMSG:
                group = ScanCodeWaitMsgButtonGroup.class;
                validateMessage(validator, messageType, button); // Additional verification of the reply message format is required
                break;
            case "article_" + WxConsts.MenuButtonType.VIEW_LIMITED:
                group = ViewLimitedButtonGroup.class;
                break;
            case WxConsts.MenuButtonType.SCANCODE_PUSH: // No verification required，Directly return That's it
            case WxConsts.MenuButtonType.PIC_SYSPHOTO:
            case WxConsts.MenuButtonType.PIC_PHOTO_OR_ALBUM:
            case WxConsts.MenuButtonType.PIC_WEIXIN:
            case WxConsts.MenuButtonType.LOCATION_SELECT:
                return;
            default:
                log.error("[validateButton][Unknown button({})]", button);
                throw new IllegalArgumentException("Unsupported button type：" + type);
        }
        // Execute verification
        ValidationUtils.validate(validator, button, group);
    }

    /**
     * According to message type，Get the corresponding media file type
     *
     * Attention，Will not return WxConsts.MediaFileType.THUMB，Because the type will be clearly marked
     *
     * @param messageType Message type {@link  WxConsts.XmlMsgType}
     * @return Media file type {@link WxConsts.MediaFileType}
     */
    public static String getMediaFileType(String messageType) {
        switch (messageType) {
            case WxConsts.XmlMsgType.IMAGE:
                return WxConsts.MediaFileType.IMAGE;
            case WxConsts.XmlMsgType.VOICE:
                return WxConsts.MediaFileType.VOICE;
            case WxConsts.XmlMsgType.VIDEO:
                return WxConsts.MediaFileType.VIDEO;
            default:
                return WxConsts.MediaFileType.FILE;
        }
    }

    /**
     * Text Type of message，Parameter verification Group
     */
    public interface TextMessageGroup {}

    /**
     * Image Type of message，Parameter verification Group
     */
    public interface ImageMessageGroup {}

    /**
     * Voice Type of message，Parameter verification Group
     */
    public interface VoiceMessageGroup {}

    /**
     * Video Type of message，Parameter verification Group
     */
    public interface VideoMessageGroup {}

    /**
     * News Type of message，Parameter verification Group
     */
    public interface NewsMessageGroup {}

    /**
     * Music Type of message，Parameter verification Group
     */
    public interface MusicMessageGroup {}

    /**
     * Click Button type，Parameter verification Group
     */
    public interface ClickButtonGroup {}

    /**
     * View Button type，Parameter verification Group
     */
    public interface ViewButtonGroup {}

    /**
     * MiniProgram Button type，Parameter verification Group
     */
    public interface MiniProgramButtonGroup {}

    /**
     * SCANCODE_WAITMSG Button type，Parameter verification Group
     */
    public interface ScanCodeWaitMsgButtonGroup {}

    /**
     * VIEW_LIMITED Button type，Parameter verification Group
     */
    public interface ViewLimitedButtonGroup {}
}
