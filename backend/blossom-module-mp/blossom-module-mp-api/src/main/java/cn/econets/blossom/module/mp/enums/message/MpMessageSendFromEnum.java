package cn.econets.blossom.module.mp.enums.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The source of WeChat public account messages
 *
 *
 */
@Getter
@AllArgsConstructor
public enum MpMessageSendFromEnum {

    USER_TO_MP(1, "Sent to the public account by fans"),
    MP_TO_USER(2, "Send to fans on the official account"),
    ;

    /**
     * Source
     */
    private final Integer from;
    /**
     * Source name
     */
    private final String name;

}
