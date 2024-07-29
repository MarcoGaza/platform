package cn.econets.blossom.module.mp.enums.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Matching mode for automatic reply to public account messages
 *
 *
 */
@Getter
@AllArgsConstructor
public enum MpAutoReplyMatchEnum {

    ALL(1, "Exact match"),
    LIKE(2, "Half match"),
    ;

    /**
     * Match
     */
    private final Integer match;
    /**
     * Matched name
     */
    private final String name;

}
