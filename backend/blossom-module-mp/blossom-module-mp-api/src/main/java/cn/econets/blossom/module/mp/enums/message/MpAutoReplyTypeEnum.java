package cn.econets.blossom.module.mp.enums.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Type of automatic reply to public account messages
 *
 *
 */
@Getter
@AllArgsConstructor
public enum MpAutoReplyTypeEnum {

    SUBSCRIBE(1, "Reply when following"),
    MESSAGE(2, "Received message reply"),
    KEYWORD(3, "Keyword reply"),
    ;

    /**
     * Source
     */
    private final Integer type;
    /**
     * Type name
     */
    private final String name;

}
